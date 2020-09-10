/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.rest.location;

import it.ariannamondo.mag.config.commons.SessionConstants;
import it.ariannamondo.mag.config.endpoint.ServiceEndpoint;
import it.ariannamondo.mag.entity.Location;
import it.ariannamondo.mag.entity.User;
import it.ariannamondo.mag.entity.utils.Response;
import it.ariannamondo.mag.rest.location.rs.LocationPagination;
import it.ariannamondo.mag.services.location.LocationService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jackarian
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = ServiceEndpoint.LOCATION_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LocationPagination> getLocations(@PathVariable Long page_size, @PathVariable Long offset) {

        LocationPagination locations = locationService.getLocations(page_size, offset);
        return ResponseEntity.ok(locations);
    }

    @RequestMapping(value = ServiceEndpoint.LOCATION_UPDATE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updateLocation(@PathVariable Long id, @RequestBody Location location) {

        if (session.getAttribute(SessionConstants.USER.getValue()) != null) {
            User details = (User) session.getAttribute(SessionConstants.USER.getValue());
            details.setPassword(null);
            location.setLastupdateBy(details);
        }
        Response<Boolean> resp = locationService.aggiorna(location);
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = ServiceEndpoint.LOCATION_CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> crea(@RequestBody Location location) throws Exception {

        if (session.getAttribute(SessionConstants.USER.getValue()) != null) {
            User details = (User) session.getAttribute(SessionConstants.USER.getValue());
            details.setPassword(null);
            location.setLastupdateBy(details);
        }
       
        
        Response<Boolean> resp = locationService.crea(location);
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = ServiceEndpoint.LOCATION_REMOVE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> elimina(@PathVariable Long id) {

        Response<Boolean> resp = locationService.elimina(id);
        return ResponseEntity.ok(resp);
    }

}
