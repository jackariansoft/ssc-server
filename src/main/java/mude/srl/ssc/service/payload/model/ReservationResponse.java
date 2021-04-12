package mude.srl.ssc.service.payload.model;

import java.util.List;

public class ReservationResponse {
//	{
//	    "count": 1,
//	    "results": [
//	        {
//	            "dt_end": "2021-01-31T22:59:00",
//	            "dt_start": "2021-01-24T23:00:00",
//	            "email": "ariannagiacomo@gmail.com",
//	            "location_id": 1,
//	            "resource_sku": "SKU00007",
//	            "user_id": 15
//	        }
//	    ]
//	}
	private Integer count;
	private List<Reservation> results;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Reservation> getResults() {
		return results;
	}
	public void setResults(List<Reservation> results) {
		this.results = results;
	}
	
	

}
