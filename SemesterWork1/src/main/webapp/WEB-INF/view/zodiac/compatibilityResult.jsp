<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат совместимости</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>

<body>
<div class="container">
    <jsp:include page="/WEB-INF/view/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>

    <div class="header">
        <h1>Результат совместимости</h1>
    </div>

    <c:if test="${not empty result}">

        <div class="advice-info">
            <h3>Соотношение совместимости</h3>
            <p>${result.percentInfo}</p>
        </div>

        <div class="advice-info">
            <h3>Любовь</h3>
            <p>${result.loveInfo}</p>
        </div>

        <div class="advice-info">
            <h3>Семья и брак</h3>
            <p>${result.familyInfo}</p>
        </div>

        <div class="advice-info">
            <h3>Дружба</h3>
            <p>${result.friendsInfo}</p>
        </div>

        <div class="advice-info">
            <h3>Работа и бизнес</h3>
            <p>${result.workInfo}</p>
        </div>
    </c:if>

    <c:if test="${empty result}">
        <div class="error-message">
            <h3>Ошибка</h3>
            <p>${error}</p>
        </div>
    </c:if>
</div>

</body>
</html>


