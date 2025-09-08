<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Mis Reservas</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/table.css"> <%-- CSS específico para tablas --%>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="container">
        <h2>Mis Reservas</h2>

        <c:choose>
            <c:when test="${not empty listaReservas}">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Hotel</th>
                            <th>Habitación</th>
                            <th>Fecha de Entrada</th>
                            <th>Fecha de Salida</th>
                            <th>Costo Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reserva" items="${listaReservas}">
                            <tr>
                                <td><c:out value="${reserva.habitacion.hotel.nombre}"/></td>
                                <td><c:out value="${reserva.habitacion.tipo}"/></td>
                                <td>
                                    <fmt:formatDate value="${reserva.fechaEntrada}" pattern="dd 'de' MMMM, yyyy"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${reserva.fechaSalida}" pattern="dd 'de' MMMM, yyyy"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${reserva.costoTotal}" type="currency" currencySymbol="$" maxFractionDigits="0"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="info-message">Aún no has realizado ninguna reserva.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>