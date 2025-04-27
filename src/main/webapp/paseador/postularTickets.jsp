<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="model.entities.Ticket" %>

<%
    List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Postularse a Tickets</title>
</head>
<body>

<h1>Tickets Disponibles</h1>

<%-- Mostrar mensajes si existen --%>
<c:if test="${param.mensaje == 'PostulacionExitosa'}">
    <p style="color: green;">¡Postulación enviada exitosamente!</p>
</c:if>
<c:if test="${param.mensaje == 'ErrorPostulacion'}">
    <p style="color: red;">Error al postularse, inténtalo de nuevo.</p>
</c:if>

<table border="1">
    <thead>
        <tr>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Duración</th>
            <th>Acción</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <td>${ticket.fecha}</td>
                <td>${ticket.hora}</td>
                <td>${ticket.duracion}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/GestionarPostulacionController" method="post">
                        <input type="hidden" name="ticketId" value="${ticket.id}" />
                        <button type="submit">Postularme</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
