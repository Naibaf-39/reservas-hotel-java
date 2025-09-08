<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="css/style.css"> <%-- AÑADE ESTA LÍNEA --%>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="container"> <%-- AÑADE ESTA CLASE --%>
        <h2>Regístrate</h2>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="registrar">
            Nombre: <input type="text" name="nombre" required><br/>
            Email: <input type="email" name="email" required><br/>
            Password: <input type="password" name="password" required><br/>
            <button type="submit">Registrar</button>
        </form>
        
        <c:if test="${not empty error}">
            <p class="error"><c:out value="${error}"/></p> <%-- AÑADE class="error" --%>
        </c:if>
    </div>
</body>
</html>