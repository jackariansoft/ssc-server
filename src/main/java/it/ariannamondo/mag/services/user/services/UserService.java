/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.services.user.services;

import it.ariannamondo.mag.entity.UserLog;
import it.ariannamondo.mag.entity.User;
import it.ariannamondo.mag.entity.utils.Response;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author jackarian
 */
public interface UserService extends UserDetailsService{
    
    void create(User users);

    void edit(User users);

    void remove(User users);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    Response getuserByName(String name);  
    
    public Response<User>  getLogin(String login, String string);
    public Response<UserLog> createLog(String address,String useragent,User user);
    public Response<UserLog> updateLog(UserLog log);
    public Response<User> DisableUser(User user);
    public Response<UserLog> getLastgLog(User user);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
}
