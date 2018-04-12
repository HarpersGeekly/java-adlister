<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Register" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<% String username = request.getParameter("username");
    if (username == null) username = "";
    String email = request.getParameter("email");
    if (email == null) email = "";
%>


<div class="container">

    <c:if test="${sessionScope.user == null}">
        <h1>Welcome! Please Register Below:</h1>
    </c:if>

<%--since I'm using the same register form with THREE servlets (Register, EditUser, DeleteUser) use ${action} and not "/register"--%>
    <form action="/register" method="POST">

        <%--Hidden, but needed for the table request:--%>
        <input id="id" name="id" type="hidden" value="${user.id}">
        <input id="joinDate" name="joinDate" type="hidden" value="${user.joinDate}">

        <div class="form-group">
            <label for="username">Username</label>
            <input id="username" name="username" class="form-control" type="text" maxlength="30" value=<%=username%>>
        </div>
            <%--<c:if test="${sessionScope.user != null} && ${sessionScope.usernameExists}">--%>
                <%--<div class="alert alert-danger alert-dismissible" role="alert">--%>
                    <%--<strong>That username already exists</strong>--%>
                    <%--&lt;%&ndash;<button type="button" class="close" data-dismiss="alert" aria-label="Close">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<span aria-hidden="true">&times;</span>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</button>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</c:if>--%>
        <div class="form-group">
            <label for="email">Email</label>
            <input id="email" name="email" class="form-control" type="text" value=<%=email%>>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" name="password" class="form-control" type="password">
        </div>
            <c:if test="${passwordEmpty}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <strong>Please enter a password</strong>
                </div>
            </c:if>
        <div class="form-group">
            <label for="confirm_password">Confirm Password</label>
            <input id="confirm_password" name="confirm_password" class="form-control" type="password">
        </div>

            <%--if sessionUser is updating show "value=Update"--%>
            <c:if test="${sessionScope.user != null}">
                <input type="submit" class="btn btn-primary btn-block" value="Update">
            </c:if>

            <%--otherwise, show value=Register--%>
            <c:if test="${sessionScope.user == null}">
                <input type="submit" class="btn btn-primary btn-block" value="Register">
            </c:if>

    </form>

    <c:if test="${sessionScope.user != null}">
    <%--delete button--%>
    <form action="/profile/delete" method="post">
        <%--Hidden, but needed for the table request:--%>
        <input type="hidden" name="id" value="${user.id}">
        <button type="submit" class="btn btn-block btn-danger">Delete</button>
    </form>
    </c:if>

    <c:if test="${sessionScope.listOfErrors.size() > 0}">
        <div id="errors" class="alert alert-danger">
            <p>Unable to register user!</p>
            <ul>
                <c:forEach var="message" items="${listOfErrors}">
                    <li><c:out value="${message}"></c:out></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>


</div>
</body>
</html>