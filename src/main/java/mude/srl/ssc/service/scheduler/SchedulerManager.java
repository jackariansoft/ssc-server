package mude.srl.ssc.service.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.beans.Prenotazione;
import mude.srl.ssc.service.scheduler.jobs.GestionePrenotazioneRisorsaAvvio;
import mude.srl.ssc.service.scheduler.jobs.GestionePrenotazioneRisorsaChiusura;
public class SchedulerManager {

	
	public static final String RESERVATION_ID_PROP = "ID_RISORSA";
	public static final String RESERVATION_ID_GROUP_ATTIVAZIONE = "RESERVATION_ATTIVAZIONE";
	public static final String RESERVATION_ID_GROUP_TERMINAZIONE = "RESERVATION_TERMINAZIONE";
	public static final String RESERVATION_ID_GROUP_SOSPENSIONE = "RESERVATION_SOSPENSIONE";
	
	public static final short ATTESA = 0;
	public static final short AVVIATA = 1;
	public static final short TERMINATA = 2;
	public static final short INTERROTTA = 3;
	public static final short SCADUTA = 4;
	public static final short INVALID_PAYLOAD = 5;
	public static final short SOSPESA = 6;
	public static final short CANCELLATA = 7;
	
	private SimpleDateFormat triggerDateFormat  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	private static SchedulerManager instance;

	public static SchedulerManager getInstance() {
		if(instance==null) {
			instance = new SchedulerManager();
		}
		return instance;
	}

	public CronExpression get(Date star,Date end) {
		  
	      return null;   
	}
	/**
	 * Metodo comune per geneare un id univo legato per il job
	 * @param r
	 * @return
	 */
	public JobKey generaJobIdDaPrenotazione(ResourceReservation r,String group) {
		return new JobKey(r.getId().toString(),group);
	}
	
	public void avviaGestionePrenotazione(ResourceReservation r, Scheduler scheduler) throws Exception {
		
		JobDataMap data  = new JobDataMap();
		data.put(SchedulerManager.RESERVATION_ID_PROP, r);
		

		
		
		JobDetail reservetionJobAttiva = newJob(GestionePrenotazioneRisorsaAvvio.class)
				 .withIdentity(generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_ATTIVAZIONE))
				 .setJobData(data).build();
		
		JobDetail reservetionJobTermina = newJob(GestionePrenotazioneRisorsaChiusura.class)
				 .withIdentity(generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_TERMINAZIONE))
				 .setJobData(data).build();
		
		  Trigger triggerStart = newTrigger()
			      .withIdentity(triggerDateFormat.format(r.getStartTime())+r.getId().toString(), RESERVATION_ID_GROUP_ATTIVAZIONE)
			      .startAt(r.getStartTime())			      
			      .build();
		  Trigger triggerEnd= newTrigger()
			      .withIdentity(triggerDateFormat.format(r.getEndTime())+r.getId().toString(), RESERVATION_ID_GROUP_TERMINAZIONE)
			      .startAt(r.getEndTime())
			      .build();
			  // Tell quartz to schedule the job using our trigger
		      Set<Trigger> triggers  = new HashSet<Trigger>();
		      triggers.add(triggerStart);
		      triggers.add(triggerEnd);
		      
			  scheduler.scheduleJob(reservetionJobAttiva, triggerStart);
			  scheduler.scheduleJob(reservetionJobTermina, triggerEnd);
			  
	
		
	}
	
	public void sospendiJobPrenotazione(ResourceReservation r,Scheduler s) throws SchedulerException {
		
		JobKey jobAttiva = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_ATTIVAZIONE);
		JobKey jobTermina  = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_TERMINAZIONE);
		
		JobDetail jb = s.getJobDetail(jobAttiva);
		List<? extends Trigger> triggs = s.getTriggersOfJob(jobAttiva);
		/**
		 * Probabile che il server si sia riavviato senza ripristinare lo stato dei job
		 */
		
           
		
	}
	
	public void resumeJobPrenotazione(ResourceReservation r,Scheduler s) throws SchedulerException {
		JobKey job = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_TERMINAZIONE);
		JobDetail jb = s.getJobDetail(job);
		List<? extends Trigger> triggs = s.getTriggersOfJob(job);
		/**
		 * Probabile che il server si sia riavviato senza ripristinare lo stato dei job
		 */
		if(jb==null&&(triggs==null||triggs.isEmpty())){
		     
		}else
        	s.pauseJob(job);
		s.resumeJob(job);
	}
	
	public void terminaJobPrenotazione(ResourceReservation r,Scheduler s) throws SchedulerException {
		
		JobKey job = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_TERMINAZIONE);
		
		s.deleteJob(job);
		
	}
	
	public void controllaEsistenzaJobPrenotazione(ResourceReservation r,Scheduler s) throws SchedulerException {
			
		JobKey jobAvvio    = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_ATTIVAZIONE);
		JobKey jobTermina  = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_TERMINAZIONE);
		JobKey jobSospendi = generaJobIdDaPrenotazione(r,RESERVATION_ID_GROUP_SOSPENSIONE);
		
		
		s.getJobDetail(jobAvvio);
		s.getJobDetail(jobTermina);
		s.getJobDetail(jobSospendi);
		 
	}
	
	
	
	

}
