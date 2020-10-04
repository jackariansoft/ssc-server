
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en-IN">
    <head>
        <meta charset="utf-8">
        <title>SSC Login</title>
        <meta name="description" content="HTML5 CSS3 Login Signup Form Design Or Template">
        <link href='https://fonts.googleapis.com/css?family=Ropa+Sans' rel='stylesheet'>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel='stylesheet'>
        <style>
            @import "https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css";
            body{font-family: 'Ropa Sans', sans-serif; color:#666; font-size:14px; color:#333}
            li,ul,body,input{margin:0; padding:0; list-style:none}
            #login-form{width:350px; background:#FFF; margin:0 auto; margin-top:20px; background:#f8f8f8; overflow:hidden; border-radius:7px}
            .login_screen{ display:block; cursor:pointer; z-index:999;line-height:60px; width:350px; text-align:center; background:#eee; font-size:18px; float:left; transition:all 600ms ease}

            /*similar*/
            .similar{width:350px; margin:0 auto; margin-top:20px}
            .similar a{display:block; border-radius:4px; float:left; width:33%; text-align:center; padding:9px 0; text-decoration:none; background:#066; color:#eee; margin-left:.5%}
            .similar a:nth-child(1){margin-left:0}

            /*sectiop*/
            .section-out{width:700px; float:left; transition:all 600ms ease}
            .section-out:after,.similar:after{content:''; clear:both; display:table}
            .section-out section{width:350px; float:left}

            .login{padding:20px}
            .ul-list{clear:both; display:table; width:100%}
            .ul-list:after{content:''; clear:both; display:table}
            .ul-list li{ margin:0 auto; margin-bottom:12px}
            .input{background:#fff; transition:all 800ms; width:247px; border-radius:3px 0 0 3px; font-family: 'Ropa Sans', sans-serif; border:solid 1px #ccc; border-right:none; outline:none; color:#999; height:40px; line-height:40px; display:inline-block; padding-left:10px; font-size:16px}
            .input,.login span.icon{vertical-align:top}
            .login span.icon{width:50px; transition:all 800ms; text-align:center; color:#999; height:40px; border-radius:0 3px 3px 0; background:#e8e8e8; height:40px; line-height:40px; display:inline-block; border:solid 1px #ccc; border-left:none; font-size:16px}
            .input:focus:invalid{border-color:red}
            .input:focus:invalid+.icon{border-color:red}
            .input:focus:valid{border-color:green}
            .input:focus:valid+.icon{border-color:green}
            #check,#check1{top:1px; position:relative}
            .btn{border:none; outline:none; background:#0099CC; border-bottom:solid 4px #006699; font-family: 'Ropa Sans', sans-serif; margin:0 auto; display:block; height:40px; width:100%; padding:0 10px; border-radius:3px; font-size:16px; color:#FFF}

            .remember{width:50%; display:inline-block; clear:both; font-size:14px}
            .remember:nth-child(2){text-align:right}
            .remember a{text-decoration:none; color:#666}

            .hide{display:none}

            /*swich form*/
            #signup:checked~.section-out{margin-left:-350px}
            #login:checked~.section-out{margin-left:0px}
            #login:checked~div .form-header li:nth-child(1),#signup:checked~div .form-header li:nth-child(2){background:#e8e8e8}
        </style>
    </head>
    <body>

        <div id="login-form">

            <input type="radio" checked id="login" name="switch" class="hide">
            <input type="radio" id="signup" name="switch" class="hide">

            <div>

                <div class="login_screen">
                    <label for="login"><i class="fa fa-lock"></i> LOGIN</label>
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="msg">${msg}</div>
                    </c:if>
                </div>


            </div>

            <div class="section-out">
                <section class="login-section">
                    <div class="login">
                        <form action="<%= request.getContextPath()%>/login" method='POST'>
                            <ul class="ul-list">
                                <li><input type="text"  name='j_username' class="input" required placeholder="Login"/><span class="icon"><i class="fa fa-user"></i></span></li>
                                <li><input type="password" name="j_password" required  class="input" placeholder="Password"/><span class="icon"><i class="fa fa-lock"></i></span></li>                                
                                <li><input type="submit" value="SIGN IN" class="btn"></li>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <%  String redirect  = request.getParameter("redirect_url");
                                    if(redirect!=null){%>
                                    <input type="hidden" name="redirect_url" value="<%=redirect%>" />
                                   <% }
                                %>
                            </ul>
                        </form>
                    </div>                   
                </section>
            </div>
        </div>

    </body>
</html>
