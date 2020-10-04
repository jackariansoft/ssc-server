<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<footer class="footer">
    <%
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        String dates = formatter.format(new Date(System.currentTimeMillis()));
    %>
    <div class="footer">
        <span class="text-center">&copy; mude s.r.l <%=dates %></span>
    </div>
</footer>
