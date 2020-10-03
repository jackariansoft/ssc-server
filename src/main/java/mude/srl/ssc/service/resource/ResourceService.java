package mude.srl.ssc.service.resource;

import mude.srl.ssc.entity.Resource;

public interface ResourceService {
	
	public static final int DISABLE_ACTION=2;
	public static final int ENABLE_ACTION =1;
	
	public void abilitaRisorsa(Resource r) throws Exception;
	public void abilitaRisorsa(Long id)  throws Exception;
	public void abilitaRisorsa(String tag) throws Exception;
	
	public void disabilitaRisorsa(Resource r) throws Exception;
	public void disabilitaRisorsa(Long id) throws Exception;
	public void disabilitaRisorsa(String tag) throws Exception;

	public void disabilitaRisorsaByReservationId(String r) throws Exception;
	
	
}
