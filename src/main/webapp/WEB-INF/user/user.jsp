<%--
  Created by IntelliJ IDEA.
  User: KySo4oK
  Date: 11.04.2020
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <title><fmt:message key="user"/> </title>
    <a href="${pageContext.request.contextPath}/logout"><fmt:message key="logout"/></a>
</head>
<body>
<h1><fmt:message key="user"/></h1>
<ul>
    <li><a href="?language=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?language=ua"><fmt:message key="label.lang.ua"/></a></li>
</ul>
</body>
</html>
