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
        <%@include file="/WEB-INF/js/admin.js"%>
    </script>
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/show-orders.css"%>
    </style>
    <style>
        <%@include file="/css/filter.css"%>
    </style>
<body>
<div id="app">
    <jsp:include page="../../fragments/admin-header.jsp"/>
    <div style="padding-top: 7%; text-align: center; width: 100%; float: right">
        <input type="radio" id="one" value="1" v-model="picked">
        <label for="one"><fmt:message key="orders"/></label>
        <input type="radio" id="two" value="2" v-model="picked">
        <label for="two"><fmt:message key="users.books"/></label>
        <hr>
    </div>
    <!--        passive orders -->
    <div v-if="picked==1">
        <table style="width: 100%; padding-top: 10%; display: table" class="table table-borderless">
            <thead>
            <tr>
                <th><fmt:message key="book.name"/></th>
                <th><fmt:message key="username"/></th>
                <th><fmt:message key="order.date"/></th>
                <th><fmt:message key="permit"/></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="order in passiveOrders">
                <td><h1 v-text="order.bookName"></h1></td>
                <td><h1 v-text="order.userName"></h1></td>
                <td><h1 v-text="order.startDate"></h1></td>
                <td>
                    <button class="btn btn-warning" v-on:click="permitOrder(order)">
                        <fmt:message key="permit"/>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <!--    active orders-->
    <div v-else>
        <table style="width: 100%; padding-top: 10%; display: table" class="table table-borderless">
            <thead>
            <tr>
                <th><fmt:message key="book.name"/></th>
                <th><fmt:message key="username"/></th>
                <th><fmt:message key="start.date"/></th>
                <th><fmt:message key="end.date"/></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="order in activeOrders">
                <td><h1 v-text="order.bookName"></h1></td>
                <td><h1 v-text="order.userName"></h1></td>
                <td><h1 v-text="order.startDate"></h1></td>
                <td><h1 v-text="order.endDate"></h1></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>