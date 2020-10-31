package mude.srl.ssc.messaging;

public enum MessageInfoType {
	 INFO(1),
	 WARNING(2),
	 ERROR(0);

	private int type;
	private MessageInfoType(int ordinal) {
		this.type = ordinal;
		
	}
	public int getType() {
		return type;
	}
	
	
	
	 
	 

}
