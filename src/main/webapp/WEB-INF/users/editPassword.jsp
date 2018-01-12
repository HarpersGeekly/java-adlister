<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <label for="password">New Password</label>
            <input id="password" name="password" class="form-control" type="text">
        </div>

        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input id="confirm_password" name="confirm_password" class="form-control" type="password">
        </div>
        <c:if test="${passwordsNotMatch}">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <strong>Passwords don't match</strong>
                    <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                    <%--<span aria-hidden="true">&times;</span>--%>
                    <%--</button>--%>
            </div>
        </c:if>
        <c:if test="${passwordEmpty}">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <strong>Both password fields need to be filled out</strong>
                    <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                    <%--<span aria-hidden="true">&times;</span>--%>
                    <%--</button>--%>
            </div>
        </c:if>

        <input type="submit" class="btn btn-primary btn-block" value="Update Password">
        <a href="/profile" type="submit" class="btn btn-block btn-warning">Nevermind</a>

    </form>

</div>

</body>
</html>
