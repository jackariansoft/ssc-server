<%-- 
    Document   : home
    Created on : 14-lug-2017, 15.35.48
    Author     : vnc
--%>
<%@page import="sezioniaccessi.Accessi"%>
<%@page import="db.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <%@include file="../header.jsp" %>
    <body>
        <c:choose>
            <c:when test="${!empty sessionScope.user}">
                <div class="container" style="width: 100%">

                    <%@include file="../common.jsp" %> 

                    <!--Load the AJAX API-->
                    <div id="chart_div" style="width: 100%;height:500px;padding:0px"></div>



                </div>
                <%@include  file="statistic_filter.jsp" %>


                <%@include  file="../footer.jsp" %>
                <%@include  file="../script_loader.jsp" %>
                <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                <%@include  file="../statistics_loader.jsp" %>
            </c:when>
            <c:otherwise>
                <%                //request.setAttribute("error", "User Not Defined");
                    RequestDispatcher disp = request.getRequestDispatcher("login.jsp");
                    disp.forward(request, response);

                %>
            </c:otherwise>
        </c:choose>
    </body>
</html>
