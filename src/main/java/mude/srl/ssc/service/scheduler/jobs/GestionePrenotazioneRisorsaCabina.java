/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.scheduler.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.scheduler.SchedulerManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jack
 */
@Component
public class GestionePrenotazioneRisorsaCabina implements Job {

    @Autowired
    private PlcService plcService;
    
    private final SimpleDateFormat time_format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        
        JobKey key = jec.getJobDetail().getKey();
        
        JobDataMap dataMap = jec.getMergedJobDataMap();  // Note the difference from the previous example
        Object get = dataMap.get(SchedulerManager.RESERVATION_ID_PROP);
        if(get instanceof ResourceReservation){
            ResourceReservation r  = (ResourceReservation) get;
            
            System.out.println("Reservetion check: "+r.getPayload()+" Start: "+time_format.format(r.getStartTime())+" End: "+time_format.format(r.getEndTime()));
            
            
            long adesso = System.currentTimeMillis();
            long start  = r.getStartTime().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(adesso-start);
            r.setTotalMinutes((int)minutes);
            short last_status  = r.getStatus();
			switch (r.getStatus()) {
				case SchedulerManager.ATTESA: {
					// TO DO AVVIA CON GESTIONE PLC
					
					r.setStatus(SchedulerManager.AVVIATA);
					
					break;
				}

			}
			try {
				
				plcService.aggiornaStatoPrenotazione(r, r.getStatus());
				
			} catch (Exception e) {
				throw new JobExecutionException(e);
			}
			
			System.out.println("Elapsed minutes: "+r.getTotalMinutes());
            
        }
    }
    
    public boolean checkPlcStatus() {
    	return true;
    }
    

}
