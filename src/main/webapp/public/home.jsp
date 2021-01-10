<%-- 
    Document   : home
    Created on : 14-lug-2017, 15.35.48
    Author     : vnc
--%>

<%@page import="mude.srl.ssc.entity.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:choose>
	<c:when test="${!empty sessionScope.user}">
		<html>
<%@include file="header.jsp"%>
<body>
	<%@include file="common.jsp"%>
	<main role="main" class="container-fluid">
		<div class="row" style="margin-left: 25px; margin-top: 70px">
			<%@include file="risorse/searchfilter.jsp"%>
		</div>
		<div class="row"
			style="margin-top: 5px; margin-left: 0px; margin-right: 0px">
			<div class="col-lg-12"
				style="margin-left: 0px; margin-right: 0px; padding: 0; padding-left: 2px">
				<div id="dg" style="width: 100%; height: 800px; margin-left: 0px; margin-right: 0px"></div>
			</div>
		</div>
		
		
		
		<div id="infoDialog"></div>
		<div id="tb" style="padding: 2px 5px;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true"
				onclick="aggiornaStatoOrdini()">Salva Modifiche</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true" onclick="reject()">Reset
				Modifche</a> <a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="deseleziona()">Deseleziona</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true"
				onclick="creaPrenotazione()">Crea Prenotazione</a> <input
				class="easyui-checkbox" name="enableSelection" value="abilita"
				checked="true" width="150" labelWidth="120" title="Se...."
				label="Abilita Selezione"> <a href="javascript:void(0)"
				class="easyui-linkbutton" onclick="exportToExcel()">Export Excel</a>			
		</div>
		<div id="modInd"></div>
		<div id="modErrors"></div>
		<div id="dd1"></div>
		<div id="dd2"></div>
		<%@include file="script_loader.jsp"%>					
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"
			integrity="sha512-5yJ548VSnLflcRxWNqVWYeQZnby8D8fJTmYRLyvs445j1XmzR8cnWi85lcHx3CUEeAX+GrK3TqTfzOO6LKDpdw=="
			crossorigin="anonymous"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"
			integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g=="
			crossorigin="anonymous"></script>		
		<script
			src="<%=request.getContextPath()%>/public/js/datepicker_functions.js?v=15"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/utils/common.js?v=20"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/utils/setting.js?v=1"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/resource/datagrid-cellediting.js?v=25"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/datagrid-detailview.js?v=25"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/datagrid-detailview-errors.js?v=26"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/resource/resource_utils.js?v=47"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/resource/resource.js?v=70"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/resource/ordini_gestione_errori.js?v=24"></script>
		<script
			src="<%=request.getContextPath()%>/public/js/datagrid-export.js"></script>
	</main>
	<%@include file="footer.jsp"%>
</body>
		</html>
	</c:when>
	<c:otherwise>
		<%
		RequestDispatcher disp = request.getRequestDispatcher("/public/login.jsp");
		disp.forward(request, response);
		%>
	</c:otherwise>
</c:choose>

