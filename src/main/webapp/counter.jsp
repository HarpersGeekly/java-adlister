<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Counter</title>
    <%@ include file="partials/styles.jsp" %>
    <link rel="stylesheet" href="css/main.css" type="text/css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>


<h1>You have visited this page ${counter} times </h1>


<%@ include file="partials/scripts.jsp" %>
</body>
</html>
