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
        <%@include file="/WEB-INF/js/user.js"%>
    </script>
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/footer.css"%>
    </style>
    <style>
        <%@include file="/css/show-orders.css"%>
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div id="app">
    <%@include file="../../fragments/user-header.jspf" %>
    <div class="check-fragment">
        <input type="radio" id="one" value="1" v-model="picked">
        <label for="one"><fmt:message key="orders"/></label>
        <input type="radio" id="two" value="2" v-model="picked">
        <label for="two"><fmt:message key="my.books"/></label>
        <hr>
    </div>
    <!--        passive orders -->
    <div v-if="picked==1">
        <table class="table table-borderless">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="book.name"/></th>
                <th><fmt:message key="order.date"/></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(order, index) in passiveOrders">
                <td>{{index+1}}</td>
                <td>{{order.bookName}}</td>
                <td>{{order.startDate}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <!--    active orders-->
    <div v-else>
        <table class="table table-borderless">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="book.name"/></th>
                <th><fmt:message key="start.date"/></th>
                <th><fmt:message key="end.date"/></th>
                <th><fmt:message key="return"/></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(order, index) in activeOrders">
                <td>{{index+1}}</td>
                <td>{{order.bookName}}</td>
                <td>{{order.startDate}}</td>
                <td>{{order.endDate}}</td>
                <td>
                    <button class="btn btn-danger" v-on:click="returnBook(order)">
                        <i class="fa fa-undo" aria-hidden="true"></i>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<%@include file="../../fragments/footer.jspf" %>
</body>
</html>
