<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ваш гороскоп</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/view/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>

    <div class="header">
        <h1>Ваш гороскоп</h1>
    </div>


    <div class="sign-info">
        <span class="sign-name">${zodiac.signName}</span>
        <img src="${zodiac.imagePath}" alt="${zodiac.signName} символ" class="zodiac-sign-image">
    </div>
    <h2>Характеристики:</h2>

    <h3>Сильная сторона:</h3>
    <div class="advice-info">
        ${zodiac.strongSide}
    </div>

    <h3>Слабая сторона:</h3>
    <div class="advice-info">
        ${zodiac.weakness}
    </div>

    <h2>Информация о знаке зодиака:</h2>
    <div class="advice-info">
        ${zodiac.info}
    </div>
</div>

</body>
</html>



