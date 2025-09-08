<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Resultados de Búsqueda</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/table.css"> <%-- CSS específico para tablas --%>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="container">
        <h2>Hoteles encontrados en <c:out value="${ciudadBuscada}"/></h2>

        <c:choose>
            <c:when test="${not empty hoteles}">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Nombre del Hotel</th>
                            <th>Dirección</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="hotel" items="${hoteles}">
                            <tr>
                                <td><c:out value="${hotel.nombre}"/></td>
                                <td><c:out value="${hotel.direccion}"/></td>
                                <td>
                                    <a href="controller?action=verDetallesHotel&id=${hotel.id}" class="btn-table">Ver Habitaciones</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="info-message">No se encontraron hoteles en la ciudad especificada.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>