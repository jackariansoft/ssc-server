/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.mail;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import mude.srl.ssc.entity.Channel;
import mude.srl.ssc.entity.Email;
import mude.srl.ssc.entity.utils.ResponseRequest;
import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.service.AbstractService;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jack
 */
@Repository
public class EmailDelegate extends AbstractService implements EmailService {

    public EmailDelegate() {
        System.out.println("Attivazione servizio recupero configurazione email");
    }
    
    
    

    @Override
    public ResponseRequest<Email> getAccountsByChannerl(Channel c) {
        ResponseRequest<Email> resp = new ResponseRequest<>();
        Email res = null;
        EntityManager em = null;
        try {
            em  = getEm();
            if (em != null) {
                Query q = em.createQuery("SELECT e FROM Email e WHERE e.valido = 1 and e.emailPK.channel = :channel AND e.isDefault =TRUE");
                q.setParameter("channel", c.getId());
                List<Email> resultList = q.getResultList();
                if (resultList != null && !resultList.isEmpty()) {
                    res = resultList.get(0);
                    resp.setData(res);
                }
            } else {
                resp.setError(true);
                resp.setErrorCode(LoggerSSC.NO_CONNECTION);
                resp.setErrorMessage("NO webshopping database connection");

            }
        } catch (PersistenceException ex) {

            resp.setError(true);
            resp.setErrorCode(LoggerSSC.SQL_EXCEPTION);
            resp.setErrorMessage("ERROR RETRIVING DATA FROM METHOD: getAccountsByChannerl");
            resp.setEx(ex);

        } catch (Exception ex) {

            resp.setError(true);
            resp.setErrorCode(LoggerSSC.GENERAL_ERROR);
            resp.setErrorMessage("GENERAL ERROR");
            resp.setEx(ex);

        }

        return resp;
    }

    @Override
    public ResponseRequest<Email> getDefault() {
        ResponseRequest<Email> resp = new ResponseRequest<>();
        Email res = null;

        try {
            EntityManager em = getEm();
            if (em != null) {
                Query q = em.createQuery("SELECT e FROM Email e WHERE e.isDefault = TRUE");
                List<Email> resultList = q.getResultList();
                if (resultList != null && !resultList.isEmpty()) {
                    res = resultList.get(0);
                    resp.setData(res);
                }
            } else {
                resp.setError(true);
                resp.setErrorCode(LoggerSSC.NO_CONNECTION);
                resp.setErrorMessage("NO webshopping database connection");

            }
        } catch (PersistenceException ex) {

            resp.setError(true);
            resp.setErrorCode(LoggerSSC.SQL_EXCEPTION);
            resp.setErrorMessage("ERROR RETRIVING DATA FROM METHOD: getDefault");
            resp.setEx(ex);

        } catch (Exception ex) {
            resp.setError(true);
            resp.setErrorCode(LoggerSSC.GENERAL_ERROR);
            resp.setErrorMessage("GENERAL ERROR");
            resp.setEx(ex);
        }
        return resp;
    }

}
