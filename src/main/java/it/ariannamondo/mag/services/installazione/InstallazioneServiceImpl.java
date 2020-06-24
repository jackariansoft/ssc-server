/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.installazione;

import it.ariannamondo.mag.MagServiceInitializer;
import it.ariannamondo.mag.entity.Installazione;
import it.ariannamondo.mag.rest.installazione.rs.InstallazionePagination;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;


@Component
public class InstallazioneServiceImpl implements InstallazioneService {
    
    
    @PersistenceContext(unitName =MagServiceInitializer.PERSISTENCE_UNIT)
    private EntityManager em;

    @Override
    public InstallazionePagination getInstallation(Long page_size, Long current) {
        
        InstallazionePagination result  = new InstallazionePagination();
        
        result.init();
        
        
        Query q = em.createQuery("SELECT COUNT(i) FROM Installazione i");
        Long count = (Long) q.getSingleResult();
        if(count>0){
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
    
    
    
    
    
}
