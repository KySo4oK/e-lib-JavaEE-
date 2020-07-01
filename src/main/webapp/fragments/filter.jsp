<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <title>Title</title>
</head>
<body>
<div class="w3-sidebar w3-light-grey w3-bar-block">
    <!--            name-->
    <div>
        <fmt:message key="username" var="partOfName"/>
        <input id="partOfName" class="form-control" placeholder="${partOfName}" type="text" v-model="partOfName">
    </div>
    <br>
    <!--            tags-->
    <div id="tagsCheckbox" class="col-md-6 boxes">
        <label><fmt:message key="select.tags"/></label>
        <div v-for="tag in tags">
            <input class="input-checkbox" type="checkbox"
                   v-bind:value="tag"
                   v-model="addedTags">
            {{tag}}
        </div>
        <!--            authors-->
        <label><fmt:message key="select.authors"/></label>
        <div v-for="author in authors">
            <input class="input-checkbox" type="checkbox"
                   v-bind:value="author"
                   v-model="addedAuthors">
            {{author}}
        </div>
        <button class="btn btn-warning" @click="changeList"><fmt:message key="load"/></button>
    </div>
</div>
</body>
</html>
