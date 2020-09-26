/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.scheduler;

import java.util.Date;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.service.scheduler.jobs.GestionePrenotazioneRisorsaCabina;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 *
 * @author Jack
 */
public class SchedulerManager {

    //private final StdSchedulerFactory sf;
    //private final Scheduler scheduler;
    public static final String RESERVATION_GROUP = "RESERVESION";
    public static final String RESERVATION_ID_PROP = "RESERVESION_ID";

    private static SchedulerManager instance = null;

    private SchedulerManager() throws SchedulerException {
//        sf = new StdSchedulerFactory();
//        scheduler = sf.getScheduler();
//        scheduler.start();
    }

    public static synchronized SchedulerManager getInstance() throws SchedulerException {
        if (instance == null) {
            instance = new SchedulerManager();

        }
        return instance;
    }

    public void avviaGestionePrenotazione(ResourceReservation reservation,Scheduler scheduler) throws SchedulerException {

         if(scheduler.isInStandbyMode()){
            scheduler.start();
        }
        JobDataMap resMap  = new JobDataMap();
        resMap.put(RESERVATION_ID_PROP, reservation);
        JobDetail job = newJob(GestionePrenotazioneRisorsaCabina.class)
                .withIdentity(reservation.getPayload(),RESERVATION_GROUP) // name "myJob", group "group1"                
                .usingJobData(resMap)
                .build();
        
        Trigger trigger = newTrigger()
                .withIdentity(reservation.getPayload(), RESERVATION_GROUP)
                .startAt(new Date(System.currentTimeMillis())) // if a start time is not given (if this line were omitted), "now" is implied
                .withSchedule(simpleSchedule()
                        
                        //.withIntervalInMinutes(15)
                        .withIntervalInSeconds(10)                        
                        .withRepeatCount(1))
                .endAt(reservation.getEndTime())// note that 10 repeats will give a total of 11 firings
                .forJob(job) // identify job with handle to its JobDetail itself                   
                .build();
        scheduler.scheduleJob(job, trigger);
       
        
    }

}
