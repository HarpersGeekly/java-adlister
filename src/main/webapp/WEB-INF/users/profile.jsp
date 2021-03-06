<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Your Profile"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp"/>

<div class="container">

    <%--=========================================== VIEWING SESSION PROFILE ======================================--%>

    <c:if test="${param.id == null || sessionScope.user.id == param.id}">
        <%--<h2> ----------------- What it looks like for Session User: ----------------------------------</h2>--%>

        <h4>Welcome, <c:out value="${user.username}"/>!</h4>
        <a href="/profile/edit?id=${user.id}">edit profile</a> |
        <a href="/profile/editPassword?id=${user.id}">change password</a>
        <p>Your registered email is: <c:out value="${user.email}"/>.</p>
        <p>You are currently logged in and can <a href="/ads/create">create ads.</a></p>
        <p> Here is a list of your ads:</p>
        <%--<c:if test="">--%>
        <%--if there aren't ads, then write "You don't have any ads. Create an ad <here>--%>
        <%--</c:if>--%>

    </c:if>
        <%--=========================================== VIEWING SOMEONE ELSES PROFILE =============================--%>

    <c:if test="${param.id != null && sessionScope.user.id != param.id}">
        <%--<h2> ------------- What it looks like when viewing another user's profile: ---------------</h2>--%>

        <h4> ${profile.username}'s Profile!</h4>
        <p> Their email is: <c:out value="${profile.email}"/>.</p>
        <p> Here is a list of ${profile.username}'s ads:</p>

    </c:if>

    <ul>
        <c:forEach var="ad" items="${ads}">
            <li>
                created on: make this happen!
                <h4><c:out value="${ad.title}"/></h4>
                <h5><c:out value="${ad.description}"/></h5>

                <c:if test="${param.id == null || sessionScope.user.id == param.id}">
                    <a href="ads/edit?id=${ad.id}"><input type=button value='Edit'/></a>
                </c:if>
                updated on: make this happen!
            </li>
        </c:forEach>
    </ul>

</div>

</body>
</html>