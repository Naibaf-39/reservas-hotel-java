<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <nav>
        <a href="index.jsp">Inicio</a>
        
        <c:if test="${empty sessionScope.usuarioLogueado}">
            <a href="controller?action=mostrarLogin">Login</a>
            <a href="controller?action=mostrarRegistro">Registro</a>
        </c:if>

        <c:if test="${not empty sessionScope.usuarioLogueado}">
            <span>Hola, <c:out value="${sessionScope.usuarioLogueado.nombre}"/>!</span>
            <a href="controller?action=verMisReservas">Mis Reservas</a>
            <a href="controller?action=logout">Logout</a>
        </c:if>
    </nav>
</header>