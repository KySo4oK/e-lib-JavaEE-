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
<div class="w3-sidebar w3-light-grey w3-bar-block" style="width:17%; float: left; padding-top: 10%;">
    <!--            name-->
    <div>
        <input id="partOfName" class="form-control" type="text" v-model="partOfName">
        <label for="partOfName"><fmt:message key="part.of.name"/></label>
    </div>
    <br>
    <!--            tags-->
    <div id="tagsCheckbox" class="col-md-6 boxes">
        <div v-for="tag in tags">
            <div style="display: inline;">
                <input style="display: inline; float: left; margin-left: 0px" type="checkbox"
                       v-bind:value="tag"
                       v-model="addedTags">
                <h6 style="display: inline; margin-right: 0px;">{{tag}}</h6>
            </div>
        </div>
    </div>
    <br>
    <label for="tagsCheckbox" style="margin: 20px ;"><fmt:message key="select.tags"/></label>
    <br>
    <!--            authors-->
    <div style="padding-top: 10px" id="authorsCheckbox" class="col-md-6 boxes">
        <div v-for="author in authors">
            <div style="display: inline;">
                <input style="display: inline; float: left; margin-left: 0px" type="checkbox"
                       v-bind:value="author"
                       v-model="addedAuthors">
                <h6 style="display: inline;">{{author}}</h6>
            </div>
        </div>
    </div>
    <br>
    <label for="authorsCheckbox" style="margin: 20px"><fmt:message key="select.authors"/></label>
    <br>
    <button class="btn btn-warning" style="width: 30%;" @click="changeList"><fmt:message key="load"/></button>
</div>
</body>
</html>
