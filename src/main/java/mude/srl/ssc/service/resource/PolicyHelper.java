package mude.srl.ssc.service.resource;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourcePolicy;

import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.payload.exception.HourOutOfLimitException;
import mude.srl.ssc.service.payload.exception.ReservationIntervalException;
import mude.srl.ssc.service.payload.exception.ReservetionPolicyException;

@Component
public class PolicyHelper implements ResourcePolicyService {

	private static final SimpleDateFormat hourParser = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat reservationPrint = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * Gestione della policy legate ad una risorsa.
	 * 
	 * @throws HourOutOfLimitException
	 * 
	 */
	public void checkPolicy(Resource r, RequestCommandResourceReservation request)
			throws ReservetionPolicyException, HourOutOfLimitException {

		/**
		 * Estrazione limiti inferiore e superiore per la prenotazione di una risorsa.
		 * Ricordare che le istanze di Date ,start_limit ed end_limit, sono le ore della
		 * giornata rispettivamente di inizio e fine ora in cui la risorsa puo' essere
		 * prenotata o cmq utilizzata.
		 * 
		 */
		ResourcePolicy policy = r.getPolicy();

		if (policy != null) {
			PolicyWrapper pw = preparaDatiPerConfronto(policy);
			merge(pw, r, request);
		} else {
			defaut(r, request);
		}

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param w
	 * @param r
	 * @param request
	 * @throws HourOutOfLimitException
	 */
	public void merge(PolicyWrapper w,Resource r, RequestCommandResourceReservation request) throws HourOutOfLimitException{
		
		     

		     Date ini_prenotazione_richiesta = request.getStart();
		     Date fin_prenotazione_richiesta = request.getEnd();
		     /**
		      * 
		      * Vediamo se dobbiamo tagliare
		      * 
		      */
		     
		     if(w.isDaily()) {
		    	
		    	/**
		    	 * Costruire un limite superiore
		    	 */
			     
			      
			     
		    	 
		    	 /**
		 		 * Conversione date di prenotazione
		 		 */
		 		Calendar c_ini_pre = Calendar.getInstance(Locale.ITALIAN);
		 		Calendar c_fin_pre = Calendar.getInstance(Locale.ITALIAN);

		 		c_ini_pre.setTime(ini_prenotazione_richiesta);
		 		c_fin_pre.setTime(fin_prenotazione_richiesta);
		 		/**
		 		 * 
		 		 */
		 		changeIfisAfterToday(c_fin_pre);
	
		 		 
		 		if (confrontaOraPerLimiteInferiore(w.getMinHour(), c_ini_pre) != 0) {
					
		 			// Sposto il tempo al limite minimo consentito
		 			
					switchTime(w.getMinHour(), c_ini_pre);			
					request.setStart(c_ini_pre.getTime());
				}
				
				if (confrontaOraPerLimiteSuperiore(w.getMaxHour(), c_fin_pre) != 0) {
					
					// Sposto il tempo al limite massimo consentito
					
					switchTime(w.getMaxHour(), c_fin_pre);
					request.setEnd(c_fin_pre.getTime());
				}
				/**
				 * Dopo tutte le trasformazioni controllo i limiti di prenotazione
				 * 
				 * Il primo controllo che viene fatto confronta l'istante corrente con la  
				 * fine della prenotazione per vedere se cmq se viene superato il tempo il massimo.
				 */
				
				String dbs = "Estremi prenotazione : "+reservationPrint.format(request.getStart())+" "+reservationPrint.format(request.getEnd());
				System.out.println(dbs);
				
				if(isBeforeNow(request.getEnd())) {
					HourOutOfLimitException ex  = new HourOutOfLimitException();
					ex.setReservation(request.getReservation());
					ex.setTarget(request.getPlc_uid());
					throw ex;
				}
				if(isStartBeforeEnd(request)) {
					ReservationIntervalException ex  = new ReservationIntervalException();
					ex.setReservation(request.getReservation());
					ex.setTarget(request.getPlc_uid());
					throw ex;
				}
				
		     }
	}

	private boolean isStartBeforeEnd(RequestCommandResourceReservation request) {
		
		return request.getStart().after(request.getEnd());
	}

	/**
	 * Operazione di default in base alle date impostate direttamente nella risorsa
	 * 
	 */
	private void defaut(Resource r, RequestCommandResourceReservation request) {

		Date start_limit = r.getStartTimeLimit();
		Date end_limit = r.getEndTimeLimit();

		if (start_limit == null || end_limit == null) {
			return;
		}
		Date ini_prenotazione_richiesta = request.getStart();
		Date fin_prenotazione_richiesta = request.getEnd();
		/**
		 * Inizio estrazione dell'ora e' associazione alla nuova istanza temporale per
		 * confrontare i dati.
		 */

		/**
		 * Data inizio prenotazione
		 */
		Calendar c_start_limit = Calendar.getInstance(Locale.ITALIAN);
		/**
		 * Data fine prenotazione
		 */
		Calendar c_end_limit = Calendar.getInstance(Locale.ITALIAN);
		/**
		 * Associazione delle date.
		 */
		c_start_limit.setTime(start_limit);
		c_end_limit.setTime(end_limit);
		/**
		 * Conversione date di prenotazione
		 */
		Calendar c_ini_pre = Calendar.getInstance(Locale.ITALIAN);
		Calendar c_fin_pre = Calendar.getInstance(Locale.ITALIAN);

		c_ini_pre.setTime(ini_prenotazione_richiesta);
		c_fin_pre.setTime(fin_prenotazione_richiesta);
		/**
		 * 
		 */
		if (confrontaOraPerLimiteInferiore(c_start_limit, c_ini_pre) != 0) {
			// Sposto il tempo al limite minimo consentito
			switchTime(c_start_limit, c_ini_pre);
			request.setStart(c_ini_pre.getTime());
		}

		if (confrontaOraPerLimiteSuperiore(c_end_limit, c_fin_pre) != 0) {
			// Sposto il tempo al limite massimo consentito
			switchTime(c_end_limit, c_fin_pre);
			request.setEnd(c_fin_pre.getTime());
		}
		/**
		 * Impostazione limiti associati alla risorsa
		 */

	}

	/**
	 * Il pivo rappresenta il limite mininimo al di sotto del quale non si puo'
	 * andare
	 * 
	 * @param pivot
	 * @param data_confronto
	 * @return 0 se la data che si confronta rispette i vincoli del limite
	 *         inferiore,-1 se l'ora e' inferiore,-2 se i minuti sono inferiori
	 */
	protected int confrontaOraPerLimiteInferiore(Calendar pivot, Calendar data_confronto) {

		int ora_ = pivot.get(Calendar.HOUR_OF_DAY);
		int minuti_ = pivot.get(Calendar.MINUTE);

		int ora = data_confronto.get(Calendar.HOUR_OF_DAY);
		int minuti = data_confronto.get(Calendar.MINUTE);

		if (ora_ > ora) {
			return -1;
		}
		if (ora == ora_ && minuti_ > minuti) {
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
	protected int confrontaOraPerLimiteSuperiore(Calendar pivot, Calendar data_confronto) {

		int _ora_ = pivot.get(Calendar.HOUR_OF_DAY);
		int _minuti_ = pivot.get(Calendar.MINUTE);

		int ora = data_confronto.get(Calendar.HOUR_OF_DAY);
		int minuti = data_confronto.get(Calendar.MINUTE);

		if (ora > _ora_) {
			return -1;
		}
		if (ora == _ora_ && minuti > _minuti_) {
			return -2;
		}
		return 0;
	}

	/**
	 * Funzione di utilita' generale per aggiornamento limite ore.
	 * 
	 * @param riferimento    instanza cui fare riferimento per lo scambio del valore
	 *                       dell'ora.
	 * 
	 * @param dataDaCambiare
	 */
	protected void switchTime(Calendar riferimento, Calendar dataDaCambiare) {
		int _ora_ = riferimento.get(Calendar.HOUR);
		int _minuti_ = riferimento.get(Calendar.MINUTE);

		dataDaCambiare.set(Calendar.HOUR, _ora_);
		dataDaCambiare.set(Calendar.MINUTE, _minuti_);
	}

	/**
	 * 
	 * Impostazione nuovo giorno
	 * 
	 * @param riferimento
	 * @param dataDaCambiare
	 */
	protected void switchDay(Calendar riferimento, Calendar dataDaCambiare) {
		int giorno = riferimento.get(Calendar.DAY_OF_MONTH);
		dataDaCambiare.set(Calendar.DAY_OF_MONTH, giorno);
	}

	/**
	 * Controllo se la data in ingresso e maggiore del giorno corrente. TODO
	 * prendere il locale della macchina da file di configurazione.
	 * 
	 * @param timeToCheck
	 * @return
	 */
	protected void changeIfisAfterToday(Calendar timeToCheck) {
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		if (timeToCheck.after(c)) {
			timeToCheck.set(Calendar.YEAR, c.get(Calendar.YEAR));
			timeToCheck.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
		}
	}

	/**
	 * La data di fine prenotazione e' scaduta se il limite superiore della
	 * prenatozione e' antecedente il tempo corrente, questo va fatto alla fine
	 * delle eventuali trasformazioni che vengono effettuate sull'intervallo.
	 * 
	 * @param timeToCheck
	 * @return
	 */
	protected boolean isBeforeNow(Calendar timeToCheck) {

		Calendar c = Calendar.getInstance(Locale.ITALIAN);

		return timeToCheck.before(c);

	}

	/**
	 * 
	 * @param timeToCheck
	 * @return
	 */
	protected boolean isBeforeNow(Date timeToCheck) {

		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		Calendar c_in = Calendar.getInstance(Locale.ITALIAN);
		c_in.setTime(timeToCheck);
		return c_in.before(c);

	}

	protected PolicyWrapper preparaDatiPerConfronto(ResourcePolicy policy) {

		PolicyWrapper w = new PolicyWrapper();

		w.setDaily(policy.getHourLimited());

		/**
		 * Controllo la correttezza del contenuto
		 */
		String day_of_week = policy.getDayOfWeek();
		String hour_of_day = policy.getHourOfDay();
		/**
		 * Gestione giorno della settimana
		 */
		if (day_of_week != null) {

			day_of_week = day_of_week.trim();
			String[] days = day_of_week.split("-");
			if (days.length == 2) {
				try {
					w.setMinDay(Integer.valueOf(days[0]));
				} catch (Exception e) {
					w.setMinDay(Integer.valueOf(1));
				}
				try {
					w.setMaxDay(Integer.valueOf(days[1]));
				} catch (Exception e) {
					w.setMaxDay(Integer.valueOf(7));
				}

			}
		} else {
			/**
			 * Impostazione di default per permettere cmq la prenotazione.
			 * 
			 */
			w.setMinDay(Integer.valueOf(1));
			w.setMaxDay(Integer.valueOf(7));
		}
		/**
		 * Gestione ore della giornata
		 */
		if (hour_of_day != null) {

			String[] hours = hour_of_day.trim().split("-");
			if (hours.length == 2) {

				try {
					Date minHour = hourParser.parse(hours[0]);
					Calendar c_maz = Calendar.getInstance(Locale.ITALIAN);
					c_maz.setTimeInMillis(minHour.getTime());
					w.setMinHour(c_maz);

				} catch (ParseException e) {

				}
				try {
					Date maxHour = hourParser.parse(hours[1]);
					Calendar c_maz = Calendar.getInstance(Locale.ITALIAN);
					c_maz.setTimeInMillis(maxHour.getTime());
					w.setMaxHour(c_maz);

				} catch (ParseException e) {

				}
			}
		}

		return w;
	}

}
