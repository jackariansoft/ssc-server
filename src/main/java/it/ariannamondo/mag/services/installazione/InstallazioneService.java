/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.installazione;

import it.ariannamondo.mag.entity.Installazione;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.installazione.rs.InstallazionePagination;

/**
 *
 * @author jackarian
 */
public interface InstallazioneService {
    
    
    public InstallazionePagination getInstallation(Long page_size,Long off_set);
    
    public Response<Boolean> update(Installazione installazione);

    public Response<Boolean> remove(Long id);

    public Response<Installazione> crea(Installazione installazione);
}
