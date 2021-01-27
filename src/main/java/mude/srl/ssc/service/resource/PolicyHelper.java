package mude.srl.ssc.service.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.payload.exception.ReservetionPolicyException;

@Component
public class PolicyHelper implements ResourcePolicyService{
	
	
	public  void checkPolicy(Resource r,RequestCommandResourceReservation request) throws ReservetionPolicyException {
	
		/**
		 * Estrazione limiti inferiore e superiore per la prenotazione di una risorsa.
		 * Ricordare che le istanze di Date ,start_limit ed end_limit, sono le ore della giornata
		 * rispettivamente di inizio e fine ora in cui la risorsa puo' essere prenotata o cmq utilizzata.
		 *  
		 */		
			Date start_limit = r.getStartTimeLimit();
			Date end_limit   = r.getEndTimeLimit();
			
			Date ini_prenotazione  = request.getStart();
			Date fin_prenotazione  = request.getEnd();
			/**
			 * Inizio estrazione dell'ora 
			 */
		   Calendar c_start_limit  = Calendar.getInstance(Locale.ITALIAN);
		   Calendar c_end_limit  = Calendar.getInstance(Locale.ITALIAN);
		   
		   c_start_limit.setTime(start_limit);
		   c_end_limit.setTime(end_limit);
		   /**
		    * Conversione date di prenotazione
		    */
		   Calendar c_ini_pre  = Calendar.getInstance(Locale.ITALIAN);
		   Calendar c_fin_pre  = Calendar.getInstance(Locale.ITALIAN);
		   
		   c_ini_pre.setTime(ini_prenotazione);
		   c_fin_pre.setTime(fin_prenotazione);
		   /**
		    * 
		    */
		   if(confrontaOraPerLimiteInferiore(c_start_limit, c_ini_pre)!=0) {
			   //Sposo il tempo al limite minimo consentito
			   switchTime(c_start_limit, c_ini_pre);
		   }
		   if(confrontaOraPerLimiteSuperiore(c_end_limit, c_fin_pre)!=0) {
			   //Sposto il tempo al limite massimo consentito
			   switchTime(c_end_limit, c_fin_pre);
		   }
		   /**
		    * Impostazione limiti associati alla risorsa
		    */
		   
 		
		
	}
	/**
	 * Il pivo rappresenta il limite mininimo al di sotto del quale non si puo' andare
	 * @param pivot
	 * @param data_confronto
	 * @return 0 se la data che si confronta rispette i vincoli del limite inferiore,-1 se l'ora e' inferiore,-2 se i minuti sono inferiori
	 */
	protected int confrontaOraPerLimiteInferiore(Calendar pivot,Calendar data_confronto) {
		
		int ora_ 	 = pivot.get(Calendar.HOUR);
		int minuti_  = pivot.get(Calendar.MINUTE);
		
		int ora 	= data_confronto.get(Calendar.HOUR);
		int minuti  = data_confronto.get(Calendar.MINUTE);
		
		if(ora_>ora) {
			return -1;
		}
		if(ora==ora_&&minuti_>minuti) {
			return -2;
		}
		return 0;
	}
	/**
	 * 
	 * @param pivot
	 * @param data_confronto
	 * @return
	 */
    protected int confrontaOraPerLimiteSuperiore(Calendar pivot,Calendar data_confronto) {
		
		int _ora_ 	 = pivot.get(Calendar.HOUR);
		int _minuti_  = pivot.get(Calendar.MINUTE);
		
		int ora 	= data_confronto.get(Calendar.HOUR);
		int minuti  = data_confronto.get(Calendar.MINUTE);
		
		if(ora>_ora_) {
			return -1;
		}
		if(ora==_ora_&&minuti>_minuti_) {
			return -2;
		}
		return 0;
	}
     protected void switchTime(Calendar riferimento, Calendar dataDaCambiare) {
    	 int _ora_ 	 = riferimento.get(Calendar.HOUR);
 		 int _minuti_  = riferimento.get(Calendar.MINUTE);
 		
 		 dataDaCambiare.set(Calendar.HOUR,_ora_);
 		 dataDaCambiare.set(Calendar.MINUTE,_minuti_);
     }

}
