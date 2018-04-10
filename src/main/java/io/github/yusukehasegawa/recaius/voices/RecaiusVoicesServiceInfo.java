package io.github.yusukehasegawa.recaius.voices;

public class RecaiusVoicesServiceInfo {
	private String service_id;
	private String password;

	public RecaiusVoicesServiceInfo(String service_id, String password) {
		this.service_id = service_id;
		this.password = password;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
