package mude.srl.ssc.service.scheduler.trigger.listener;

import java.util.logging.Level;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.quartz.TriggerListener;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.scheduler.SchedulerManager;

@Component
public class ReservetionTriggerListener implements TriggerListener{

	@Autowired
    private PlcService plcService;
	
	@Autowired
	private LoggerService loggerService;
	
	
	private String name;
	
	public ReservetionTriggerListener() {
		super();
		this.name = "TRIGGER_RESERVATION";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		   
		   Object mappedReservation = context.getJobDetail().getJobDataMap().get(SchedulerManager.RESERVATION_ID_PROP);
		   if(mappedReservation instanceof ResourceReservation) {
			   ResourceReservation r = (ResourceReservation) mappedReservation;
			   try {
				plcService.aggiornaStatoPrenotazione(r,SchedulerManager.TERMINATA);
				loggerService.logInfo(Level.INFO, "Finalizzazione Prenotazione:{0}", r);
			} catch (Exception e) {
				loggerService.logException(Level.SEVERE,"Errore chiusura prenotazione: "+r, e);
			}
		   }
		
	}

}
