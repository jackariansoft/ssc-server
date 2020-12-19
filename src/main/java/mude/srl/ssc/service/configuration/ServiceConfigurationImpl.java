package mude.srl.ssc.service.configuration;

import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mude.srl.ssc.entity.Configuration;
import mude.srl.ssc.service.AbstractService;
import mude.srl.ssc.service.log.LoggerService;


@Component
public class ServiceConfigurationImpl extends AbstractService<Configuration> implements ConfigurationService{

	
	 @Autowired
	 LoggerService loggerService;
	 
	@Override
	public Configuration getCurrentValidConfig() throws Exception {
		Configuration resp = null;
	        EntityManager em = null;

	        try {
	            em = getEm();
	            TypedQuery<Configuration> q = em.createQuery("SELECT p FROM Configuration p WHERE p.valida = TRUE ", Configuration.class);
	           
	            resp = q.getSingleResult();

	        } catch (NonUniqueResultException | NoResultException ex) {
	           loggerService.logWarning(Level.WARNING, "getCurrentValidConfig: Nessuna configurazione valida");
	           throw ex;

	        } catch (Exception ex) {
	            loggerService.logException(Level.SEVERE, "getCurrentValidConfig", ex);
	            throw ex;
	        } finally {

	        }
	        return resp;
	}

}
