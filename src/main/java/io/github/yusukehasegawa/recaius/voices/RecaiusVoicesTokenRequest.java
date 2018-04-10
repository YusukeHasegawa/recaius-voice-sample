package io.github.yusukehasegawa.recaius.voices;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecaiusVoicesTokenRequest {
	private long expiry_sec = 3600;
	private RecaiusVoicesServiceInfo speech_recog_jaJP, speech_recog_enUS,
			speech_recog_zhCN, speech_recog_koK;

	public RecaiusVoicesTokenRequest(RecaiusVoicesServiceInfo speech_recog_jaJP) {
		this.speech_recog_jaJP = speech_recog_jaJP;
	}

	public RecaiusVoicesTokenRequest(RecaiusVoicesServiceInfo speech_recog_jaJP,
			RecaiusVoicesServiceInfo speech_recog_enUS) {
		this.speech_recog_jaJP = speech_recog_jaJP;
		this.speech_recog_enUS = speech_recog_enUS;
	}

	public RecaiusVoicesTokenRequest(RecaiusVoicesServiceInfo speech_recog_jaJP,
			RecaiusVoicesServiceInfo speech_recog_enUS,
			RecaiusVoicesServiceInfo speech_recog_zhCN) {
		this.speech_recog_jaJP = speech_recog_jaJP;
		this.speech_recog_enUS = speech_recog_enUS;
		this.speech_recog_zhCN = speech_recog_zhCN;
	}

	public RecaiusVoicesTokenRequest(RecaiusVoicesServiceInfo speech_recog_jaJP,
			RecaiusVoicesServiceInfo speech_recog_enUS,
			RecaiusVoicesServiceInfo speech_recog_zhCN,
			RecaiusVoicesServiceInfo speech_recog_koK) {
		this.speech_recog_jaJP = speech_recog_jaJP;
		this.speech_recog_enUS = speech_recog_enUS;
		this.speech_recog_zhCN = speech_recog_zhCN;
		this.speech_recog_koK = speech_recog_koK;
	}

	public long getExpiry_sec() {
		return expiry_sec;
	}

	public void setExpiry_sec(long expiry_sec) {
		this.expiry_sec = expiry_sec;
	}

	public RecaiusVoicesServiceInfo getSpeech_recog_jaJP() {
		return speech_recog_jaJP;
	}

	public void setSpeech_recog_jaJP(RecaiusVoicesServiceInfo speech_recog_jaJP) {
		this.speech_recog_jaJP = speech_recog_jaJP;
	}

	public RecaiusVoicesServiceInfo getSpeech_recog_enUS() {
		return speech_recog_enUS;
	}

	public void setSpeech_recog_enUS(RecaiusVoicesServiceInfo speech_recog_enUS) {
		this.speech_recog_enUS = speech_recog_enUS;
	}

	public RecaiusVoicesServiceInfo getSpeech_recog_zhCN() {
		return speech_recog_zhCN;
	}

	public void setSpeech_recog_zhCN(RecaiusVoicesServiceInfo speech_recog_zhCN) {
		this.speech_recog_zhCN = speech_recog_zhCN;
	}

	public RecaiusVoicesServiceInfo getSpeech_recog_koK() {
		return speech_recog_koK;
	}

	public void setSpeech_recog_koK(RecaiusVoicesServiceInfo speech_recog_koK) {
		this.speech_recog_koK = speech_recog_koK;
	}

}
