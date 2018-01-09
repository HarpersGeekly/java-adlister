<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Show One Ad" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">
    <h1><c:out value="${ad.title}"/></h1>
    <h3><c:out value="${ad.description}"/></h3>
    <a href="/profile?id=${sessionScope.user.id}"><h4>username: <c:out value="${ad.username}"/></h4></a>
    <h4>email: <c:out value="${ad.email}"/></h4>
</div>

</body>
</html>