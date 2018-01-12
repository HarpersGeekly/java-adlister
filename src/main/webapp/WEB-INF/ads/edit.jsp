<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Create a new Ad" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />
<div class="container">
    <h1>Edit Ad</h1>
    <form action="/ads/edit" method="post">

        <%--Hidden, but needed for the table request:--%>
        <input id="id" name="id" type="hidden" value="${ad.id}">

        <%--What is seen:--%>
        <div class="form-group">
            <label for="title">Title</label>
            <input id="title" name="title" class="form-control" type="text" value="${ad.title}">
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description" class="form-control" type="text">${ad.description}</textarea>
        </div>
        <input type="submit" class="btn btn-block btn-primary" value="Update Ad">
    </form>

    <%--delete button--%>
    <form action="/ads/delete" method="post">
        <%--Hidden, but needed for the table request:--%>
        <input type="hidden" name="id" value="${ad.id}">
        <button type="submit" class="btn btn-block btn-danger">Delete</button>
    </form>

</div>
</body>
</html>
