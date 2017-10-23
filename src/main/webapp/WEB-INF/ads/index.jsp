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
    <h1>Here Are all the ads!</h1>

    <ul>
        <c:forEach var="ad" items="${ads}">
            <li>
                <h2>Ad -- <c:out value="${ad.id}" />:</h2>
                <h4>User: <c:out value="${ad.userId}"/></h4>
                <h2><c:out value="${ad.title}" /></h2>
                <h4><c:out value="${ad.description}" /></h4>
            </li>
        </c:forEach>
    </ul>
</div>

</body>
</html>
