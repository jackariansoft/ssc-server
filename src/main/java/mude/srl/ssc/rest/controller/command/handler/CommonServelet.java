/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TreeMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import mude.srl.ssc.entity.Users;
import mude.srl.ssc.entity.utils.Pager;
import mude.srl.ssc.entity.utils.Request;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.rest.controller.common.client.DateUtils;



/**
 *
 * @author upload
 */
public class CommonServelet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8046466840743552841L;
	/**
	 * 
	 */
	
	protected Users user;
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    protected SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
   
    private DataSource dataSource;
    
    private WebApplicationContext springContext;
  
    public static final String REDIRECT_URL_PARAMETER  =  "redirect_url";
    public static final String PUBLIC_DOC_ROOT="/public/";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected Users getSessionUser() {
        return this.user;
    }
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }
    /**
     *
     * @param request
     * @return
     */
    protected Users validateSessioneLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Users userLogin = null;
        if (session != null) {
            Object attribute = session.getAttribute("user");
            if (attribute != null && (attribute instanceof Users)) {
                userLogin = (Users) attribute;
            }
        }
        return userLogin;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Validate query string parameters according to user role. If it founds
     * malformed values disable user and invalidate the session.
     *
     * @param req
     * @param response
     * @param user
     * @param request
     * @return Responce.
     */
    protected Response<Boolean> setCommonRequestField(HttpServletRequest request, HttpServletResponse response, Users user, Request req) {

        Response<Boolean> res = new Response<>();
        res.setResult(Boolean.TRUE);
        HttpSession session = request.getSession(false);
        try {
            req.setUser(user);

            String start = request.getParameter("start");
            String end = request.getParameter("end");
            
            

            if ((start != null && start.length() == 0) && (end != null && end.length() == 0)) {

                if (session.getAttribute("start") == null || session.getAttribute("end") == null) {
                    LocalDate now = LocalDate.now();
                    LocalDate days30ago = now.minusDays(30);
                    start = dateFormat.format(DateUtils.asDate(days30ago));
                    end = dateFormat.format(DateUtils.asDate(now));
                    session.setAttribute("start", start);
                    session.setAttribute("end", end);
                } else {

                    start = (String) session.getAttribute("start");
                    end = (String) session.getAttribute("end");
                }
            } else if (start == null || end == null) {
                if (session.getAttribute("start") == null || session.getAttribute("end") == null) {
                    LocalDate now = LocalDate.now();
                    LocalDate days30ago = now.minusDays(30);
                    start = dateFormat.format(DateUtils.asDate(days30ago));
                    end = dateFormat.format(DateUtils.asDate(now));
                    session.setAttribute("start", start);
                    session.setAttribute("end", end);
                } else {
                    start = (String) session.getAttribute("start");
                    end = (String) session.getAttribute("end");
                }
            } else {
                session.setAttribute("start", start);
                session.setAttribute("end", end);
            }

            req.setPager(new Pager());

            String channel = request.getParameter("channel");
            String channelExcl = request.getParameter("channelExcl");
            String page = request.getParameter("page");
            String pageSize = request.getParameter("pageSize");
            String orderList = request.getParameter("orderlist");
            String cap_list = request.getParameter("cap_list");
            String orderStatus = request.getParameter("status");
            String logisticStatus = request.getParameter("logisticStatus");
            String skus = request.getParameter("skus");
            String fattura = request.getParameter("fattura");
            //Estremi ricerca fattura
            String delta_min = request.getParameter("delta_min");
            String delta_max = request.getParameter("delta_max");
            //Estremi ricerca riga fattura vettore
            String delta_riga_min = request.getParameter("delta_riga_min");
            String delta_riga_max = request.getParameter("delta_riga_max");
            String rif_mitt_num = request.getParameter("rif_mitt_num");
            String rif_mitt_alfa = request.getParameter("rif_mitt_alfa");
            String rag_soc = request.getParameter("rag_soc");
            String fatture  = request.getParameter("fatture");
            
            
            if(rag_soc!=null){
                req.setRag_soc(rag_soc.trim());
            }
            if(fattura!=null&&!fattura.isEmpty()){
                try{
                    long parseLong = Long.parseLong(fattura);
                    req.setFatturaSpezione(parseLong);
                }catch(NumberFormatException ex){
                    
                }
            }
            if(delta_min!=null&&!delta_min.isEmpty()){
                
                BigDecimal bdelta_min = validateBigDecimal(delta_min);
                req.setDelta_min(bdelta_min);
                
            }
            if(delta_max!=null&&!delta_max.isEmpty()){
                
                BigDecimal bdelta_min = validateBigDecimal(delta_max);
                req.setDelta_max(bdelta_min);
                
            }
            if(delta_riga_max!=null&&!delta_riga_max.isEmpty()){
                
                BigDecimal bdelta_min = validateBigDecimal(delta_riga_max);
                req.setDelta_riga_max(bdelta_min);
                
            }
            if(delta_riga_min!=null&&!delta_riga_min.isEmpty()){
                
                BigDecimal bdelta_min = validateBigDecimal(delta_riga_min);
                req.setDelta_riga_min(bdelta_min);
                
            }
            
            if(skus!=null&&!skus.isEmpty()){
                req.setSkus(skus);
            }
            
            String indirizzo=request.getParameter("indirizzo");
            if(indirizzo!=null&&!indirizzo.isEmpty()){
                req.setIndirizzo(indirizzo.trim());
            }
            String civico=request.getParameter("civico");
            if(civico!=null&&!civico.isEmpty()){
                req.setCivico(civico.trim());
            }
            String citta=request.getParameter("citta");
            if(citta!=null&&!citta.isEmpty()){
                req.setCitta(citta.trim());
            }
            String provincia=request.getParameter("provincia");
           
            String cap=request.getParameter("cap");
            if(cap!=null&&!cap.isEmpty()){
                req.setCap(cap.trim());
            }
            String country=request.getParameter("country");
            if(country!=null&&!country.isEmpty()){
                req.setCountry(country.trim());
            }
            String cod_rep=request.getParameter("cod_rep");
            if(cod_rep!=null&&!cod_rep.isEmpty()){
                req.setCodRep(cod_rep.trim());
            }
            String tip_doc=request.getParameter("tip_doc");
            if(tip_doc!=null&&!tip_doc.isEmpty()){
                req.setTipDoc(tip_doc.trim());
            }
            String num_doc=request.getParameter("num_doc");
            if(num_doc!=null&&!num_doc.isEmpty()){
                req.setNumDoc(num_doc.trim());
            }
            String task=request.getParameter("task");
            if(task!=null&&!task.isEmpty()){
                try{
                    long parseLong = Long.parseLong(task);
                    req.setBatchId(parseLong);
                }catch(NumberFormatException ex){
                    
                }
            }
           
            if (start != null && start.length() > 0) {
                req.setStart(dateFormat.parse(start));
            }
            if (end != null && end.length() > 0) {
             req.setEnd(dateFormat.parse(end));    
            }
            
            req.setOrderStatus(orderStatus);
            req.setLogisticStatus(logisticStatus);
            if (pageSize != null) {
                try {
                    long lPsize = Long.parseLong(pageSize);
                    if (lPsize > 300) {
                        req.getPager().setPageSize(100);
                    } else {
                        req.getPager().setPageSize(lPsize);
                    }
                } catch (NumberFormatException ex) {
                    req.getPager().setPageSize(100);
                }

            } else {
                req.getPager().setPageSize(100);
            }
            if (page != null && !page.isEmpty()) {
                try {
                    Long c_page = Long.parseLong(page);
                    req.getPager().setCurrentPage(c_page.intValue());
                } catch (NumberFormatException ex) {
                    req.getPager().setCurrentPage(1);
                }
            }
            if (orderList != null && !orderList.isEmpty()) {
                req.setOrderList(orderList);
            }
            if (cap_list != null && !cap_list.isEmpty()) {
                req.setCap_list(cap_list);
            }
            if (rif_mitt_alfa != null && !rif_mitt_alfa.isEmpty()) {
                req.setRif_mitt_alfa(rif_mitt_alfa);
            }
            if (rif_mitt_num != null && !rif_mitt_num.isEmpty()) {
                req.setRif_mitt_num(rif_mitt_num);
            }
             if (fatture != null && !fatture.isEmpty()) {
                req.setFatture(fatture);
            }
            if (channel != null && !channel.isEmpty()) {
                req.setChannel(channel);
            }
            if(channelExcl!=null&&!channelExcl.isEmpty()){
                req.setChannelExcl(channelExcl);
            }
            req.setUser(user);

        } catch (ParseException | NumberFormatException ex) {
            res.setFault(true);
            res.setResult(Boolean.FALSE);
            res.setException(ex);
            res.setErrorType(Response.INTERNAL_SERVER_ERROR);
            res.setErrorDescription(Response.ERROR_MG_INTERNAL_SERVER_ERROR);
            res.setErrorMessage(ex.getMessage());
        }
        return res;
    }

    public boolean validateDate(String date) {
        boolean check = true;
        if (date == null) {
            check = false;
        } else {
            try {
                dateFormat.parse(date);
            } catch (ParseException ex) {
                check = false;
            }
        }
        return check;
    }

    public Double validateDouble(String doubleValue) {
        Double res = null;
        if (doubleValue != null) {
            try {
                res = new Double(doubleValue.trim());
            } catch (NumberFormatException ex) {

            }
        }
        return res;
    }
    public BigDecimal validateBigDecimal(String doubleValue) {
        BigDecimal res = null;
        if (doubleValue != null) {
            try {
                res = new BigDecimal(doubleValue.trim());
            } catch (NumberFormatException ex) {
                res = null;
            }
        }
        return res;
    }
    /**
     * 
     * @param jndiname 
     */
    public void setDataSource(String jndiname) {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/" + jndiname);
            
        } catch (NamingException e) {
            // Handle error that it's not configured in JNDI.
            throw new IllegalStateException(jndiname + " is missing in JNDI!", e);
        }
    }
    /**
     * 
     * @return
     * @throws SQLException 
     */
     public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
     
}
