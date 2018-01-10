<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: RyanHarper
  Date: 1/10/18
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Edit Profile" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />
<div class="container">

<form action="/profile/edit" method="POST">

    <h1>Edit your profile below:</h1>

    <%--Hidden, but needed for the table request:--%>
    <input id="id" name="id" type="hidden" value="${user.id}">

    <div class="form-group">
        <label for="username">Username</label>
        <input id="username" name="username" class="form-control" type="text" maxlength="30" value=${user.username}>
    </div>
    <c:if test="${sessionScope.user != null} && ${sessionScope.usernameExists != null}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <strong>That username already exists</strong>
                <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                <%--<span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
        </div>
    </c:if>

    <div class="form-group">
        <label for="email">Email</label>
        <input id="email" name="email" class="form-control" type="text" value=${user.email}>
    </div>

    <input type="submit" class="btn btn-primary btn-block" value="Update Profile">

</form>

</div>

</body>
</html>
