<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <title>Login</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/footer.css"%>
    </style>
    <style>
        <%@include file="/css/guest.css"%>
    </style>
</head>
<body style="text-align:center">
<jsp:include page="fragments/guest-header.jsp"/>
<div style="margin:0 auto;width:75%;text-align:left">
    <p class="h3 text-center mb-4"><fmt:message key="sign.in"/></p>
    <form action="login" method="post" style="max-width: 500px; margin: auto;">
        <fmt:message key="username" var="username"/>
        <input id="username" class="form-control" placeholder="${username}" type="text" name="username">
        <fmt:message key="password" var="password"/>
        <input type="password" class="form-control" placeholder="${password}" name="password" id="password">
        <div style="text-align: center; margin-top: 20px">
            <fmt:message key="submit" var="submit"/>
            <input class="btn btn-warning" type="submit" name="submit" value="${submit}">
        </div>
    </form>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>