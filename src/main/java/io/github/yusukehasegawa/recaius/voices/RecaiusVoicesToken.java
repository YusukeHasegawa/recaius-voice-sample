package io.github.yusukehasegawa.recaius.voices;

class RecaiusVoicesToken {
	private long expiry_sec;
	private String token;

	public long getExpiry_sec() {
		return expiry_sec;
	}

	public void setExpiry_sec(long expiry_sec) {
		this.expiry_sec = expiry_sec;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
