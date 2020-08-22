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

                        <%@include file="searchfilter.jsp" %>                        

                    </div>
                    <div class="row" style="margin-top: 5px;margin-left: 0px;margin-right:0px">
                        
                            <div id="dg" style="width:100%;height:800px;margin-left: 0px;margin-right:0px"></div>
                        
                    </div>
                </main>               
                <div id="tb" style="padding:2px 5px;">                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcel()">Export Excel</a>
                </div>
                 <div id="tb-view" style="padding:2px 5px;">                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcelDettaglio()">Export Excel</a>
                </div>
                <%@include  file="../script_loader.jsp" %>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.combogrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.treegrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/datepicker_functions.js?v=15"></script>
                <script src="<%= request.getContextPath()%>/js/utils/common.js?v=18"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-export.js"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-detailview.js?v=25"></script>                                
                <script src="<%= request.getContextPath()%>/js/datagrid-filter.js?v=25"></script>
                <script src="<%= request.getContextPath()%>/js/vettori/datagrid-dettaglio-fattura-view.js?v=4"></script>
                <script src="<%= request.getContextPath()%>/js/vettori/datagrid-scrollview.js?v=4"></script>
                <script src="<%= request.getContextPath()%>/js/vettori/dettaglio_fattura.js?v=10"></script>
                <script src="<%= request.getContextPath()%>/js/vettori/vettori.js?v=8"></script>                
               

                
                

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