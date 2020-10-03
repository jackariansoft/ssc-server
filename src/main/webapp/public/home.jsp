<%-- 
    Document   : home
    Created on : 14-lug-2017, 15.35.48
    Author     : vnc
--%>

<%@page import="mude.srl.ssc.entity.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <%@include file="header.jsp" %>
    <body>
        <c:choose>
            <c:when test="${!empty sessionScope.user}">
                <div class="container">

                    <%@include file="common.jsp" %> 

                    <!--Load the AJAX API-->
                    



                </div>
               


                <%@include  file="footer.jsp" %>
               
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
