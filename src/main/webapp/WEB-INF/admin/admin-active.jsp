<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<html lang="en" xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/show-orders.css"%>
    </style>
    <style>
        <%@include file="/css/filter.css"%>
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<body>
<div id="app">
    <%@include file="../../fragments/admin-header.jspf" %>
    <!--    active orders-->
    <div class="table-wrapper">
        <table class="table table-borderless">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="book.name"/></th>
                <th><fmt:message key="username"/></th>
                <th><fmt:message key="start.date"/></th>
                <th><fmt:message key="end.date"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}" varStatus="status">
                <tr>
                    <td><c:out value="${ status.count }"/></td>
                    <td><c:out value="${ order.bookName }"/></td>
                    <td><c:out value="${ order.userName }"/></td>
                    <td><c:out value="${ order.startDate }"/></td>
                    <td><c:out value="${ order.endDate }"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@include file="../../fragments/footer.jspf" %>
</body>
</html>