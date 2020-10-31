<%-- 
    Document   : home
    Created on : 14-lug-2017, 15.35.48
    Author     : vnc
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file="header.jsp"%>
<body>

	<main role="main" class="container-fluid">
	
		<div class="jumbotron">
  <h1 class="display-4">Smart Station - Entry Point</h1>
  <p class="lead"></p>
  <hr class="my-4">
  <p>
  <a class="btn btn-primary btn-lg" href="#" role="button">Avvicina il qrcode al lettore</a>
</div>


		<div id="message" class="shadow-lg p-3 mb-5 bg-white rounded">
			<div id="message-title" style="text-align: center"></div>
			<div id="message-body" style="text-align: center"></div>
		</div>






	</main>

     <input type="hidden" id="plc" value="<%= request.getParameter("plc")%>"/>


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
		src="<%= request.getContextPath()%>/public/js/plugins/jquery.tagbox.js"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/plugins/jquery.combogrid.js"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/plugins/jquery.treegrid.js"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/datepicker_functions.js?v=15"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/utils/common.js?v=20"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/datagrid-detailview.js?v=25"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/datagrid-detailview-errors.js?v=26"></script>
	<script
		src="<%= request.getContextPath()%>/public/js/user/user.js?v=23"></script>
</body>
</html>
