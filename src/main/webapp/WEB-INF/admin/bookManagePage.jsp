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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/book-manage.js"%>
    </script>
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/footer.css"%>
    </style>
    <style>
        <%@include file="/css/filter.css"%>
    </style>
    <style>
        <%@include file="/css/book-manage.css"%>
    </style>
<body>
<div id="app">
    <jsp:include page="../../fragments/admin-header.jsp"/>
    <div class="check-fragment">
        <input type="radio" id="one" value="1" v-model="picked">
        <label for="one">
            <fmt:message key="add.book"/>
        </label>
        <input type="radio" id="two" value="2" v-model="picked">
        <label for="two">
            <fmt:message key="edit.delete"/></label>
        <hr>
    </div>
    <!--    add-->
    <div v-if="picked==1" id="add">
        <div class="w3-sidebar w3-light-grey w3-bar-block">
            <div class="col-md-6 boxes" style="padding-top: 15%;">
                <!--            tags-->
                <label><fmt:message key="select.tags"/></label>
                <div v-for="tag in tags">
                    <input class="input-checkbox" type="checkbox"
                           v-bind:value="tag"
                           v-model="addedBook.tags">
                    {{tag}}
                </div>
                <!--            authors-->
                <label><fmt:message key="select.tags"/></label>
                <div v-for="author in authors">
                    <input class="input-checkbox" type="checkbox"
                           v-bind:value="author"
                           v-model="addedBook.authors">
                    {{author}}
                </div>
            </div>
        </div>
        <div class="book-names-input">
            <input id="book_name" type="text" class="form-control" placeholder="<fmt:message key="book.name.en"/>"
                   v-model="addedBook.name">
            <input id="book_name_ua" type="text" class="form-control" placeholder="<fmt:message key="book.name.ua"/>"
                   v-model="addedBook.nameUa">
            <div>
                <button v-on:click="addBook" class="btn btn-warning" style="width: 30%;"><fmt:message key="load"/></button>
            </div>
        </div>
    </div>
    <!--    edit or delete-->
    <div v-else>
        <!--        filter-->
        <jsp:include page="../../fragments/filter.jsp"/>
        <!--        books -->
        <div>
            <table class="table table-borderless">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message key="book.name.en"/></th>
                    <th><fmt:message key="book.name.ua"/></th>
                    <th><fmt:message key="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(book, index) in books" :key="book.id">
                    <td>{{index+1}}</td>
                    <td>
                        <h1><input type="text" class="form-control" v-model="book.name"></h1></td>
                    <td>
                    <td>
                        <h1><input type="text" class="form-control" v-model="book.nameUa"></h1>
                    </td>
                    <td class="buttons-actions">
                        <button class="btn btn-success" v-on:click="editBook(book)">
                            <i class="fa fa-pencil" aria-hidden="true"></i>
                        </button>
                        <button class="btn btn-danger" v-on:click="deleteBook(book)">
                            <i class="fa fa-times" aria-hidden="true"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <button class="btn btn-success" @click="loadMore()">
            <i class="fa fa-chevron-down"></i>
        </button>
    </div>
</div>
<jsp:include page="../../fragments/footer.jsp"/>
</body>
</html>