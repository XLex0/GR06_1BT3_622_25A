<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entities.Postulacion" %>

<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
    String rol = (String) session.getAttribute("rol");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Postulaciones – PetGo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body class="d-flex flex-column min-vh-100">

<% if (success != null && message != null) { %>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        Swal.fire({
            icon: '<%= success ? "success" : "error" %>',
            title: '<%= success ? "¡Éxito!" : "¡Error!" %>',
            text: '<%= message %>',
            timer: 3000,
            timerProgressBar: true,
            showConfirmButton: false
        });
    });
</script>
<%
    session.removeAttribute("success");
    session.removeAttribute("message");
}
%>

<header class="navbar navbar-expand-lg navbar-light shadow-sm" style="background: linear-gradient(90deg, #4CAF50 0%, #45a049 100%);">
    <div class="container d-flex justify-content-between align-items-center py-2">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/index.jsp">
            <i class="fas fa-dog fa-2x text-white me-2"></i>
            <span class="fw-bold text-white">PetGo</span>
        </a>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-light rounded-pill px-4">Cerrar Sesión</a>
        </div>
    </div>
</header>

<main class="flex-grow-1 bg-light py-5">
    <div class="container">
        <h1 class="text-success fw-bold text-center mb-5">Mis Postulaciones</h1>

        <div class="table-responsive">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-success">
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th><c:out value="${rol == 'Cliente' ? 'Paseador' : 'Cliente'}" /></th>
                        <th>Teléfono</th>
                        <th>Fecha del Paseo</th>
                        <th>Hora</th>
                        <th>Duración</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="postulacion" items="${postulaciones}">
                        <tr>
                            <td>${postulacion.id}</td>
                            <td>${postulacion.fecha}</td>

                            <c:choose>
                                <c:when test="${rol == 'Cliente'}">
                                    <td>${postulacion.usuario.nombre} ${postulacion.usuario.apellido}</td>
                                    <td>${postulacion.usuario.telefono}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${postulacion.ticket.usuario.nombre} ${postulacion.ticket.usuario.apellido}</td>
                                    <td>${postulacion.ticket.usuario.telefono}</td>
                                </c:otherwise>
                            </c:choose>

                            <td>${postulacion.ticket.fecha}</td>
                            <td>${postulacion.ticket.hora}</td>
                            <td>${postulacion.ticket.duracion} horas</td>

                            <td>
                                <c:choose>
                                    <c:when test="${postulacion.aprobado == true}">Aceptado</c:when>
                                    <c:when test="${postulacion.aprobado == false}">Rechazado</c:when>
                                    <c:otherwise>Pendiente</c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:if test="${rol == 'Cliente' && postulacion.aprobado == null}">
                                    <button class="btn btn-success btn-sm me-1"
                                            onclick="confirmarAccion(${postulacion.id}, true)">
                                        Aceptar
                                    </button>
                                    <button class="btn btn-danger btn-sm"
                                            onclick="confirmarAccion(${postulacion.id}, false)">
                                        Rechazar
                                    </button>
                                </c:if>
                            </td>
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

<script>
    function confirmarAccion(postulacionId, aceptar) {
        Swal.fire({
            title: aceptar ? "¿Aceptar esta postulación?" : "¿Rechazar esta postulación?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: aceptar ? "Aceptar" : "Rechazar",
            cancelButtonText: "Cancelar",
            confirmButtonColor: aceptar ? "#28a745" : "#dc3545"
        }).then((result) => {
            if (result.isConfirmed) {
                const form = document.createElement("form");
                form.method = "POST";
                form.action = `${'${pageContext.request.contextPath}'}/PostulacionController?route=${aceptar ? 'aceptarPostulacion' : 'rechazarPostulacion'}`;

                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "postulacionId";
                input.value = postulacionId;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
