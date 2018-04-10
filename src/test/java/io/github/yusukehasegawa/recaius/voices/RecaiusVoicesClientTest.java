package io.github.yusukehasegawa.recaius.voices;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class RecaiusVoicesClientTest {
	RecaiusVoicesClient client;
	InputStream voice;

	@Before
	public void setUp() throws Exception {
		final String service_id = System.getProperty("service_id");
		final String password = System.getProperty("password");

		final RecaiusVoicesTokenRequest request = new RecaiusVoicesTokenRequest(
				new RecaiusVoicesServiceInfo(service_id, password));
		client = new RecaiusVoicesClient(request);

		voice = new ClassPathResource("HON2_22A.wav").getInputStream();
		//voice = new ClassPathResource("recaius_test.wav").getInputStream();
	}

	@Test
	public void test() {
		final String uuid = client.getUuid();
		System.out.println(uuid);
		assertNotNull(uuid);
		{
			final RecaiusVoicesVoiceRequest request = new RecaiusVoicesVoiceRequest(uuid,
					voice);
			final Object ret = client.putVoice(request);
			System.out.println(ret);
			// assertNotNull(ret);
		}

		{
			final Object ret = client.perfectResultsVoice(uuid);
			System.out.println(ret);
			assertNotNull(ret);
		}



		client.deleteUuid(uuid);

	}

}
