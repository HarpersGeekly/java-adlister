<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Viewing All The Ads" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">
    <h1>Welcome to AdLister!</h1>
    <h2>Here are all the ads:</h2>

    <ul>
        <c:forEach var="ad" items="${ads}">
            <li>
                <%--I want to get an id from a user clicking on the link:--%>
                <a href="/show?id=${ad.id}"><h2><c:out value="${ad.title}"/></h2></a>
                <h3><c:out value="${ad.description}"/></h3>
                <h5>Username: <c:out value="${ad.username}"/></h5>
            </li>
        </c:forEach>
    </ul>
</div>

</body>
</html>

