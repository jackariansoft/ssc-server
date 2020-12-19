package mude.srl.ssc.rest.controller.command;

import java.io.Serializable;

public class QrCodeInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2345270973818436431L;
	String qrcode;

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	@Override
	public String toString() {
		return "QrCodeInfo [qrcode=" + qrcode + "]";
	}
	
	
	
	
}
