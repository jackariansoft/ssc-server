/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.mail.LoggerSSC;
import mude.srl.ssc.service.AbstractService;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jack
 */
@Repository
public class PlcServiceImpl extends AbstractService implements PlcService {

    @Override
    public Plc getPlcByUID(String uid) {
        Plc resp = null;
        EntityManager em = null;

        try {
            em  = getEm();
            TypedQuery<Plc> q = em.createQuery("SELECT p FROM Plc p WHERE p.uid = :uid ", Plc.class);
            q.setParameter("uid", uid);
            resp = q.getSingleResult();

        } catch (NonUniqueResultException | NoResultException ex) {
            LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "getPlcByUID", ex);
        } catch (Exception ex) {
            LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "getPlcByUID", ex);
        } finally {
            
        }
        return resp;
    }

}
