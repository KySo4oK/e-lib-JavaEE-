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
    <title>Login</title>
</head>
<body>
<form action="login" method="post">
    <table style="with: 50%">
        <tr>
            <td><fmt:message key="username"/></td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td><fmt:message key="password"/></td>
            <td><input type="password" name="password"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
<ul>
    <li><a href="?language=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?language=ua"><fmt:message key="label.lang.ua"/></a></li>
</ul>
</body>
</html>
