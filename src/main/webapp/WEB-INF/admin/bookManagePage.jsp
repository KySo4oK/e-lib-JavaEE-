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
    <jsp:include page="admin-header.jsp"/>
    <div style="padding-top: 10%; text-align: center; width: 83%; float: right">
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
    <div v-if="picked==1" id="add" style="max-width: 500px; margin: auto;">
        <input id="book_name" type="text" class="form-control" placeholder="<fmt:message key="book.name.en"/>"
               v-model="addedBook.name">
        <input id="book_name_ua" type="text" class="form-control" placeholder="<fmt:message key="book.name.ua"/>"
               v-model="addedBook.nameUa">
        <!--            tags-->
        <div id="tagsChecks" class="col-md-6 boxes">
            <div v-for="tag in tags">
                <div style="display: inline;">
                    <input style="display: inline; float: left; margin-left: 0px" type="radio"
                           v-bind:value="tag"
                           v-model="addedBook.tag">
                    <h6 style="display: inline; margin-right: 0px;">{{tag}}</h6>
                </div>
            </div>
        </div>
        <br>
        <label for="tagsCheckbox" style="margin: 20px ;">
            <fmt:message key="select.tags"/></label>
        <br>
        <!--            authors-->
        <div style="padding-top: 10px" id="authorsСhecks" class="col-md-6 boxes">
            <div v-for="author in authors">
                <div style="display: inline;">
                    <input style="display: inline; float: left; margin-left: 0px" type="checkbox"
                           v-bind:value="author"
                           v-model="addedBook.authors">
                    <h6 style="display: inline;">{{author}}</h6>
                </div>
            </div>
        </div>
        <br>
        <label for="authorsCheckbox" style="margin: 20px">
            <fmt:message key="select.authors"/>
        </label>
        <br>
        <div style="text-align: center; margin-top: 20px">
            <button v-on:click="addBook" class="btn btn-warning" style="width: 30%;">
                <fmt:message key="load"/></button>
        </div>
    </div>
    <!--    edit or delete-->
    <div v-else>
        <!--        filter-->
        <div class="w3-sidebar w3-light-grey w3-bar-block"
             style="width:17%; float: left; padding-top: 10%; background-color: lightgrey;">
            <!--            name-->
            <div>
                <input id="partOfName" class="form-control" type="text" v-model="partOfName">
                <label for="partOfName">
                    <fmt:message key="part.of.name"/></label>
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
            <label for="tagsCheckbox" style="margin: 20px ;">
                <fmt:message key="select.tags"/>
            </label>
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
            <label for="authorsCheckbox" style="margin: 20px">
                <fmt:message key="select.authors"/></label>
            <br>
            <button class="btn btn-warning" style="width: 30%;" @click="changeList">
                <fmt:message key="load"/>
            </button>
        </div>
        <!--        books -->
        <div>
            <table style="width: 75%;margin-left:25%; padding-top: 10%; display: table" class="table table-borderless">
                <thead>
                <tr>
                    <th><fmt:message key="book.name.en"/></th>
                    <th><fmt:message key="book.name.ua"/></th>
                    <th><fmt:message key="edit"/></th>
                    <th><fmt:message key="delete"/></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="book in books" style="padding-right: 0px">
                    <td>
                        <h1><input type="text" class="form-control" :value="book.name" v-model="book.name"></h1></td>
                    <td>
                    <td>
                        <h1><input type="text" class="form-control" :value="book.nameUa" v-model="book.nameUa"></h1></td>
                    <td>
                        <button class="btn btn-warning" v-on:click="editBook(book)"><fmt:message key="edit"/></button>
                    </td>
                    <td>
                        <button class="btn btn-warning" v-on:click="deleteBook(book)"><fmt:message
                                key="delete"/></button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <button class="btn btn-warning" style="width: 30%;" @click="loadMore()"><fmt:message key="load.more"/>
        </button>
    </div>
</div>
</div>
</div>
</div>

<script type="text/javascript">
    let app = new Vue({
        el: '#app',
        data: {
            addedBook: {
                name: "",
                nameUa: "",
                authors: [],
                tag: "",
            },
            picked: '',
            books: [],
            tags: [],
            addedTags: [],
            addedAuthors: [],
            partOfName: null,
            authors: [],
            message: null,
            page: 0,
            siteName: 'e-lib'
        },
        async mounted() {
            await this.getBooks();
            await this.getAuthors();
            await this.getTags();
        },
        methods: {
            async addBook() {
                let res = await axios.post('/admin/add', this.addedBook)
                    .catch(function (error) {
                        if (error.response) {
                            if (error.response.status == '404')
                                return;//todo show field with this problem
                        }
                    });
            },
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
                let res = await axios.post('/admin/filter/' + this.page + "/5", filter);
                if (!res) return;
                if (this.page === 0) {
                    this.books = res.data;
                } else {
                    this.books = this.books.concat(res.data);
                }
                this.page++;
            },
            async getTags() {
                let res = await axios.get('/user-admin/tags');
                if (!res) return;
                this.tags = res.data;
                console.log(this.books);
            },
            async getBooks() {
                let res = await axios.get('/admin/books/' + this.page + "/5");//todo normal updating
                if (!res) return;
                this.books = this.books.concat(res.data);
            },
            async getAuthors() {
                let res = await axios.get('/user-admin/authors');
                if (!res) return;
                this.authors = res.data;
            },
            async editBook(book) {
                let res = await axios.put('/admin/edit', book)
                    .catch(function (error) {
                        if (error.response) {
                            if (error.response.status == '404')
                                return;//todo show field with this problem
                        }
                    });
                if (!res) return;
                this.message = res.data + book.name;
                this.page = 0;
                await this.getBooks();
            },
            async deleteBook(book) {
                let res = await axios.delete('/admin/delete/' + book.id)
                    .catch(function (error) {
                        if (error.response) {
                            if (error.response.status == '404')
                                return;//todo show field with this problem
                        }
                    });
                if (!res) return;
                this.message = res.data + book.name;
                this.page = 0;
                await this.getBooks();
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
</style>
</body>
</html>