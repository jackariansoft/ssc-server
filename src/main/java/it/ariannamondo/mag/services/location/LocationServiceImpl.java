/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.location;

import it.ariannamondo.mag.entity.Location;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.installazione.StatiInstallazione;
import it.ariannamondo.mag.rest.location.rs.LocationPagination;
import it.ariannamondo.mag.services.AbstractService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author jackarian
 */
@Component
public class LocationServiceImpl extends AbstractService<Location> implements LocationService {

    @Override
    public LocationPagination getLocations(Long page_size, Long current) {
        LocationPagination result = new LocationPagination();
        try {

            result.init();

            Query q = getEm().createQuery("SELECT COUNT(i) FROM Location i");
            Long count = (Long) q.getSingleResult();
            if (count > 0) {
                result.setTotalRows(count);
                result.setPageSize(page_size);
                result.setCurrentPage(current);
                result.setPageNumber();

                q = getEm().createQuery("SELECT i FROM Location i");

                q.setFirstResult((int) result.getOffset());
                q.setMaxResults(result.getPageSize().intValue());
                List<Location> resultList = q.getResultList();
                result.setData(resultList);

            } else {
                List<Location> emptyList = new ArrayList<>();
                result.setData(emptyList);
            }

        } catch (Exception ex) {
            Logger.getLogger(LocationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Response<Boolean> crea(Location location) throws Exception {
        //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Response<Boolean> resp = new Response<>();
        EntityTransaction tx = null;
        EntityManager tm = null;
        try {
            tm = getEmForTransaction();
            tx = tm.getTransaction();
            tx.begin();
            location.setCreationDate(new Date(System.currentTimeMillis()));
            location.setLastUpdate(new Date(System.currentTimeMillis()));
            //location.setLastupdateBy((User) principal);
            tm.persist(location);
            tx.commit();
        } catch (Exception ex) {
            //resp.setResult(Boolean.FALSE);
            //resp.setFault(true);
            //resp.setException(ex);
            Logger.getLogger(LocationService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
            
        } finally {
            if (tm != null) {
                tm.close();
            }
            closeTransaction(tx);
        }

        return resp;
    }

    @Override
    public Response<Boolean> aggiorna(Location location) {
       // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<Boolean> resp = new Response<>();
        EntityTransaction tx = null;
        EntityManager tm = null;
        try {
            tm = getEmForTransaction();
            tx = tm.getTransaction();
            tx.begin();
            Query q = tm.createQuery(
                    "UPDATE Location i "
                    + "SET i.ipAddress = :ipAddress,\n"
                    + "i.ipPortService = :ipPortService,\n"
                    + "i.lastUpdate = :lastupdate,\n"
                    + "i.reference = :reference,\n"
                    + "i.lastupdateBy = :lastupdateBy,\n"
                    + "i.stato = :stato,\n"
                    + "i.title = :title,\n"
                    + "i.active = :active,\n"
                    + "i.login = :login,\n"
                    + "i.password = :password,\n"
                    + "i.hotspotTag = :hotspotTag \n"
                    + " WHERE i.id = :id");

            q.setParameter("ipAddress", location.getIpAddress());
            q.setParameter("ipPortService", location.getIpPortService());
            q.setParameter("lastupdate", new Date(System.currentTimeMillis()));
            q.setParameter("reference", location.getReference());
            q.setParameter("lastupdateBy", location.getLastupdateBy());
            q.setParameter("stato", location.getStato());
            q.setParameter("title", location.getTitle());
            q.setParameter("active", location.getActive());
            q.setParameter("login", location.getLogin());

            
            q.setParameter("password", location.getPassword());
            q.setParameter("hotspotTag", location.getHotspotTag());

            q.setParameter("id", location.getId());
            q.executeUpdate();
            tx.commit();
            //getEm().refresh(location);
            resp.setResult(Boolean.TRUE);
        } catch (Exception ex) {
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
            if (tx != null&&tx.isActive()) {
                tx.setRollbackOnly();
            }
            Logger.getLogger("LocationService").log(Level.SEVERE, "", ex);
        } finally {
            if (tm != null) {
                tm.close();
            }
            closeTransaction(tx);
        }
        return resp;
    }

    @Override
    public Response<Boolean> elimina(Long id) {
        Response<Boolean> resp = new Response<>();
        try {
            Location find = getEm().find(Location.class, id);
            if (find != null) {
                find.setStato(StatiInstallazione.ELIMINATA.getStato());
                getEm().merge(find);
                resp.setResult(Boolean.TRUE);
            }
        } catch (Exception ex) {
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
        }
        return resp;
    }

}
