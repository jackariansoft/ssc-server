<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
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
                            <li class="breadcrumb-item active" aria-current="page">Ordini</li>
                        </ol>
                    </nav>
                    <div class="row" style="margin-left:25px">

                        <%@include file="searchfilter.jsp" %>                        

                    </div>
                    <div class="row" style="margin-top: 5px;margin-left: 0px;margin-right:0px">
                        <div class="col-lg-12" style="margin-left: 0px;margin-right:0px;padding: 0;padding-left:2px">
                            <!-- <div id="cc" style="width:100%;height:500px;margin-left: 0px">  
                                
                                <div data-options="region:'center',title:'Orders',iconCls:'icon-ok'"> -->
                            <div id="dg" style="width:100%;height:800px;margin-left: 0px;margin-right:0px"></div>
                            <!--        </div>
                             </div> -->
                        </div>
                    </div>
                    <div id="infoDialog"></div>
                </main>               
                <div id="tb" style="padding:2px 5px;">
                    <h6 id="amount">Total Amount:<span id="amount_label" class="badge badge-secondary">&nbsp;00.0&euro;</span></h6>                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="aggiornaStatoOrdini()">Salva Modifiche</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">Reset Modifche</a>                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="deseleziona()">Deseleziona</a>
                    <input class="easyui-checkbox" name="enableSelection" value="abilita" checked="true" width="150" labelWidth="120" title="Se...." label="Abilita Selezione">                    
                    <input class="easyui-textbox"  name="noteSpedizione" id ="noteSpedizione" data-options="iconCls:'icon-man',iconAlign:'left',labelWidth:'150',prompt:'Note spedizione'" style="width:300px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportToExcel()">Export Excel</a>
                </div>
                <div id="mAssicurazione" class="easyui-menu" style="width:150px;">
                    <div data-options="name:'updateAll'">Aggiorna tutte</div>
                    <div data-options="name:'reset'">Reset</div>
                </div>
                <div id="mContrassegno" class="easyui-menu" style="width:150px;">
                    <div data-options="name:'updateAll'">Aggiorna tutte</div>
                    <div data-options="name:'reset'">Reset</div>
                </div>
                <div id="modInd" ></div>
                <div id="modErrors"></div>
                <div id="upload"></div>
                <%@include  file="../script_loader.jsp" %>


               
                
                
                
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.tagbox.js"></script>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.combogrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/plugins/jquery.treegrid.js"></script>
                <script src="<%= request.getContextPath()%>/js/datepicker_functions.js?v=15"></script>                
                <script src="<%= request.getContextPath()%>/js/utils/common.js?v=18"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/datagrid-cellediting.js?v=25"></script>
                <script src="<%= request.getContextPath()%>/js/datagrid-detailview.js?v=25"></script>                
                <script src="<%= request.getContextPath()%>/js/datagrid-detailview-errors.js?v=26"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/my.window.js?v=23"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/ordini_utils.js?v=47"></script>
                <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDxsGkqICeiQ0Dvg3B823mHOrvsyI2ZrrE&libraries=places&callback=initAutocomplete" async defer></script>
                <script src="<%= request.getContextPath()%>/js/ordini/ordini.js?v=68"></script>
                <script src="<%= request.getContextPath()%>/js/ordini/ordini_gestione_errori.js?v=24"></script>                                
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
            //request.setAttribute("error", "User Not Defined");
            //RequestDispatcher disp = request.getRequestDispatcher("/login?redirect_url="+request.getRequestURL()+"?"+request.getQueryString());
            //disp.forward(request, response);
            if (request.getQueryString() != null) {
                response.sendRedirect(request.getContextPath() + "/login?redirect_url=" + request.getRequestURL() + "?" + URLEncoder.encode(request.getQueryString(), "UTF-8"));
            } else {
                response.sendRedirect(request.getContextPath() + "/login?redirect_url=" + request.getRequestURL());
            }

        %>
    </c:otherwise>
</c:choose>


