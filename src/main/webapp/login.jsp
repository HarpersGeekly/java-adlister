<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--PROTOTYPE PURPOSES: USING EL. ESSENTIALLY DOES THE SAME AS SERVLET--%>
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
    <label>Username</label>
    <input type="text" name="username">
    <label>Password</label>
    <input type="password" name="password">
    <input type="submit" value="Login!">
</form>

<%@ include file="partials/scripts.jsp" %>
</body>
</html>