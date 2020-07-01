<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="main"/></title>
    <style>
        <%@include file="/css/header.css"%>
    </style>
    <style>
        <%@include file="/css/footer.css"%>
    </style>
    <style>
        <%@include file="/css/guest.css"%>
    </style>
</head>
<body>
<div id="app">
    <jsp:include page="fragments/guest-header.jsp"/>
    <div class="task-wrapper">
        <strong>
            16. Система Библиотека. Создайте Каталог, по которому можно искать по:
            <ul>
                <li>Автору (одному из группы).</li>
                <li>Названию книги или её фрагменте.</li>
                <li>Одному из ключевых слов книги (атрибут книги).</li>
            </ul>
            Каталог книг заполняет Администратор, добавляя и изменяя/удаляя их. Каждая книга должна иметь адрес (место
            на
            полке) или читателя. Читатель чтобы взять книгу регистрируется, оставляя э-мейл и номер телефона. Книга
            может
            быть взята у Администратора в библиотеке на время не более месяца,
            только в случае если книга доступна в библиотеке. Администратор должен иметь страницу где отражаются взятые
            книги и читатели, которые пользуются книгой.
        </strong>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>