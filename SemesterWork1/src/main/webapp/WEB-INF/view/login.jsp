<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/register.css">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Вход в систему</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="email">Почта:</label>
                <input id="email" name="email" type="email" placeholder="Введите адрес электронной почты">
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input id="password" name="password" type="password" placeholder="Введите пароль">
            </div>
            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
        </form>
        <p>Еще нет учетной записи? <a href="registration" class="link">Зарегистрироваться</a></p>

    </div>
</div>

</body>
</html>
