<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark" id="link-nav">
        <a class="navbar-brand" href="#">Dashboard-SSC</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<%= request.getContextPath()%>/public/home.jsp">Home</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="<%= request.getContextPath()%>/public/plc/plc.jsp">PLC Config</a>
                </li>                                             
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath()%>/logout">Logout</a>
                </li>
                
            </ul>
        </div>
    </nav>    
</header>
