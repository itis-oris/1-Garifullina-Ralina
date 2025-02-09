<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка совместимости</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script src="<c:url value='/js/scripts.js' />"></script>

</head>
<body>
<div class="container-profile">
    <jsp:include page="/WEB-INF/view/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>
        <div class="header">
            <h1>Проверка совместимости</h1>
        </div>

        <form action="<c:url value="compatibilityCheck"/>" method="POST">
            <div class="profile-info">
                <label for="manBirthDate">День рождения мужчины:</label><br>
                <input type="date" id="manBirthDate" name="manBirthDate"><br><br>
            </div>

            <div class="profile-info">
                <label for="womanBirthDate">День рождения женщины:</label><br>
                <input type="date" id="womanBirthDate" name="womanBirthDate"><br><br>
            </div>


            <button type="submit" class="orange-button">Проверить</button>
        </form>

    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>
</div>
</body>
</html>
