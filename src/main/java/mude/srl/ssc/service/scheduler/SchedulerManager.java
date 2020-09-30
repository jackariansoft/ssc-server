package mude.srl.ssc.service.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.scheduler.jobs.GestionePrenotazioneRisorsaCabina;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;


import org.quartz.JobDataMap;
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

	public void avviaGestionePrenotazione(ResourceReservation r, Scheduler scheduler) throws Exception {
		
		JobDataMap data  = new JobDataMap();
		data.put(SchedulerManager.RESERVATION_ID_PROP, r);
		
		JobDetail reservetionJob = newJob(GestionePrenotazioneRisorsaCabina.class)
				 .withIdentity(new JobKey(r.getId().toString(),RESERVATION_ID_GROUP))
				 .setJobData(data).build();
		Trigger trigger = newTrigger()
			      .withIdentity(r.getId().toString(), RESERVATION_ID_GROUP)
			      .startAt(r.getStartTime())
			      .withSchedule(simpleSchedule()
			          .withIntervalInSeconds(30)
			          .repeatForever())
			      .endAt(r.getEndTime())
			      .build();

			  // Tell quartz to schedule the job using our trigger
			  scheduler.scheduleJob(reservetionJob, trigger);
				
	
		
	}
	
	
	
	

}
