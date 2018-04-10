package io.github.yusukehasegawa.recaius.voices;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author y-hasegawa
 *
 */
public class RecaiusVoicesClient {

	public static final String AUTH_URL = "https://api.recaius.jp/auth/v2";
	public static final String VOICE_URL = "https://api.recaius.jp/asr/v2";

	private static final int CONNECT_TIMEOUT = 3000;
	private static final int READ_TIMEOUT = 5000;

	private final RestTemplate requestVoice;

	public RecaiusVoicesClient(RecaiusVoicesTokenRequest request) {
		this(request, new RestTemplateBuilder());
	}

	public RecaiusVoicesClient(RecaiusVoicesTokenRequest request,
			RestTemplateBuilder builder) {
		this(request,
				builder.rootUri(AUTH_URL).setConnectTimeout(CONNECT_TIMEOUT)
						.setReadTimeout(READ_TIMEOUT).build(),
				builder.rootUri(VOICE_URL).setConnectTimeout(CONNECT_TIMEOUT)
						.setReadTimeout(READ_TIMEOUT).build());
	}

	public RecaiusVoicesClient(RecaiusVoicesTokenRequest request,
			RestTemplate requestToken, RestTemplate requestVoice) {
		this.requestVoice = requestVoice;
		this.requestVoice.getInterceptors()
				.add(new TokenClientHttpRequestInterceptor(request, requestToken));
	}

	/**
	 * 音声認識の開始
	 * @return
	 */
	public String getUuid() {
		return this.getUuid(new RecaiusVoicesUuidRequest());
	}

	/**
	 * 音声認識の開始
	 * @param request
	 * @return
	 */
	public String getUuid(RecaiusVoicesUuidRequest request) {
		final RecaiusVoicesUuid res = requestVoice.postForObject("/voices", request,
				RecaiusVoicesUuid.class);
		return res.getUuid();
	}

	/**
	 * 音声認識の終了
	 * @param request
	 * @return
	 */
	public void deleteUuid(String uuid) {
		requestVoice.delete("/voices/" + uuid);
	}

	/**
	 * 音声データの送信・音声認識結果取得
	 * @param uuid
	 * @param voice
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> putVoice(RecaiusVoicesVoiceRequest request) {
		final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("uuid", request.getUuid());
		map.add("voice_id", request.getVoice_id());
		map.add("voice", new InputStreamResource(request.getVoice()));
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
				map, headers);
		final ResponseEntity<List> res = requestVoice.exchange(
				"/voices/" + request.getUuid(), HttpMethod.PUT, requestEntity,
				List.class);
		return res.getBody();
	}

	/**
	 * 音声認識結果取得 ループして完全な結果取得を取得する
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> perfectResultsVoice(String uuid) {
		List<String> ret = null;
		while (true) {
			final ResponseEntity<List> res = this.resultsVoice(uuid);
			if (res.getStatusCode() == HttpStatus.OK) {
				if (res.toString().contains("NO_DATA")) {
					break;
				}
				if (!res.toString().contains("SOS")) {
					ret = res.getBody();
				}
			}
			else if (res.getStatusCode() == HttpStatus.NO_CONTENT
					&& !CollectionUtils.isEmpty(ret)) {
				break;
			}
		}
		return ret;
	}

	/**
	 * 音声認識結果取得
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity<List> resultsVoice(String uuid) {
		final ResponseEntity<List> res = requestVoice
				.getForEntity("/voices/" + uuid + "/results", List.class);
		return res;
	}

	/**
	 * @author y-hasegawa
	 *
	 */
	static final class TokenClientHttpRequestInterceptor
			implements ClientHttpRequestInterceptor {

		private RecaiusVoicesToken token;
		private long tokenExpiry;
		private final Object lock = new Object();

		private final RestTemplate requestToken;
		private final RecaiusVoicesTokenRequest request;

		TokenClientHttpRequestInterceptor(RecaiusVoicesTokenRequest request,
				RestTemplate requestToken) {
			this.request = request;
			this.requestToken = requestToken;
		}

		void refreshToken(RestTemplate restTemplate) {
			if (token == null || tokenExpiry < System.currentTimeMillis()) {
				synchronized (lock) {
					if (token == null || tokenExpiry < System.currentTimeMillis()) {
						this.token = token();
						this.tokenExpiry = System.currentTimeMillis()
								+ (this.token.getExpiry_sec() - 300) * 1000;
					}
				}
			}
		}

		RecaiusVoicesToken token() {
			final RecaiusVoicesToken token = requestToken.postForObject("/tokens",
					this.request, RecaiusVoicesToken.class);
			return token;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			refreshToken(this.requestToken);
			request.getHeaders().set("X-Token", token.getToken());
			return execution.execute(request, body);
		}

	}
}
