package mude.srl.ssc.service.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.scheduler.jobs.GestionePrenotazioneRisorsaCabina;
public class SchedulerManager {

	
	public static final String RESERVATION_ID_PROP = "ID_RISORSA";
	public static final String RESERVATION_ID_GROUP = "RESERVATION";
	
	public static final short ATTESA = 0;
	public static final short AVVIATA = 1;
	public static final short TERMINATA = 2;
	public static final short INTERROTTA = 3;
	public static final short SCADUTA = 4;
	public static final short INVALID_PAYLOAD = 5;
	public static final short SOSPESA = 6;
	
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
	public void avviaGestionePrenotazione(ResourceReservation r, Scheduler scheduler) throws Exception {
		
		JobDataMap data  = new JobDataMap();
		data.put(SchedulerManager.RESERVATION_ID_PROP, r);
		
		
		 Calendar start  = Calendar.getInstance(Locale.ITALIAN);
		 start.setTime(r.getStartTime());
		 start.add(Calendar.MINUTE, 10);
		 
		 Calendar end  = Calendar.getInstance(Locale.ITALIAN);
		 end.setTime(r.getEndTime());
		 end.add(Calendar.MINUTE,1);
		
		
		JobDetail reservetionJob = newJob(GestionePrenotazioneRisorsaCabina.class)
				 .withIdentity(new JobKey(r.getId().toString(),RESERVATION_ID_GROUP))
				 .setJobData(data).build();
		
		  Trigger triggerStart = newTrigger()
			      .withIdentity(r.getStartTime().toString(), RESERVATION_ID_GROUP)
			      .startAt(r.getStartTime())
			      //.withSchedule(simpleSchedule()
			      //.withRepeatCount(1))
			      //.withSchedule(cronSchedule(get(r.getStartTime(),r.getEndTime())))
			      //.endAt(Date.from(start.toInstant()))
			      .build();
		  Trigger triggerEnd= newTrigger()
			      .withIdentity(r.getEndTime().toString(), RESERVATION_ID_GROUP)
			      .startAt(r.getEndTime())
			      //.withSchedule(simpleSchedule()
			     // .withRepeatCount(1))
			      //.withSchedule(cronSchedule(get(r.getStartTime(),r.getEndTime())))
			      //.endAt(Date.from(end.toInstant()))
			      .build();
			  // Tell quartz to schedule the job using our trigger
		      Set<Trigger> triggers  = new HashSet<Trigger>();
		      triggers.add(triggerStart);
		      triggers.add(triggerEnd);
		      
			  scheduler.scheduleJob(reservetionJob, triggers,false);
			  
	
		
	}
	
	
	
	

}
