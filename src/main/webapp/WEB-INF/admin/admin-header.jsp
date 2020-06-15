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
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
<body>
<header>
    <div class="navbar navbar-default" style="background-color: aquamarine; margin: 0 !important">
        <h1 style="float: left">{{ siteName }}</h1>
        <span>
                <a style="float: right; margin-right: 5px" href="/admin?language=ua">
                    <fmt:message key="label.lang.ua"/>
                </a>
            </span>
        <span>
                <a style="float: right; margin-right: 5px" href="/admin?language=en">
                    <fmt:message key="label.lang.en"/>
                </a>
            </span>
        <span><a style="float: right; margin-right: 5px" href="/logout">
                <fmt:message key="logout"/>
            </a></span>
        <span>
                <a style="float: right; margin-right: 5px" href="/admin/bookManage">
                    <fmt:message key="books"/>
                </a>
            </span>
        <span>
                <a style="float: right; margin-right: 5px" href="/admin">
                    <fmt:message key="orders"/>
                </a>
            </span>
        <span>
                <a style="float: right; margin-right: 5px" href="/">
                    <fmt:message key="main"/>
                </a>
            </span>
    </div>
</header>
<style lang="less">
    input {
        margin-top: 17px;
    }

    body {
        font-family: Arial;
        font-style: normal;
    }

    header {
        position: fixed;
        height: 10%;
        left: 0;
        top: 0;
        width: 100%;
        z-index: 10;
    }

    span {
        font-size: 30px;
        height: content-box;
    }
</style>
</body>
</html>