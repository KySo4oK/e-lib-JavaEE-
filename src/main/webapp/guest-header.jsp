<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
</head>
<body style="text-align: center">
<header>
    <div class="navbar navbar-default" style="background-color: aquamarine;">
        <h1 style="float: left"><fmt:message key="site.name"/></h1>
        <span><a style="float: right; margin-right: 5px" href="?language=en"><fmt:message
                key="label.lang.en"/></a></span>
        <span><a style="float: right; margin-right: 5px" href="?language=ua"><fmt:message
                key="label.lang.ua"/></a></span>
        <span><a style="float: right; margin-right: 5px" href="${pageContext.request.contextPath}/login"><fmt:message
                key="sign.in"/></a></span>
        <span><a style="float: right; margin-right: 5px"
                 href="${pageContext.request.contextPath}/registration"><fmt:message key="sign.up"/></a></span>
        <span><a style="float: right; margin-right: 5px" href="/">
                <fmt:message key="main"/>
            </a></span>
    </div>
</header>
</body>
</html>