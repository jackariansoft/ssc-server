<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${!empty sessionScope.user}">
        <jsp:forward page="home.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="login.jsp"/>
    </c:otherwise>
</c:choose>