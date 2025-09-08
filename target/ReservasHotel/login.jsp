<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="css/style.css"> <%-- AÑADE ESTA LÍNEA --%>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="container"> <%-- AÑADE ESTA CLASE --%>
        <h2>Iniciar Sesión</h2>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="login">
            Email: <input type="email" name="email" required><br/>
            Password: <input type="password" name="password" required><br/>
            <button type="submit">Entrar</button>
        </form>
        
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}"/></p> <%-- AÑADE class="error" --%>
        </c:if>
    </div>
</body>
</html>