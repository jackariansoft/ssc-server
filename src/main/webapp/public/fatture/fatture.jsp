<%@page import="sezioniaccessi.Accessi"%>
<%@page import="db.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:choose>
    <c:when test="${!empty sessionScope.user}">
        <%

            Users user = (Users) request.getSession(false).getAttribute("user");
            if (user.getAccessType() == -1 || (user.getAccessType() & Accessi.GESTIONE_ORDINE) > 0) {
        %>
        <html>
            <%@include file="../header.jsp" %>
            <body>
                <%@include file="../common.jsp" %>                
                <main role="main" class="container-fluid">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>/home.jsp">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Fatture Fornitori</li>
                        </ol>
                    </nav>
                    <div class="row" style="margin-left:25px">

                        <%@include file="fatture_filter.jsp" %>                        

                    </div>
                    <div class="row" style="margin-top: 5px;margin-left: 0px;margin-right:0px">

                        <div id="dg" style="width:100%;height:800px;margin-left: 0px;margin-right:0px"></div>

                    </div>
                </main>
                <div id="tb" style="padding:2px 5px;">
                    <div class="card">
                        <div class="card-header">
                            <h6 class="card-title">Totali</h6>
                        </div>
                        <div class="card-body"> 
                            <div class="form-row" style="width: 100%;">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Ordini:</th>
                                            <th scope="col">Fornitori:</th>
                                            <th scope="col">I Margine:</th>
                                            <th scope="col">Margine Perc I:</th>
                                            <th scope="col">Commisione:</th>
                                            <th scope="col">Trasporto:</th>
                                            <th scope="col">Trasporto Previsto:</th>
                                             <th scope="col">Costi:</th>
                                            <th scope="col">II Margine:</th>
                                            <th scope="col">Margine Perc II:</th>
                                            <th scope="col">Fatture:</th>
                                                                                       
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><span id="amount_label" class="border rounded-pill">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_fornitori_label" class="border ">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_margine_1_label" class="border">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_margine_perc_1_label" class="border">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_commissione_label" class="border">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_trasporto_label" class="border ">&nbsp;00.0&euro;</span></td>                         
                                            <td><span id="amount_trasporto_pres_label" class="border">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_costi_label" class="border ">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_margine_2_label" class="border">&nbsp;00.0&percnt;</span></td>
                                            <td><span id="amount_marg_perc_label" class="border ">&nbsp;00.0&euro;</span></td>
                                            <td><span id="amount_fatture_label" class="border ">&nbsp;00.0&euro;</span></td>                                            
                                                                                                                                    
                                        </tr>                                        
                                    </tbody>
                                </table>                               
                            </div>

                        </div>
                    </div>

                    <div class="form-row" style="width: 70%;">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcel()">Export Excel</a>
                    </div>

                </div>
                <div id="tb-view" style="padding:2px 5px;">                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcelDettaglio()">Export Excel</a>
                </div>
                <%@include  file="../script_loader.jsp" %>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.tagbox.js"></script>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.combogrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.treegrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/datepicker_functions.js?v=15"></script>
                <script src="<%= request.getContextPath()%>/js/utils/common.js?v=23"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-export.js"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-detailview.js?v=25"></script>                                
                <script src="<%= request.getContextPath()%>/js/datagrid-filter.js?v=27"></script>               
                <script src="<%= request.getContextPath()%>/js/fatture/fatture.js?v=10"></script>                





            </body>
        </html>
        <% } else {%>
        <html>
            <body>No hai accesso a questa sezione.<a href="<%= request.getContextPath()%>/home.jsp">Home</a></body>
        </html>
        <%  }%>
    </c:when>
    <c:otherwise>
        <%
            //request.setAttribute("error", "User Not Defined");
            RequestDispatcher disp = request.getRequestDispatcher("/login");
            disp.forward(request, response);

        %>
    </c:otherwise>
</c:choose>