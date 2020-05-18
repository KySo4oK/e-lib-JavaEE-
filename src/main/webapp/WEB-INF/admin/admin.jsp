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
<html lang="en" xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
<body>
<div id="app">
    <header>
        <div class="navbar navbar-default" style="background-color: aquamarine; margin: 0 !important">
            <h1 style="float: left">{{ siteName }}</h1>
            <span>
                <a style="float: right; margin-right: 5px" href="/user?language=ua">
                    <fmt:message key="label.lang.ua"/>
                </a>
            </span>
            <span>
                <a style="float: right; margin-right: 5px" href="/user?language=en">
                    <fmt:message key="label.lang.en"/>
                </a>
            </span>
            <span><a style="float: right; margin-right: 5px" href="/logout">
                <fmt:message key="logout"/>
            </a></span>
            <span>
                <a style="float: right; margin-right: 5px" href="/book">
                    <fmt:message key="books"/>
                </a>
            </span>
            <span>
                <a style="float: right; margin-right: 5px" href="/orders">
                    <fmt:message key="orders"/>
                </a>
            </span>
            <span>
                <a style="float: right; margin-right: 5px" href="/">
                    <fmt:message key="main"/>
                </a>
            </span>
        </div>
    </header>
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
<script type="text/javascript">
    let app = new Vue({
        el: '#app',
        data: {
            activeOrders: [],
            passiveOrders: [],
            siteName: 'e-lib',
            picked: '',
        },
        async mounted() {
            await this.getActiveOrders();
            await this.getPassiveOrders();
        },
        methods: {
            async getActiveOrders() {
                let res = await axios.get('/admin/active');
                if (!res) return;
                this.activeOrders = res.data;
            },
            async getPassiveOrders() {
                let res = await axios.get('/admin/passive');
                if (!res) return;
                this.passiveOrders = res.data;
            },
            async permitOrder(order) {
                let res = await axios.put('/admin/permit', order)
                    .catch(function (error) {
                        if (error.response) {
                            if (error.response.status == '404')
                                return;//todo show field with this problem
                        }
                    });
                if (!res) return;
                await this.getActiveOrders();
                await this.getPassiveOrders();
            },

        }


    });
</script>
<style lang="less">
    input {
        margin-top: 17px;
    }

    body {
        font-family: Arial;
        font-style: normal;
    }

    header {
        position: fixed;
        height: 10%;
        left: 0;
        top: 0;
        width: 100%;
        z-index: 10;
    }

    span {
        font-size: 30px;
        height: content-box;
    }
</style>
</body>
</html>