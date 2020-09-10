/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.location;

import it.ariannamondo.mag.entity.Location;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.location.rs.LocationPagination;

/**
 *
 * @author jackarian
 */
public interface LocationService {
    
    public LocationPagination getLocations(Long page_size, Long current);
    
    public Response<Boolean>  crea(Location location) throws Exception;
    public Response<Boolean>  aggiorna(Location location);
    public Response<Boolean>  elimina(Long id);
}
