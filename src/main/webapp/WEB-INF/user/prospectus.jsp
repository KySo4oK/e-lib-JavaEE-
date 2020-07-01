<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<html lang="en" xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/prospectus.js"%>
    </script>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/header.css"%>--%>
<%--    </style>--%>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/footer.css"%>--%>
<%--    </style>--%>
<%--    <style>--%>
<%--        <%@include file="/WEB-INF/css/filter.css"%>--%>
<%--    </style>--%>
<%--    <style>--%>
<%--        <%@include file="${pageContext.request.contextPath}/WEB-INF/css/prospectus.css"%>--%>
<%--    </style>--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/css/prospectus.css">
<body style="text-align: center">
<div id="app">
    <jsp:include page="user-header.jsp"/>
    <main>
        <!--        filter-->
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
        <!--        books -->
        <div style="padding-top: 10%;">
            <table style="width: 75%;margin-left:25%; padding-top: 10%; display: table" class="table table-borderless">
                <thead>
                <tr>
                    <th><fmt:message key="name"/></th>
                    <th><fmt:message key="authors"/></th>
                    <th><fmt:message key="tags"/></th>
                    <th><fmt:message key="order"/></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="book in books">
                    <td><h1 v-text="book.name"></h1></td>
                    <td>
                        <div v-for="author in book.authors">
                            <h1 v-text="author">
                            </h1>
                        </div>
                    </td>
                    <td>
                        <div>
                            <h1 v-text="book.tag">
                            </h1>
                        </div>
                    </td>
                    <td>
                        <button class="btn btn-warning" style="height: 100%; width: 100%;" v-on:click="orderBook(book)">
                            <fmt:message key="order"/>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <button class="btn btn-warning" style="width: 30%;" @click="loadMore()">
            <fmt:message key="load.more"/>
        </button>
    </main>
</div>
</body>
</html>