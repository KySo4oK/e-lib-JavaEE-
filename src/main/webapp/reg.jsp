<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <title><fmt:message key="sign.up"/></title>
</head>
<body>
<jsp:include page="guest-header.jsp"/>
<form action="registration" method="post">
    <table style="with: 50%">
        <tr>
            <td><fmt:message key="username"/></td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td><fmt:message key="email"/></td>
            <td><input type="email" name="email"/></td>
        </tr>
        <tr>
            <td><fmt:message key="phone"/></td>
            <td><input type="text" name="phone"/></td>
        </tr>
        <tr>
            <td><fmt:message key="password"/></td>
            <td><input type="password" name="password"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
