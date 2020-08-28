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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author jackarian
 */
@Component
public class LocationServiceImpl extends AbstractService<Location>  implements LocationService{

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

            }else{
                List<Location> emptyList = new ArrayList<>();
                result.setData(emptyList);
            }

        } catch (Exception ex) {
            Logger.getLogger(LocationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Response<Boolean> crea(Location location) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<Boolean> resp = new Response<>();
        EntityTransaction tx = null;
        EntityManager tm = null;
        try {
            tm = getEmForTransaction();
            tx = tm.getTransaction();
            tx.begin();
            tm.persist(location);
            tx.commit();
        }catch(Exception ex){
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
        }finally{
            if (tm != null) {
                tm.close();
            }
            closeTransaction(tx);
        }
        
        return resp;
    }

    @Override
    public Response<Boolean> aggiorna(Location location) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<Boolean> resp = new Response<>();
        EntityTransaction tx = null;
        EntityManager tm = null;
        try {
            tm = getEmForTransaction();
            tx = tm.getTransaction();
            tx.begin();
            tm.merge(location);
            tx.commit();
        }catch(Exception ex){
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
        }finally{
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
