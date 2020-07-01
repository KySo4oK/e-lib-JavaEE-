<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="main"/></title>
<%--    <link href="/WEB-INF/css/header.css" rel="stylesheet" type="text/css">--%>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/header.css"%>--%>
<%--    </style>--%>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/footer.css"%>--%>
<%--    </style>--%>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/guest.css"%>--%>
<%--    </style>--%>
</head>
<body>
<jsp:include page="guest-header.jsp"/>
</body>
</html>