/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.installazione;

import it.ariannamondo.mag.MagServiceInitializer;
import it.ariannamondo.mag.entity.Installazione;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.installazione.StatiInstallazione;
import it.ariannamondo.mag.rest.installazione.rs.InstallazionePagination;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class InstallazioneServiceImpl implements InstallazioneService {

    @PersistenceContext(unitName = MagServiceInitializer.PERSISTENCE_UNIT)
    private EntityManager em;

    @Override
    public InstallazionePagination getInstallation(Long page_size, Long current) {

        InstallazionePagination result = new InstallazionePagination();

        result.init();

        Query q = em.createQuery("SELECT COUNT(i) FROM Installazione i");
        Long count = (Long) q.getSingleResult();
        if (count > 0) {
            result.setTotalRows(count);
            result.setPageSize(page_size);
            result.setCurrentPage(current);
            result.setPageNumber();

            q = em.createQuery("SELECT i FROM Installazione i");

            q.setFirstResult((int) result.getOffset());
            q.setMaxResults(result.getPageSize().intValue());
            List<Installazione> resultList = q.getResultList();
            result.setData(resultList);

        }

        return result;
    }

    @Override
    public Response<Boolean> update(Installazione installazione) {
        Response<Boolean> resp = new Response<>();
        try {
            Installazione merge = em.merge(installazione);
            resp.setResult(Boolean.TRUE);
        } catch (Exception ex) {
            resp.setResult(Boolean.FALSE);
            resp.setFault(true);
            resp.setException(ex);
        }
        return resp;

    }

    @Override
    public Response<Boolean> remove(Long id) {
        Response<Boolean> resp = new Response<>();
        try {
            Installazione find = em.find(Installazione.class, id);
            if (find != null) {
                find.setStato(StatiInstallazione.AVVIATA.getStato());
                em.merge(find);
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
            em.persist(installazione);
        } catch (Exception ex) {
            resp.setFault(true);
            resp.setException(ex);
        }
        return resp;
    }

}
