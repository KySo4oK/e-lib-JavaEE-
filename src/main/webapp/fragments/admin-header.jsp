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
<body>
<header>
    <div class="navbar navbar-default">
        <span class="header-title"><fmt:message key="site.name"/></span>
        <div class="navigation">
            <div class="header-link">
                <a href="/admin"><fmt:message key="orders"/></a>
            </div>
            <div class="header-link">
                <a href="/admin/bookManage"><fmt:message key="books"/></a>
            </div>
            <div class="header-link">
                <a href="/logout"><fmt:message key="logout"/></a>
            </div>
            <div class="header-link">
                <a href="/admin?language=en"><fmt:message key="label.lang.en"/></a>
            </div>
            <div class="header-link">
                <a href="/admin?language=ua"><fmt:message key="label.lang.ua"/></a>
            </div>
        </div>
    </div>
</header>
</body>
</html>