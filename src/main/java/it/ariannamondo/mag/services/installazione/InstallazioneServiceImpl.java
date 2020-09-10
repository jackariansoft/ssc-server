/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.installazione;

import it.ariannamondo.mag.entity.Installazione;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.installazione.StatiInstallazione;
import it.ariannamondo.mag.rest.installazione.rs.InstallazionePagination;
import it.ariannamondo.mag.services.AbstractService;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InstallazioneServiceImpl extends AbstractService<Installazione> implements InstallazioneService {

    @Override
    public InstallazionePagination getInstallation(Long page_size, Long current) {
        InstallazionePagination result = new InstallazionePagination();
        try {

            result.init();

            Query q = getEm().createQuery("SELECT COUNT(i) FROM Installazione i");
            Long count = (Long) q.getSingleResult();
            if (count > 0) {
                result.setTotalRows(count);
                result.setPageSize(page_size);
                result.setCurrentPage(current);
                result.setPageNumber();

                q = getEm().createQuery("SELECT i FROM Installazione i");

                q.setFirstResult((int) result.getOffset());
                q.setMaxResults(result.getPageSize().intValue());
                List<Installazione> resultList = q.getResultList();
                result.setData(resultList);

            }

        } catch (Exception ex) {
            Logger.getLogger(InstallazioneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Response<Boolean> update(Installazione installazione) {

        //Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Response<Boolean> resp = new Response<>();
        EntityTransaction tx = null;
        EntityManager tm = null;
        try {
            tm = getEmForTransaction();
            tx = tm.getTransaction();
            tx.begin();
            Query q = tm.createQuery(
                    "UPDATE Installazione i "
                    + "SET i.descEstesa = :descEstesa,\n"
                    + "i.descrizione = :descrizione,\n"
                    + "i.dataInizioLavori =:dataInizioLavori,\n"
                    + "i.dataFineLavori =:dataFineLavori,\n"
                    + "i.scaduta = :scaduta,\n"
                    + "i.lastupdate = :lastupdate,\n"
                    + "i.note = :note,\n"
                    + "i.lastupdateBy = :lastupdateBy,\n"
                    + "i.indirizzoIp = :indirizzoIp\n"
                    + "WHERE i.id = :id");

            q.setParameter("descEstesa", installazione.getDescEstesa());
            q.setParameter("descrizione", installazione.getDescrizione());
            q.setParameter("dataInizioLavori", installazione.getDataInizioLavori());
            q.setParameter("dataFineLavori", installazione.getDataFineLavori());
            q.setParameter("scaduta", installazione.getScaduta());
            q.setParameter("lastupdate", new Date(System.currentTimeMillis()));
            q.setParameter("note", installazione.getNote());
            q.setParameter("indirizzoIp", installazione.getIndirizzoIp());
            q.setParameter("lastupdateBy",principal);                   
            q.setParameter("id", installazione.getId());
            q.executeUpdate();
            tx.commit();
            getEm().refresh(installazione);
            resp.setResult(Boolean.TRUE);
        } catch (Exception ex) {
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
            if(tx!=null){
                tx.setRollbackOnly();
            }
        } finally {
            if (tm != null) {
                tm.close();
            }
            closeTransaction(tx);
        }
        return resp;

    }

    @Override
    public Response<Boolean> remove(Long id) {
        Response<Boolean> resp = new Response<>();
        try {
            Installazione find = getEm().find(Installazione.class, id);
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

    @Override
    public Response<Installazione> crea(Installazione installazione) {
        Response<Installazione> resp = new Response<>();
        try {
            getEm().persist(installazione);
        } catch (Exception ex) {
            resp.setFault(true);
            resp.setException(ex);
        }
        return resp;
    }

}
