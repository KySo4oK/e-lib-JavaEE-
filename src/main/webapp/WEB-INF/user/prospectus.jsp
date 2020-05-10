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
<body style="text-align: center">
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
            <span><a style="float: right; margin-right: 5px" href="/user">
                <fmt:message key="my.books"/>
            </a></span>
            <span><a style="float: right; margin-right: 5px" href="/prospectus">
                <fmt:message key="prospectus"/>
            </a></span>
            <span><a style="float: right; margin-right: 5px" href="/">
                <fmt:message key="main"/>
            </a></span>
        </div>
    </header>
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
                        <div v-for="tag in book.tags">
                            <h1 v-text="tag">
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
    <!--    <footer>-->
    <!--        {{ new Date().getFullYear() }} — <strong>e-lib</strong>-->
    <!--    </footer>-->
</div>
<script type="text/javascript">
    let app = new Vue({
        el: '#app',
        data: {
            books: [],
            tags: [],
            addedTags: [],
            addedAuthors: [],
            partOfName: null,
            authors: [],
            message: null,
            siteName: 'e-lib',
            page: 0,
            filter: false,
        },
        async mounted() {
            await this.getBooks();
            await this.getAuthors();
            await this.getTags()
        },
        methods: {
            async changeList() {
                this.page = 0;
                this.filter = true;
                this.loadByFilter();
            },
            async loadByFilter() {
                let filter = {
                    tags: this.addedTags,
                    authors: this.addedAuthors,
                    name: '%' + this.partOfName + '%',
                };
                let res = await axios.post('/filter/' + this.page + "/5", filter);
                if (!res) return;
                if (this.page === 0) {
                    this.books = res.data;
                } else {
                    this.books = this.books.concat(res.data);
                }
                this.page++;
            },
            async getTags() {
                let res = await axios.get('/tags');
                if (!res) return;
                this.tags = res.data;
                console.log(this.books);
            },
            async getBooks() {
                let res = await axios.get('/books/' + this.page + "/5");
                if (!res) return;
                this.books = this.books.concat(res.data);
            },
            async getAuthors() {
                let res = await axios.get('/authors');
                if (!res) return;
                this.authors = res.data;
            },
            async orderBook(book) {
                let res = await axios.post('/order', book)
                    .catch(function (error) {
                        if (error.response) {
                            if (error.response.status == '404')
                                return;//todo show field with this problem
                        }
                    });
                if (!res) return;
                this.message = res.data + book.name;
            },
            async loadMore() {
                if (this.filter) {
                    this.page++;
                    this.loadByFilter();
                } else {
                    this.page++;
                    this.getBooks();
                }
            }
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

    /*footer {*/
    /*    position: absolute;*/
    /*    left: 0;*/
    /*    bottom: 0;*/
    /*    width: 100%;*/
    /*    height: 80px;*/
    /*}*/
</style>
</body>
</html>