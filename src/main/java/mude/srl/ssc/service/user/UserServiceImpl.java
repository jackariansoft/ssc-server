/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.user;

import java.util.ArrayList;
import java.util.Date;
import mude.srl.ssc.entity.utils.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import mude.srl.ssc.entity.UserLog;
import mude.srl.ssc.entity.Users;
import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.service.AbstractService;
import mude.srl.ssc.service.log.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl extends AbstractService<Users> implements UserService, UserDetailsService {


    @Autowired
    LoggerService loggerService;
    
    @Override
    public void create(Users users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Users users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Users users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users find(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Users> findAll() {

        return null;
    }

    @Override
    public List<Users> findRange(int[] range) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response getuserByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<Users> getLogin(String login, String password) {
        Response<Users> resp = new Response<>();
        try {
            Query q = getEm().createQuery("SELECT u FROM Users u WHERE u.login = :name AND u.password=:password AND u.active=TRUE");
            q.setParameter("name", login);
            q.setParameter("password", password);

            Object singleResult = q.getSingleResult();
            if (singleResult instanceof Users) {
                resp.setResult((Users) singleResult);
            }
        } catch (NoResultException ex) {
            resp.setResult(null);

        } catch (NonUniqueResultException ex) {
            setErrorResponse(resp, ex);
        } catch (Exception ex) {
            setErrorResponse(resp, ex);
        }
        return resp;
    }

    

    @Override
    public Response<UserLog> createLog(String address, String useragent, Users user) {
        Response<UserLog> resp = new Response<>();
        UserLog log = new UserLog();
        log.setUsers(user);
        log.setUserComment("");
        log.setConnectionFrom(address);
        log.setUserAgent(useragent);
        log.setConnectionnStart(new Date(System.currentTimeMillis()));

        if (user.getUserLogCollection() == null) {
            user.setUserLogCollection(new ArrayList<>());
        }
        user.getUserLogCollection().add(log);
        EntityManager emt = null;
        EntityTransaction tx = null;
        try {
            Query q = getEm().createNativeQuery("INSERT INTO user_log ("
                    + "connection_from, "
                    + "connectionn_start, "
                    + "user_agent,"
                    + "user_comment, "
                    + "users) "
                    + "VALUES(?1,?2,?3,?4,?5) ");

            q.setParameter(1, log.getConnectionFrom());
            q.setParameter(2, log.getConnectionnStart());
            q.setParameter(3, log.getUserAgent());
            q.setParameter(4, log.getUserComment());
            q.setParameter(5, user.getUserId());
            int executeUpdate = q.executeUpdate();
            resp.setResult(log);
        } catch (PersistenceException ex) {
            setErrorResponse(resp, ex);
        } catch (Exception ex) {
            loggerService.logException(Level.SEVERE, null, ex);
        }
        return resp;
    }

    @Override
    public Response<UserLog> updateLog(UserLog log) {
        Response<UserLog> resp = new Response<>();
        try {
//            em.merge(log);
        } catch (PersistenceException ex) {
            setErrorResponse(resp, ex);
        }
        return resp;
    }

    @Override
    public Response<Users> DisableUser(Users user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response<UserLog> getLastgLog(Users user) {
        Response<UserLog> resp = new Response<>();
        try {
            Query q = getEm().createQuery("SELECT u FROM UserLog u WHERE u.user = :user ORDER BY u.connectionnStart DESC");
            q.setParameter("user", user);

            List<UserLog> resultList = q.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                resp.setResult(resultList.get(0));
            }
        } catch (NoResultException ex) {
            resp.setResult(null);

        } catch (NonUniqueResultException ex) {
            setErrorResponse(resp, ex);
        } catch (Exception ex) {
            setErrorResponse(resp, ex);
        }
        return resp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = null;
        try {
            TypedQuery<Users> q = (TypedQuery<Users>) getEm().createQuery("SELECT u FROM Users u WHERE u.username = :login", Users.class);
            q.setParameter("login", username);
            u = q.getSingleResult();
            loggerService.logInfo(Level.INFO, " User: {0}", u.getFirstName());
        } catch (NoResultException | NonUniqueResultException ex) {
            loggerService.logException(Level.SEVERE, " " + username, ex);
            throw new UsernameNotFoundException(username, ex);
        } catch (Exception ex) {
             loggerService.logException(Level.SEVERE, " " + username, ex);
            throw new UsernameNotFoundException(username, ex);
        }
        return u;
    }

}
