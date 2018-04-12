<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Please Log In" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

    <div class="container">
        <h1>Please Log In</h1>
        <form action="/login" method="POST">
            <div class="form-group">
                <label for="username">Username</label>
                <input id="username" name="username" class="form-control" type="text" value="${requestScope.username}">
                <%--${requestScope.username} is the attribute set from the paramaters in the LoginServlet--%>
            </div>
            <c:if test="${usernameEmpty}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <strong>Please enter a username</strong>
                        <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                        <%--<span aria-hidden="true">&times;</span>--%>
                        <%--</button>--%>
                </div>
            </c:if>
            <c:if test="${userNotExist}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <strong>That username doesn't exist. Have you <a href="/register">registered?</a></strong>
                </div>
            </c:if>

            <div class="form-group">
                <label for="password">Password</label>
                <input id="password" name="password" class="form-control" type="password" value="${requestScope.password}">

            </div>
            <c:if test="${passwordEmpty}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <strong>Please enter a password</strong>
                </div>
            </c:if>
            <c:if test="${passwordIncorrect != null}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <strong>Incorrect password</strong>
                </div>
            </c:if>

            <input type="submit" class="btn btn-primary btn-block" value="Log In">
        </form>
    </div>
</body>
</html>
