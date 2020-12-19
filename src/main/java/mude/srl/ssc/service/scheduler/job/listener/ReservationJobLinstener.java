package mude.srl.ssc.service.scheduler.job.listener;

import java.util.logging.Level;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.scheduler.SchedulerManager;

public class ReservationJobLinstener implements JobListener{

	private static final String GLOBAL_JOB_RESERVATION =  "ReservationJobListener";
	
	@Autowired
	private LoggerService loggerService;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return GLOBAL_JOB_RESERVATION;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		Object r =(ResourceReservation)context.getJobDetail().getJobDataMap().get(SchedulerManager.RESERVATION_ID_PROP);
		loggerService.logInfo(Level.INFO, "jobToBeExecuted", r);
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		Object r =(ResourceReservation)context.getJobDetail().getJobDataMap().get(SchedulerManager.RESERVATION_ID_PROP);
		loggerService.logInfo(Level.INFO, "jobExecutionVetoed", r);
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		Object r =(ResourceReservation)context.getJobDetail().getJobDataMap().get(SchedulerManager.RESERVATION_ID_PROP);
		loggerService.logInfo(Level.INFO, "jobWasExecuted", r);
		
	}

}
