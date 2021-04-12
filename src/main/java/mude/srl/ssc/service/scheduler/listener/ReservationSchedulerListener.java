package mude.srl.ssc.service.scheduler.listener;

import java.util.logging.Level;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;

import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.resource.ResourceService;
import mude.srl.ssc.service.scheduler.SchedulerManager;

public class ReservationSchedulerListener implements SchedulerListener {

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private LoggerService loggerService;
	
	@Autowired
    private PlcService plcService;
	
	@Override
	public void jobScheduled(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		
		
		try {
			Long id = Long.parseLong(jobKey.getName());
//			resourceService.disabilitaRisorsaByReservationId(jobKey.getName());
//			plcService.aggiornaStatoPrenotazione(id,SchedulerManager.TERMINATA);
			loggerService.logInfo(Level.INFO, "Finalizzazione Prenotazione:{0}", id);				
			   
		} catch (Exception e) {
			loggerService.logException(Level.SEVERE,"ERRORE DISABILITAZIONE RISORSA", e);
		}
		
		
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobsPaused(String jobGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobsResumed(String jobGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		
		loggerService.logException(Level.SEVERE,"ERORRE SCHEDULER GESTIONE RISORSE: "+msg, cause);
		
	}

	@Override
	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerStarting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulingDataCleared() {
		// TODO Auto-generated method stub
		
	}

}
