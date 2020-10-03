package mude.srl.ssc.rest.controller.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mude.srl.ssc.entity.UserLog;
import mude.srl.ssc.entity.Users;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.rest.controller.command.handler.CommonServelet;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.user.UserService;

@WebServlet
public class LogoutController extends CommonServelet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    UserService userService;
    @Autowired
    LoggerService loggerService;
   
    
	 public void processRequest (HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
	    	 try {

	             HttpSession session = request.getSession(false);
	             if (session != null) {
	                 Object attribute = session.getAttribute("user");
	                 if (attribute != null && (attribute instanceof Users)) {
	                   
	                     Users user = (Users) attribute;
	                     Response<UserLog> logresp = userService.getLastgLog(user);
	                     if (!logresp.isFault()) {
	                         UserLog log = logresp.getResult();
	                         log.setConnectionEnd(new Date(System.currentTimeMillis()));
	                         log.setUserComment("Session Closed by user");
	                         userService.updateLog(log);
	                     }

	                 }
	             }
	             request.logout();
	             request.getSession().invalidate();
	             RequestDispatcher requestDispatcher;
	             requestDispatcher = request.getRequestDispatcher("/login.jsp");
	             requestDispatcher.forward(request, response);
	         } catch (ServletException | IOException e) {
	             throw new ServletException(e);
	         } finally {

	         }
	    }

}
