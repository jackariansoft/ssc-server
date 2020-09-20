/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mude.srl.ssc.config.JwtTokenUtil;
import mude.srl.ssc.config.commons.SessionConstants;
import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.Groups;
import mude.srl.ssc.entity.Users;
import mude.srl.ssc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jack
 */
@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;
    
    @RequestMapping(path = ServiceEndpoint.USER,method =RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> userInfo() {        
   
            Users details  = null;
            if(SecurityContextHolder.getContext().getAuthentication()!=null){
                details = (Users) session.getAttribute(SessionConstants.USER.getValue());
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
