/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.utils.ResourceStatus;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.AbstractService;
import mude.srl.ssc.service.log.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jack
 */
@Repository
public class PlcServiceImpl extends AbstractService implements PlcService {

    @Autowired
    LoggerService loggerService;
    
    @Override
    public Plc getPlcByUID(String uid) {
        Plc resp = null;
        EntityManager em = null;

        try {
            em = getEm();
            TypedQuery<Plc> q = em.createQuery("SELECT p FROM Plc p WHERE p.uid = :uid ", Plc.class);
            q.setParameter("uid", uid);
            resp = q.getSingleResult();

        } catch (NonUniqueResultException | NoResultException ex) {
           loggerService.logException(Level.SEVERE, "getPlcByUID", ex);

        } catch (Exception ex) {
            loggerService.logException(Level.SEVERE, "getPlcByUID", ex);
        } finally {

        }
        return resp;
    }

    @Override
    public Resource getReourceByPlcAndTag(Plc plc, String tag) {
       
        Resource resource = null;
        EntityManager em = null;
        try {
            em = getEm();
            TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE r.plc = :plc AND r.tag =:tag ", Resource.class);
            q.setParameter("plc", plc);
            q.setParameter("tag", tag);
            resource = q.getSingleResult();

        } catch (NonUniqueResultException | NoResultException ex) {
            loggerService.logException(Level.SEVERE, "getPlcByUID", ex);

        } catch (Exception ex) {
           loggerService.logException(Level.SEVERE, "getPlcByUID", ex);
        } finally {

        }

        return resource;
    }

    @Override
    public ResourceReservation controllaPerAvvio(Resource r, RequestCommandResourceReservation request) throws Exception {
        ResourceReservation reservation = null;
        EntityManager em = null;
        EntityTransaction tx = null;

        em = getEmForTransaction();
        if (em == null) {
            throw new SQLException("No database connection");
        }
        tx = em.getTransaction();
        if (tx == null) {
            throw new SQLException("No database transaction available");

        }
        try {
            tx.begin();
            TypedQuery<Long> q = em.createQuery(
                     "SELECT COUNT(r) "
                   + "FROM ResourceReservation r  "
                   + "WHERE r.resource = :resource AND  r.status IN (:status1,:status2) "
                    + "AND ( ( r.startTime BETWEEN :start AND :end ) "
                    + "OR (r.endTime BETWEEN :start AND :end) "
                    + "OR (r.startTime = :start AND r.endTime = :end)) ", Long.class);
            
            q.setParameter("resource", r);
            q.setParameter("status1", ResourceStatus.AVVIATA.getStatus());
            q.setParameter("status2", ResourceStatus.ATTESA.getStatus());
            q.setParameter("start", request.getStart());
            q.setParameter("end", request.getEnd());
            
            Long countAttive = q.getSingleResult();
            if(countAttive==0){
                
                
                
                
                reservation  = new ResourceReservation();
                
                reservation.setRequestTime(new Date(System.currentTimeMillis()));
                reservation.setStartTime(request.getStart());               
                reservation.setEndTime(request.getEnd());
                reservation.setResource(r);
                reservation.setPayload(request.getPayload());
                reservation.setStatus(ResourceStatus.ATTESA.getStatus());
                em.persist(reservation);
                
                
            }
            tx.commit();
                    
        } catch (Exception ex) {
            closeTransaction(tx);
            loggerService.logException(Level.SEVERE, "Errore creazione prenotazione", ex);
            throw  ex;
            
        } finally {
          
                em.close();
            
        }

        return reservation;
    }

    /**
     * Aggiornamento stato prenotazione
     */    
	@Override
	public Response<ResourceReservation> aggiornaStatoPrenotazione(ResourceReservation r, Short status) throws Exception {
		Response<ResourceReservation> resp  = new Response<ResourceReservation>();
		EntityManager em = null;
        EntityTransaction tx = null;

        em = getEmForTransaction();
        if (em == null) {
            throw new SQLException("No database connection");
        }
        tx = em.getTransaction();
        if (tx == null) {
            throw new SQLException("No database transaction available");

        }
        try {
            tx.begin();
            Query q = em.createQuery("UPDATE ResourceReservation r SET r.status = :status,r.totalMinutes = :totalMinutes ");
            q.setParameter("status", status);
            q.setParameter("totalMinutes", r.getTotalMinutes());
            q.executeUpdate();            
            tx.commit();
        }catch (Exception e) {
        	closeTransaction(tx);
            loggerService.logException(Level.SEVERE, "Errore aggiornamento prenotazione", e);
			throw e;
			
		}finally {
			em.clear();
			em.close();
			
		}
		return resp;
		
	}

	@Override
	public Resource getReourceByPlcAndTag(String plc_uid, String tag) throws Exception {
		 
		Resource resource = null;
        EntityManager em = null;
        try {
            em = getEm();
            TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE r.plc.uid = :plc AND r.tag =:tag ", Resource.class);
            q.setParameter("plc", plc_uid);
            q.setParameter("tag", tag);
            resource = q.getSingleResult();

        } catch (NonUniqueResultException | NoResultException ex) {
            loggerService.logException(Level.SEVERE, "getPlcByUID", ex);

        } catch (Exception ex) {
           loggerService.logException(Level.SEVERE, "getPlcByUID", ex);
        } finally {

        }

        return resource;
	}

}
