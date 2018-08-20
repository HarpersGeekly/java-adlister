<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--PROTOTYPE PURPOSES: USING EL. ESSENTIALLY DOES THE SAME AS SERVLET--%>
<%--<%--%>

<%--//    remember to use "<form action="/login.jsp" method="POST">"--%>

    <%--String username = request.getParameter("username");--%>
    <%--String password = request.getParameter("password");--%>

   <%--if (request.getMethod().equalsIgnoreCase("post")) {--%>
<%--// takes care of null issues...only fire off the following code when the post method gets fired--%>
        <%--if (username.equalsIgnoreCase("admin") &&--%>
                <%--password.equalsIgnoreCase("password")) {--%>

            <%--response.sendRedirect("profile.jsp?username=" + request.getParameter("username"));--%>
        <%--} else {--%>
            <%--request.getRequestDispatcher("/login.jsp"); //if the username and password isn't admin and password it redirects back to login--%>
        <%--}--%>
    <%--}--%>

<%--%>--%>
<%--<c:if test="${param.username.equalsIgnoreCase('admin') && param.password.equalsIgnoreCase('password')}">--%>
<%--<% response.sendRedirect("profile.jsp?username=" + request.getParameter("username")); %>--%>
<%--</c:if>--%>

<html>
<head>

    <title>Login</title>
    <%@ include file="partials/styles.jsp" %>
    <link rel="stylesheet" href="css/main.css" type="text/css">

</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<form action="/login" method="POST">
<%--<form action="/login.jsp" method="POST">--%>
    <label>Username</label>
    <input type="text" name="username">
    <label>Password</label>
    <input type="password" name="password">
    <input type="submit" value="Login!">
</form>

<%@ include file="partials/scripts.jsp" %>
</body>
</html>