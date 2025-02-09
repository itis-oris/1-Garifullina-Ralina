<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container-profile">
    <div class="header">
        <h1>Редактировать профиль</h1>
    </div>


    <!-- Сообщения об ошибках или успехе -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <div class="profile-info">
        <!-- Форма для редактирования профиля -->
        <form action="profileEdit" method="POST">

            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" id="name" name="name" value="${user.userName}" required>
                <c:if test="${not empty errorFirstName}">
                    <p class="error-message">${errorFirstName}</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="birthDate">Дата рождения:</label>
                <input type="date" id="birthDate" name="birthDate" value="${user.birthDate != null ? user.birthDate : ''}" required>
                <c:if test="${not empty errorBirthDate}">
                    <p class="error-message">${errorBirthDate}</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="zodiac">Знак зодиака:</label>
                <input type="text" id="zodiac" name="zodiac" value="${zodiac.signName}" readonly>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>
                <c:if test="${not empty errorEmail}">
                    <p class="error-message">${errorEmail}</p>
                </c:if>
            </div>



            <div class="form-group">
                <label>
                    <input type="checkbox" id="changePassword" name="changePassword" onchange="togglePasswordChange()">
                    <c:if test="${not empty changePassword || not empty errorPassword || not empty errorConfirmPassword }">checked</c:if>
                    Изменить пароль
                </label>
            </div>

            <div id="passwordSection" style="display: ${not empty errorPassword || not empty errorConfirmPassword || changePassword ? 'block' : 'none'};">


                <div class="form-group">
                    <label for="password">Новый пароль:</label>
                    <input type="password" id="password" name="password">
                    <c:if test="${not empty errorPassword}">
                        <p class="error-message">${errorPassword}</p>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Подтвердите новый пароль:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword">
                    <c:if test="${not empty errorConfirmPassword}">
                        <p class="error-message">${errorConfirmPassword}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn-save">Сохранить</button>
            </div>
        </form>

        <div class="form-buttons">
            <a href="profile" class="btn-back">Вернуться в профиль</a>
        </div>
    </div>
</div>
