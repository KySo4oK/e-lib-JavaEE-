<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="error" /></title>
</head>
    <body>
        <h2>
           <fmt:message key="error"/><br/>
            <i>Error <%= exception %></i>
        </h2>
    <br>
        <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="main"/></a>
        <ul>
            <li><a href="?language=en"><fmt:message key="label.lang.en"/></a></li>
            <li><a href="?language=ua"><fmt:message key="label.lang.ua"/></a></li>
        </ul>


    </body>
</html>