/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.security.controller;

import mude.srl.ssc.config.JwtTokenUtil;
import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.rest.security.model.JwtRequest;
import mude.srl.ssc.rest.security.model.JwtResponse;
import mude.srl.ssc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userDetailsService;

    @RequestMapping(value =ServiceEndpoint.LOGIN, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    
     @RequestMapping(value =ServiceEndpoint.LOGOUT, method = {RequestMethod.GET,RequestMethod.POST})
     public ResponseEntity<Response<Boolean>> logout(){
         
         SecurityContextHolder.getContext().setAuthentication(null);
         Response<Boolean> resp = new Response<>();
         resp.setFault(false);
         return ResponseEntity.ok(resp);
     }
     

    

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
