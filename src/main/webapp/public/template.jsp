<%@page import="mude.srl.ssc.entity.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <%@include file="header.jsp" %>
<body>
    <c:choose>
        <c:when test="${!empty sessionScope.user}">
            <div class="container">

                <%@include file="common.jsp" %>
                

                <footer class="footer">
                    <p>&copy; mude s.r.l 2019</p>
                </footer>


            </div>           
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
            <script src="js/bootstrap.min.js"></script>
        </c:when>
        <c:otherwise>
            <%
                //request.setAttribute("error", "User Not Defined");
                RequestDispatcher disp = request.getRequestDispatcher("public/login.jsp");
                disp.forward(request, response);

            %>
        </c:otherwise>
    </c:choose>
</body>
</html>
