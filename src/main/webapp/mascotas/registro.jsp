<%--
  Created by IntelliJ IDEA.
  User: TOMMY
  Date: 25/4/2025
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="background-color: #e3e3e3; padding: 10px;">
    <a href="${pageContext.request.contextPath}/paseador/registrarTicket.jsp">Registrar Ticket</a> |
    <a href="${pageContext.request.contextPath}/ListarTicketsDisponiblesController">Postular a Ticket</a> |
    <a href="${pageContext.request.contextPath}/GestionarTicketController?accion=listar">Listar Mis Tickets</a> |
    <a href="${pageContext.request.contextPath}/CerrarSesionController">Cerrar Sesión</a>
</div>
<hr>


<h1>REGISTRO DE MASCOTAS</h1>

<a href="../inicio.jsp">Inicio</a>

</body>
</html>
