<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <title><fmt:message key="sign.up"/></title>
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
<body>
<%@include file="fragments/guest-header.jspf" %>
<div class="form-wrapper">
    <p class="h3 text-center mb-4"><fmt:message key="sign.up"/></p>
    <form action="registration" method="post">
        <fmt:message key="username" var="username"/>
        <input id="username" class="form-control" type="text" placeholder="${username}" name="username">
        <fmt:message key="phone" var="phone"/>
        <input id="phone" class="form-control" type="text" placeholder="${phone}" name="phone">
        <fmt:message key="email" var="email"/>
        <input type="email" class="form-control" id="email" placeholder="${email}" name="email">
        <fmt:message key="password" var="password"/>
        <input type="password" class="form-control" id="password" placeholder="${password}" name="password">
        <div>
            <fmt:message key="submit" var="submit"/>
            <input class="btn btn-warning" type="submit" name="submit" value="${submit}">
        </div>
    </form>
</div>
<%@include file="fragments/footer.jspf" %>
</body>
</html>