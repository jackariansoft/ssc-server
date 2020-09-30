package mude.srl.ssc.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.TriggerListener;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import mude.srl.ssc.service.scheduler.job.listener.ReservationJobLinstener;
import mude.srl.ssc.service.scheduler.job.listener.ReservationSchedulerListener;
import mude.srl.ssc.service.scheduler.jobs.utils.ReservationJobFactory;
import mude.srl.ssc.service.scheduler.trigger.listener.ReservetionTriggerListener;

@Configuration
public class SchedulerConfiguration {

	@Autowired
    private ReservationJobFactory reservationJobFactory;
	
	@Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
	
	@Bean(name = "scheduler")
    public Scheduler scheduler() throws Exception {
        return schedulerFactoryBean().getScheduler();
    }
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setOverwriteExistingJobs(true);

        // Delayed startup
        //factory.setStartupDelay(20);

        // Loading quartz data source configuration
        factory.setQuartzProperties(quartzProperties());

        // Custom Job Factory for Spring Injection
        factory.setJobFactory(reservationJobFactory);
        /*********Global listener configuration******************/
        TriggerListener reservationListener = new ReservetionTriggerListener();
        capableBeanFactory.autowireBean(reservationListener);
        JobListener reservationJobListener = new ReservationJobLinstener();
        capableBeanFactory.autowireBean(reservationJobListener);
        factory.setGlobalTriggerListeners(reservationListener);
        factory.setGlobalJobListeners(reservationJobListener);
        factory.setSchedulerListeners(new ReservationSchedulerListener());
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
      
            System.out.println("testing environment quartz To configure");
            propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        
        //Properties in quartz.properties are read and injected before the object is initialized
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz Initialization listener
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

}
