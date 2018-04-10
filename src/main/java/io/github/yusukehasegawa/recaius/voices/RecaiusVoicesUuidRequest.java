package io.github.yusukehasegawa.recaius.voices;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecaiusVoicesUuidRequest {
	private String audio_type = "audio/x-linear";
	private Integer energy_thresho;
	private String result_type = "one_best";
	private int result_count = 1;
	private int model_id = 1;
	private boolean push_to_talk = false;

	public String getAudio_type() {
		return audio_type;
	}

	public void setAudio_type(String audio_type) {
		this.audio_type = audio_type;
	}

	public Integer getEnergy_thresho() {
		return energy_thresho;
	}

	public void setEnergy_thresho(Integer energy_thresho) {
		this.energy_thresho = energy_thresho;
	}

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}

	public int getResult_count() {
		return result_count;
	}

	public void setResult_count(int result_count) {
		this.result_count = result_count;
	}

	public int getModel_id() {
		return model_id;
	}

	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}

	public boolean isPush_to_talk() {
		return push_to_talk;
	}

	public void setPush_to_talk(boolean push_to_talk) {
		this.push_to_talk = push_to_talk;
	}

}
