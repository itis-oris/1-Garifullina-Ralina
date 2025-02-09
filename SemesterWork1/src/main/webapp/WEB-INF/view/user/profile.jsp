<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container-profile">

    <jsp:include page="/WEB-INF/view/sidebarMenu.jsp" />

    <div class="header">
        <h1>Профиль пользователя</h1>
    </div>

    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>

    <!-- Сообщения об ошибках или успехе -->
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <div class="profile-info">
        <p><strong>Имя:</strong> ${user.userName}</p>
        <p><strong>Email:</strong> ${user.email}</p>
        <p><strong>Дата рождения:</strong> ${user.birthDate != null ? user.birthDate : "Не указана"}</p>
        <p><strong>Знак зодиака:</strong> ${zodiac.signName}</p>
    </div>

    <div class="form-buttons">
        <a href="${pageContext.request.contextPath}/profileEdit" class="btn-edit-profile">Редактировать профиль</a>
    </div>

    <form action="${pageContext.request.contextPath}/profile" method="POST" onsubmit="return confirm('Вы уверены, что хотите удалить аккаунт? Это действие необратимо.')">
        <button type="submit" class="btn-logout">Удалить аккаунт</button>
    </form>
</div>
</body>
</html>