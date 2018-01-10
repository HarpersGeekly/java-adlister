<%--
  Created by IntelliJ IDEA.
  User: RyanHarper
  Date: 1/10/18
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Edit Password" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />
<div class="container">

    <form action="/profile/editPassword" method="POST">

        <h1>Change your password below:</h1>

        <%--Hidden, but needed for the table request:--%>
        <input id="id" name="id" type="hidden" value="${user.id}">

        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" name="password" class="form-control" type="password">
        </div>
        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input id="confirm_password" name="confirm_password" class="form-control" type="password">
        </div>

        <input type="submit" class="btn btn-primary btn-block" value="Update Password">

    </form>

</div>

</body>
</html>
