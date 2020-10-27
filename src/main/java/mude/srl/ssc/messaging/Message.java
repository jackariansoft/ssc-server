package mude.srl.ssc.messaging;

public class Message {
	
	private int type;
	private String title;
	private String body;
	
	
	
	public Message() {
		super();
		
	}

	public Message(int type, String title, String body) {
		super();
		this.type = type;
		this.title = title;
		this.body = body;
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
	
	

}
