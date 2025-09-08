<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Bienvenido a Reservas Hotel</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    
    <div class="container">
        <%-- Usamos c:choose para mostrar un contenido u otro según la sesión --%>
        <c:choose>
            <%-- CUANDO EL USUARIO HA INICIADO SESIÓN --%>
            <c:when test="${not empty sessionScope.usuarioLogueado}">
                <h1>Encuentra tu próximo destino</h1>
                <p>Busca hoteles en la ciudad que prefieras, <c:out value="${sessionScope.usuarioLogueado.nombre}"/>.</p>
                
                <form action="controller" method="post">
                    <input type="hidden" name="action" value="buscarHoteles">
                    <input type="text" name="ciudad" placeholder="Ingresa una ciudad, ej: Santiago" required>
                    <button type="submit">Buscar Hoteles</button>
                </form>
            </c:when>
            
            <%-- CUANDO NADIE HA INICIADO SESIÓN (INVITADO) --%>
            <c:otherwise>
                <h1>Bienvenido a nuestro portal de reservas</h1>
                <p>Encuentra los mejores hoteles al mejor precio.</p>
                <p><strong><a href="controller?action=mostrarLogin">Inicia sesión</a> o <a href="controller?action=mostrarRegistro">regístrate</a> para comenzar a buscar y reservar.</strong></p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>