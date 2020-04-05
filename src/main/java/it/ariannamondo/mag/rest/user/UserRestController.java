/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.rest.user;

import it.ariannamondo.mag.config.JwtTokenUtil;
import it.ariannamondo.mag.config.commons.SessionConstants;
import it.ariannamondo.mag.config.endpoint.ServiceEndpoint;
import it.ariannamondo.mag.entity.Groups;
import it.ariannamondo.mag.entity.User;
import it.ariannamondo.mag.services.user.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jackarian
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserRestController {
    
    
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;
    
    @RequestMapping(path = ServiceEndpoint.USER,method =RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> userInfo() {        
   
            User details  = null;
            if(SecurityContextHolder.getContext().getAuthentication()!=null){
                details = (User) session.getAttribute(SessionConstants.USER.getValue());
                details.setPassword(null);
            }
            
            return ResponseEntity.ok(details);              
            
    }

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping(path = ServiceEndpoint.USER_GROUPS,method =RequestMethod.GET)
    public ResponseEntity<Groups> getGroup(@PathVariable Long user_id) {
        return ResponseEntity.ok(new Groups());
    }
}
