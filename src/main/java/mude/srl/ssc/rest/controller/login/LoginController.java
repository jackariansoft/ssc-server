/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import mude.srl.ssc.entity.UserLog;
import mude.srl.ssc.entity.Users;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.rest.controller.command.handler.CommonServelet;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.user.UserService;



/**
 *
 * @author Jack
 */
@WebServlet
public class LoginController extends CommonServelet{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1084294997468132778L;
	@Autowired
    UserService userService;
    @Autowired
    LoggerService loggerService;
    
  

    
       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        String login = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        String redirect_url  = request.getParameter(CommonServelet.REDIRECT_URL_PARAMETER);
        //String name = request.getUserPrincipal().getName();
        if (login != null && password != null) {
            if (login.length() > 0 && password.length() > 0) {
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                try {
                    MessageDigest dig = MessageDigest.getInstance("MD5");
                    dig.update(password.getBytes(), 0, password.length());
                    byte[] digest = dig.digest();
                    StringBuilder hexString = new StringBuilder();
                    for (int i = 0; i < digest.length; i++) {
                        String hex = Integer.toHexString(0xFF & digest[i]);
                        if (hex.length() == 1) {
                            hexString.append("0");
                        }
                        hexString.append(hex);

                    }
                    //String toString = new BigInteger(1, digest).toString(16);
                    Response<Users> resp = userService.getLogin(login, hexString.toString());
                    if (resp.isFault()) {

                    } else {
                        Users user = resp.getResult();
                        if (user == null) {

                            request.setAttribute("msg", "Login or Password incorrect");
                            request.setAttribute(CommonServelet.REDIRECT_URL_PARAMETER, redirect_url);
                            RequestDispatcher disp = request.getRequestDispatcher(CommonServelet.PUBLIC_DOC_ROOT+"login.jsp");
                            disp.forward(request, response);

                        } else {

                            Response<UserLog> log = userService.createLog(request.getRemoteAddr(), request.getHeader("User-Agent"), user);
                            
                                HttpSession newSession = request.getSession(true);
                                user.setLastLog(log.getResult());
                                newSession.setAttribute("user", user);
                                if(redirect_url!=null&&redirect_url.trim().length()>0){
                                    response.sendRedirect(redirect_url);
                                    //RequestDispatcher disp = request.getRequestDispatcher(redirect_url);
                                    //disp.forward(request, response);
                                }else{
                                    RequestDispatcher disp = request.getRequestDispatcher(CommonServelet.PUBLIC_DOC_ROOT+"/home.jsp");
                                    disp.forward(request, response);
                                }
                            
                        }
                    }
                } catch (NoSuchAlgorithmException ex) {
                   // Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("msg", "Provide Login and Password");
                RequestDispatcher disp = request.getRequestDispatcher(CommonServelet.PUBLIC_DOC_ROOT+"login.jsp");
                disp.forward(request, response);
            }
        } else {

            request.setAttribute("msg", "Provide Login and Password");
            request.setAttribute(CommonServelet.REDIRECT_URL_PARAMETER, redirect_url);
            RequestDispatcher disp = request.getRequestDispatcher(CommonServelet.PUBLIC_DOC_ROOT+"login.jsp");
            disp.forward(request, response);
        }
    }
    
    //
    
    
}
