package io.github.yusukehasegawa.recaius.voices;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecaiusVoicesVoiceRequest {
	private String uuid;
	private int voice_id = 1;
	private InputStream voice;

	public RecaiusVoicesVoiceRequest(String uuid, InputStream voice) {
		this.uuid = uuid;
		this.voice = voice;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getVoice_id() {
		return voice_id;
	}

	public void setVoice_id(int voice_id) {
		this.voice_id = voice_id;
	}

	public InputStream getVoice() {
		return voice;
	}

	public void setVoice(InputStream voice) {
		this.voice = voice;
	}

}
