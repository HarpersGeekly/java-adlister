<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Your Profile" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">

    <c:if test="${sessionScope.user != null}">
    <h1>Welcome, <c:out value="${sessionScope.user.username}"/>!</h1>
    <p>Your registered email is: <c:out value="${sessionScope.user.email}"/>.</p>
    <p>You are currently logged in.</p>
    <p> Here is a list of your ads:</p>
    <%--<c:if test="">--%>
        <%--if there aren't ads, then write "You don't have any ads. Create an ad <here>--%>
    <%--</c:if>--%>

    <ul>
        <c:forEach var="ad" items="${ads}">
            <li>
                <h1><c:out value="${ad.title}"/></h1></a>
                <h3><c:out value="${ad.description}"/></h3>

            <c:if test="${sessionScope.user != null}">
                <input type=button value='Edit'/>
                <input type=button value='Delete'/>
            </c:if>
            </li>
        </c:forEach>
    </ul>
    </c:if>

    <c:if test="${sessionScope.user == null}">
    <h1> ${user.username}'s Profile!</h1>
    <p> Their email is: <c:out value="${sessionScope.user.email}"/>.</p>
    <p> Here is a list of ${user.username}'s ads:</p>

    <ul>
        <c:forEach var="ad" items="${ads}">
            <li>
                <h1><c:out value="${ad.title}"/></h1></a>
                <h3><c:out value="${ad.description}"/></h3>
            </li>
        </c:forEach>
    </ul>
    </c:if>
</div>

</body>
</html>