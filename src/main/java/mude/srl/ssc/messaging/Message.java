package mude.srl.ssc.messaging;

import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;

public class Message {
	
	private int type;
	private String title;
	private String body;
	private String plc_source;
	
	
	
	public Message() {
		super();
		
	}

	public Message(MessageInfoType type, String title, String body,String plc_source) {
		super();
		this.type = type.getType();
		this.title = title;
		this.body = body;
		this.plc_source=plc_source;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getPlc_source() {
		return plc_source;
	}

	public void setPlc_source(String plc_source) {
		this.plc_source = plc_source;
	}
	
	public static Message buildFromRequest(MessageInfoType type, String title, String body,RequestCommandResourceReservation r) {
		Message m   = new Message(type,title,body,r.getPlc_uid());
		return m;
	}

}
