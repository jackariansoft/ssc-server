/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.QrcodeTest;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.beans.Prenotazione;
import mude.srl.ssc.entity.utils.Request;
import mude.srl.ssc.entity.utils.ResourceStatus;
import mude.srl.ssc.entity.utils.Response;

import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.AbstractService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.payload.exception.ReservationIntervalException;
import mude.srl.ssc.service.scheduler.trigger.listener.ReservetionTriggerListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jack
 */
@Repository
public class PlcServiceImpl extends AbstractService<Plc> implements PlcService {

	@Autowired
	LoggerService loggerService;

	@Override
	public Plc getPlcByUID(String uid) throws Exception {
		Plc resp = null;
		EntityManager em = null;

		try {
			em = getEm();
			TypedQuery<Plc> q = em.createQuery("SELECT p FROM Plc p WHERE p.uid = :uid ", Plc.class);
			q.setParameter("uid", uid);
			resp = q.getSingleResult();

		} catch (NonUniqueResultException | NoResultException ex) {
			loggerService.logWarning(Level.WARNING, "getPlcByUID: Nessun plc trovato con inpu: " + uid);

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getPlcByUID", ex);
			throw ex;
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
			TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE r.plc = :plc AND r.tag =:tag ",
					Resource.class);
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
	public Resource getReourceByTag(String tag) throws Exception {
		Resource resource = null;
		EntityManager em = null;
		try {
			em = getEm();
			TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE  r.tag =:tag ", Resource.class);

			q.setParameter("tag", tag);
			resource = q.getSingleResult();

		} catch (NonUniqueResultException | NoResultException ex) {
			loggerService.logException(Level.SEVERE, "getReourceByTag", ex);

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getReourceByTag", ex);
		} finally {

		}

		return resource;
	}

	@Override
	public Response<ResourceReservation> controllaPerAvvio(Resource r, RequestCommandResourceReservation request)
			throws Exception {

		Response<ResourceReservation> resp = new Response<ResourceReservation>();

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
			TypedQuery<Long> q = em.createQuery("SELECT COUNT(r) " + "FROM ResourceReservation r  "
					+ "WHERE r.resource = :resource AND  r.status IN (:status1,:status2,:status3) "
					+ "AND ( ( r.startTime BETWEEN :start AND :end ) " + "OR (r.endTime BETWEEN :start AND :end) "
					+ "OR (r.startTime = :start AND r.endTime = :end) OR (:start BETWEEN r.startTime AND  r.endTime ) "
					+ "OR (:end BETWEEN r.startTime AND  r.endTime )) ", Long.class);
			//q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			q.setParameter("resource", r);
			q.setParameter("status1", ResourceStatus.AVVIATA.getStatus());
			q.setParameter("status2", ResourceStatus.ATTESA.getStatus());
			q.setParameter("status3", ResourceStatus.SOSPESA.getStatus());
			q.setParameter("start", request.getStart());
			q.setParameter("end", request.getEnd());

			Long countAttive = q.getSingleResult();
			if (countAttive == 0) {

				reservation = new ResourceReservation();

				reservation.setRequestTime(new Date(System.currentTimeMillis()));
				reservation.setStartTime(request.getStart());
				reservation.setEndTime(request.getEnd());
				reservation.setResource(r);
				reservation.setPayload(request.getPayload());
				reservation.setStatus(ResourceStatus.ATTESA.getStatus());
				resp.setResult(reservation);
				em.persist(reservation);

			} else {
				/**
				 * TODO decidere come gestire prenotazione in overlap
				 */
				resp.setErrorDescription("Intervallo prenotazione non valido");
				resp.setFault(true);
				resp.setErrorMessage(Response.RESERVATION_INTERVAL_OVERLAP_MESSAGE);
				resp.setErrorType(Response.RESERVATION_INTERVAL_OVERLAP);				
				resp.setException(new ReservationIntervalException("Intervallo prenotazione : "+request));
			}
			tx.commit();

		} catch (Exception ex) {
			closeTransaction(tx);
			loggerService.logException(Level.SEVERE, "Errore creazione prenotazione", ex);
			throw ex;

		} finally {

			em.close();

		}

		return resp;
	}

	@Override
	public Response<Long> controllaPrenotazioniAttive(Resource r) throws Exception {
		Response<Long> resp = new Response<Long>();

		
		EntityManager em = null;
		EntityTransaction tx = null;

		em = getEmForTransaction();
		if (em == null) {
			throw new SQLException("No database connection");
		}
	
		try {
		
			TypedQuery<Long> q = em.createQuery("SELECT COUNT(r) " + "FROM ResourceReservation r  "
					+ "WHERE r.resource = :resource AND  r.status IN (:status1,:status2,:status3) "
					+ "AND ( :current_time BETWEEN r.startTime AND r.endTime )  ", Long.class);

			q.setParameter("resource", r);
			q.setParameter("current_time", new Timestamp(System.currentTimeMillis()));
			q.setParameter("status1", ResourceStatus.AVVIATA.getStatus());
			q.setParameter("status2", ResourceStatus.ATTESA.getStatus());
			q.setParameter("status3", ResourceStatus.SOSPESA.getStatus());
		

			Long countAttive = q.getSingleResult();
			resp.setResult(countAttive);

		} catch (Exception ex) {
			closeTransaction(tx);
			loggerService.logException(Level.SEVERE, "Errore creazione prenotazione", ex);
			throw ex;

		} finally {

			em.close();

		}

		return resp;
	}

	/**
	 * Aggiornamento stato prenotazione
	 */
	@Override
	public Response<ResourceReservation> aggiornaStatoPrenotazione(ResourceReservation r, Short status)
			throws Exception {
		Response<ResourceReservation> resp = new Response<ResourceReservation>();
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
			Query q = em.createQuery(
					"UPDATE ResourceReservation r SET r.status = :status,r.totalMinutes = :totalMinutes WHERE r.id = :id ");
			q.setParameter("status", status);
			q.setParameter("totalMinutes", r.getTotalMinutes());
			q.setParameter("id", r.getId());
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			closeTransaction(tx);
			loggerService.logException(Level.SEVERE, "Errore aggiornamento prenotazione", e);
			throw e;

		} finally {
			em.clear();
			em.close();

		}
		return resp;

	}

	@Override
	public Response<ResourceReservation> aggiornaStatoPrenotazione(Long id, Short status) throws Exception {
		Response<ResourceReservation> resp = new Response<ResourceReservation>();
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
			Query q = em.createQuery("UPDATE ResourceReservation r SET r.status = :status WHERE r.id = :id");
			q.setParameter("status", status);
			q.setParameter("id", id);
			q.executeUpdate();
			ResourceReservation r = em.find(ResourceReservation.class, id);
			resp.setResult(r);
			tx.commit();
		} catch (Exception e) {
			closeTransaction(tx);
			loggerService.logException(Level.SEVERE, "Errore aggiornamento prenotazione", e);
			throw e;

		} finally {
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
			TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE r.plc.uid = :plc AND r.tag =:tag ",
					Resource.class);
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

	@Override
	public Plc getPlcById(Long id) throws Exception {
		Plc plc = null;
		EntityManager em = null;
		try {
			em = getEm();
			TypedQuery<Plc> q = em.createQuery("SELECT plc FROM Plc plc WHERE plc.id = :id ", Plc.class);
			q.setParameter("id", id);

			plc = q.getSingleResult();

		} catch (NonUniqueResultException | NoResultException ex) {

			loggerService.logException(Level.SEVERE, "getPlcById", ex);

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getPlcById", ex);
		} finally {

		}

		return plc;
	}

	@Override
	public Resource getReourceById(Long id) {
		Resource r = null;
		EntityManager em = null;
		try {
			em = getEm();

			TypedQuery<Resource> q = em.createQuery("SELECT r FROM Resource r WHERE r.id = :id ", Resource.class);
			q.setParameter("id", id);

			r = q.getSingleResult();

		} catch (NonUniqueResultException | NoResultException ex) {

			loggerService.logException(Level.SEVERE, "getPlcById", ex);

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getPlcById", ex);
		} finally {

		}
		return r;
	}

	@Override
	public Resource getResourceByReservetionId(Long id) {
		Resource r = null;
		EntityManager em = null;
		try {
			em = getEm();

			TypedQuery<ResourceReservation> q = em.createQuery("SELECT r FROM ResourceReservation r WHERE r.id = :id ",
					ResourceReservation.class);
			q.setParameter("id", id);

			ResourceReservation rr = q.getSingleResult();
			r = rr.getResource();

		} catch (NonUniqueResultException | NoResultException ex) {

			loggerService.logException(Level.SEVERE, "getPlcById", ex);

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getPlcById", ex);
		} finally {

		}
		return r;
	}

	@Override
	public List<ResourceReservation> getReservation(Request req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Resource> getResource(Request req) throws Exception {
		List<Resource> resp = new ArrayList<Resource>();
		try {
			TypedQuery<Resource> q = getEm().createQuery("SELECT r FROM Resource r", Resource.class);

			List<Resource> rl = q.getResultList();

			if (rl != null && !rl.isEmpty()) {
				resp.addAll(rl);
			}

		} catch (Exception e) {
			loggerService.logException(Level.SEVERE, "getResource", e);
			throw e;
		}

		return resp;
	}

	@Override
	public List<Prenotazione> getReservationBeans(Request req) throws Exception {
		List<Prenotazione> res = new ArrayList<Prenotazione>();
		String sql_init = "select\r\n" + "	rr.id,\r\n" + "	payload,\r\n" + "	request_time,\r\n" + "	resource,\r\n"
				+ "	start_time,\r\n" + "	end_time,\r\n" + "	status,\r\n" + "	total_minutes,\r\n"
				+ "	received_interrupt,\r\n" + "	receved_interrupt_at,\r\n" + "	interrupt_motivation,\r\n"
				+ "	lastupdate,\r\n" + "	schedule_id,\r\n" + "	r.reference,\r\n" + "	r.tag,\r\n"
				+ "	p2.id as plc_ref,\r\n" + "	p2.ip_address \r\n" + "from\r\n" + "	resource_reservation rr\r\n"
				+ "join resource r on\r\n" + "	rr.resource = r.id\r\n" + "join plc p2 on\r\n" + "	r.plc = p2.id\r\n"
				+ "	order by request_time desc";
		try {

			Query q = getEm().createNativeQuery(sql_init, "PrenotazioniXML");

			List<Prenotazione> rl = q.getResultList();
			if (!rl.isEmpty()) {
				res.addAll(rl);
			}

		} catch (NonUniqueResultException | NoResultException ex) {

			loggerService.logException(Level.SEVERE, "getPlcById", ex);
			throw ex;
		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, "getPlcById", ex);
			throw ex;
		} finally {

		}
		return res;
	}

	@Override
	public QrcodeTest getQrcodeTestById(String id) throws Exception {
		return getEm().find(QrcodeTest.class, id);
	}

	@Override
	public List<Plc> getPlcList(Request req) throws Exception {
		List<Plc> res = new ArrayList<Plc>();
		try {
			TypedQuery<Plc> q = getEm().createQuery("SELECT p FROM Plc p", Plc.class);
			List<Plc> list = q.getResultList();
			if (list != null && !list.isEmpty()) {
				res = list;
			}

		} catch (Exception e) {
			loggerService.logException(Level.SEVERE, "getPlcList", e);
			throw e;
		}
		return res;
	}

	@Override
	public Response<ResourceReservation> cercaPerIdPrenotazione(Long id) throws Exception {
		      
		Response<ResourceReservation> resp  = new Response<ResourceReservation>();
		ResourceReservation r = getEm().find(ResourceReservation.class, id);
	    resp.setResult(r);
		return resp;
	}

}
