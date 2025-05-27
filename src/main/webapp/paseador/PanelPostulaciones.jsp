<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entities.Postulacion" %>

<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Postulaciones – PetGo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= contextPath %>/css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body class="d-flex flex-column min-vh-100">

<%@ include file="../includes/headerPostulador.jsp" %>

<main class="flex-grow-1 bg-light py-5">
    <div class="container">
        <h1 class="text-success fw-bold text-center mb-5">Mis Postulaciones</h1>

        <div class="table-responsive">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-success">
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Cliente</th>
                    <th>Teléfono</th>
                    <th>Fecha del Paseo</th>
                    <th>Hora</th>
                    <th>Duración</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="postulacion" items="${postulaciones}">
                    <tr>
                        <td>${postulacion.id}</td>
                        <td>${postulacion.fecha}</td>
                        <td>${postulacion.ticket.usuario.nombre} ${postulacion.ticket.usuario.apellido}</td>
                        <td>${postulacion.ticket.usuario.telefono}</td>
                        <td>${postulacion.ticket.fecha}</td>
                        <td>${postulacion.ticket.hora}</td>
                        <td>${postulacion.ticket.duracion} horas</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>

<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
