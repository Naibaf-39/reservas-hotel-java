<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Detalles del Hotel</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="container">
        <h1><c:out value="${hotel.nombre}"/></h1>
        <p><c:out value="${hotel.direccion}"/>, <c:out value="${hotel.ciudad}"/></p>

        <hr>
        <h2>Habitaciones Disponibles</h2>

        <c:choose>
            <c:when test="${not empty hotel.habitaciones}">
                <c:forEach var="hab" items="${hotel.habitaciones}">
                    <div class="list-item">
                        <h3><c:out value="${hab.tipo}"/></h3>
                        <p>Capacidad: <c:out value="${hab.capacidad}"/> personas</p>
                        <p><strong>Precio por noche: <fmt:formatNumber value="${hab.precioPorNoche}" type="currency" currencySymbol="$" maxFractionDigits="0"/></strong></p>
                        
                        <form action="controller" method="post" class="inline-form">
                            <input type="hidden" name="action" value="procesarReserva">
                            <input type="hidden" name="habitacionId" value="${hab.id}">
                            
                            <div class="form-group">
                                <label for="fechaEntrada-${hab.id}">Fecha de Entrada:</label>
                                <input type="date" id="fechaEntrada-${hab.id}" name="fechaEntrada" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="fechaSalida-${hab.id}">Fecha de Salida:</label>
                                <input type="date" id="fechaSalida-${hab.id}" name="fechaSalida" required>
                            </div>
                            
                            <button type="submit">Reservar Ahora</button>
                        </form>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                 <p class="info-message">No hay habitaciones registradas para este hotel.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>