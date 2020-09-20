<%@page import="sezioniaccessi.Accessi"%>
<%@page import="db.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:choose>
    <c:when test="${!empty sessionScope.user}">
        <%

            Users user = (Users) request.getSession(false).getAttribute("user");
            if (user.getAccessType() == -1 || (user.getAccessType() & Accessi.GESTIONE_LOGISTICA) > 0) {
        %>
        <html>
            <%@include file="../header.jsp" %>
            <body>
                <%@include file="../common.jsp" %>                
                <main role="main" class="container-fluid">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>/home.jsp">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Spedizioni</li>
                        </ol>
                    </nav>
                    <div class="row" style="margin-left:  2%">

                        <%@include file="filter_spedizioni.jsp" %>                        

                    </div>
                    <div class="row" style="margin-top: 5px;margin-left: 0px;margin-right:0px">
                        
                            <div id="dg" style="width:100%;height:800px;margin-left: 0px;margin-right:0px"></div>
                        
                    </div>
                </main>               
                <div id="tb" style="padding:2px 5px;">
                    <h6 id="amount" style="display: none">Total Amount:<span id="amount_label" class="badge badge-secondary">&nbsp;00.0&euro;</span></h6>                                                            
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptTask()">Conferma</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectTask()">Cancella Modifiche</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChangesTask()">Modifche effettutate</a>                    
                    <input class="easyui-checkbox" name="enableSelection" value="abilita" checked="true" width="150" labelWidth="120" title="Se...." label="Abilita Selezione">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcel()">Export Excel</a>
                </div>                
                <div id="mStatus" class="easyui-menu" style="width:150px;">
                    <div data-options="name:'comfirmAll'">Conferma tutte</div>
                    <div data-options="name:'suspendAll'">Sospendi tutte</div>
                    <div data-options="name:'attendAll'">Attesa tutte</div>
                    <div data-options="name:'disableAll'">Annulla tutte</div>
                    <div data-options="name:'sendAll'">Spedite tutte</div>                    
                </div>
                <div id="modMenuRighe">
                    <div data-options="name:'modifica'">Modifica</div>
                    <div data-options="name:'viewOrder'">Visualizza Info Ordine</div>                    
                </div>
                <div id="modModifiche">

                </div>
                <div id="modMoves" style="width: 80%">

                </div>

                <%@include  file="../script_loader.jsp" %>
                <%@include  file="opzione_sposta_spedizioni.jsp" %>
                
                <script src="<%= request.getContextPath()%>/js/datepicker_functions.js?v=5"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-detailview.js?v=17"></script>                
                <script src="<%= request.getContextPath()%>/js/utils/common.js?v=18"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/datagrid-spedizioni-view.js?v=18"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/my.window.js?v=26"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/sposta-spedizioni.js?v=27"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/dettaglio_spedizioni.js?v=34"></script>                
                <script src="<%= request.getContextPath()%>/js/ordini/spedizioni.js?v=34"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-export.js"></script>


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

            RequestDispatcher disp = request.getRequestDispatcher("/login");
            disp.forward(request, response);

        %>
    </c:otherwise>
</c:choose>


