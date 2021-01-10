<%@page import="mude.srl.ssc.entity.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${!empty sessionScope.user}">
		<html>
<%@include file="../header.jsp"%>
<body>
	<%@include file="../common.jsp"%>
	<main role="main" class="container-fluid"
		style="margin-top: 75px; margin-bottom: 10px;">
		<div class="row"
			style="margin-top: 5px; margin-left: 0px; margin-right: 0px">
			<div class="col-lg-2"
				style="margin-left: 0px; margin-right: 0px; padding: 0; padding-left: 2px">
				<ul id="plc_list"></ul>
			</div>
			<div class="col-lg-8" id="viewer"
				style="margin-left: 0px; margin-right: 0px; padding: 0; padding-left: 2px">

			</div>
		</div>
		<div id="mpresource" class="easyui-menu" style="width: 120px;">
			<div onclick="attiva()" data-options="iconCls:'icon-add'">Attiva</div>
			<div onclick="disattiva()" data-options="iconCls:'icon-remove'">Disattiva</div>			
		</div>
	</main>
	<%@include file="../footer.jsp"%>
	<%@include file="../script_loader.jsp"%>
	<script	src="<%= request.getContextPath()%>/public/js/utils/setting.js?v=1"></script>
	<script src="<%=request.getContextPath()%>/public/js/plc/plc.js?v=1"></script>
</body>
		</html>
	</c:when>
	<c:otherwise>
		<%
		//request.setAttribute("error", "User Not Defined");
		RequestDispatcher disp = request.getRequestDispatcher("/public/login.jsp");
		disp.forward(request, response);
		%>
	</c:otherwise>
</c:choose>
