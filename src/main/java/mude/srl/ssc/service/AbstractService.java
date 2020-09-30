/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service;

import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import mude.srl.ssc.entity.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 *
 * @author Jack
 * @param <T>
 */
public class AbstractService<T> {

    /**
     *
     */
//    @PersistenceContext(unitName =PERSISTENCE_UNIT)
    private static EntityManager em;

    @Autowired
    protected JpaTransactionManager userTransactionManager;
    private static EntityManagerFactory entityManagerFactory;

    public EntityManager getEm() throws Exception {
        if (em == null) {
            if(entityManagerFactory==null)
             entityManagerFactory = userTransactionManager.getEntityManagerFactory();
            if (entityManagerFactory != null) {
                em = entityManagerFactory.createEntityManager();
            }else{
                throw new Exception("Nessuan risorsa database");
            }
        }
        return em;
    }
    public EntityManager getEmForTransaction() throws Exception {
        EntityManager emt = null;
        if(entityManagerFactory==null)
            entityManagerFactory = userTransactionManager.getEntityManagerFactory();
        
            
                emt = entityManagerFactory.createEntityManager();
            
        
        return emt;
    }

    protected boolean constraintValidationsDetected(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());

            }
            return true;
        } else {
            return false;
        }
    }
    
    public void closeTransaction(EntityTransaction tx){
        if(tx!=null&&tx.isActive()){
            tx.rollback();
        }
    }
    
    
    public void setErrorResponse(Response<?> resp, Exception ex) {
        resp.setFault(true);
        resp.setErrorType(Response.INTERNAL_SERVER_ERROR);
        resp.setErrorDescription(Response.ERROR_MG_INTERNAL_SERVER_ERROR);
        resp.setErrorMessage(ex.getMessage());
    }
}
