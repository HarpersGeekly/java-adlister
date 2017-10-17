<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile Page</title>
    <%@ include file="partials/styles.jsp" %>
    <link rel="stylesheet" href="css/main.css" type="text/css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<h1>Hello, ${param.username}! Welcome to the profile page!</h1>

<%@ include file="partials/scripts.jsp" %>
</body>
</html>
