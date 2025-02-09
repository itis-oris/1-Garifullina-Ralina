<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/register.css">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Регистрация</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="<c:url value="/registration"/>" method="POST">
            <div class="form-group">
                <label for="email">Почта:</label>
                <input id="email" name="email" type="email" placeholder="Введите адрес электронной почты">
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input id="password" name="password" type="password" placeholder="Введите пароль">
            </div>
            <div class="form-group">
                <label for="userName">Имя:</label>
                <input id="userName" name="userName" type="text" placeholder="Введите имя пользователя">
            </div>
            <div class="form-group">
                <label for="birthDate">Дата рождения:</label>
                <input id="birthDate" name="birthDate" type="date" placeholder="Выберите дату рождения">
            </div>
            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>