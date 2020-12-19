package mude.srl.ssc.service.payload.model;

import java.io.Serializable;

public class UnlockRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4927810937558463724L;
	private String token;

	public UnlockRequest(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
