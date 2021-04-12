package mude.srl.ssc.service.resource;


import java.util.Calendar;

public class PolicyWrapper {
	
	Integer minDay;
	Integer maxDay;
	
	Calendar minHour;	
	Calendar maxHour;
	
	
	
	boolean daily;

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public Integer getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(Integer maxDay) {
		this.maxDay = maxDay;
	}

	
	public Calendar getMinHour() {
		return minHour;
	}

	public void setMinHour(Calendar minHour) {
		this.minHour = minHour;
	}

	public Calendar getMaxHour() {
		return maxHour;
	}

	public void setMaxHour(Calendar maxHour) {
		this.maxHour = maxHour;
	}

	public boolean isDaily() {
		return daily;
	}

	public void setDaily(boolean daily) {
		this.daily = daily;
	}
	
	
}
