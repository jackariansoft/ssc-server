package mude.srl.ssc.service.scheduler.jobs.utils;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class ReservationJobFactory extends AdaptableJobFactory {

	@Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // Calling methods of parent classes
        Object jobInstance = super.createJobInstance(bundle);
        // Injection
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
