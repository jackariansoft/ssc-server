/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.scheduler.jobs;

import java.text.SimpleDateFormat;
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
        }
    }

}
