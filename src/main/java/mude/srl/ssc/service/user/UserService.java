/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.user;


import mude.srl.ssc.entity.utils.Response;
import java.util.List;
import mude.srl.ssc.entity.UserLog;
import mude.srl.ssc.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author jackarian
 */
public interface UserService extends UserDetailsService{
    
    void create(Users users);

    void edit(Users users);

    void remove(Users users);

    Users find(Object id);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();
    Response getuserByName(String name);  
    
    public Response<Users>  getLogin(String login, String string);
    public Response<UserLog> createLog(String address,String useragent,Users user);
    public Response<UserLog> updateLog(UserLog log);
    public Response<Users> DisableUser(Users user);
    public Response<UserLog> getLastgLog(Users user);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
}
