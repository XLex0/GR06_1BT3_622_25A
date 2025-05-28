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

        <div class="row g-4">
            <c:forEach var="postulacion" items="${postulaciones}">
                <div class="col-md-6">
                    <div class="card shadow-sm border-0 rounded-4 h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <div>
                                    <h5 class="mb-1 text-success">
                                        <c:choose>
                                            <c:when test="${rol == 'Cliente'}">
                                                ${postulacion.usuario.nombre} ${postulacion.usuario.apellido}
                                                <button class="btn btn-sm btn-link text-info p-0 ms-1"
                                                        onclick="verPerfil(${postulacion.usuario.id})"
                                                        title="Ver perfil del paseador">
                                                    <i class="fas fa-circle-info fa-lg"></i>
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                ${postulacion.ticket.usuario.nombre} ${postulacion.ticket.usuario.apellido}
                                            </c:otherwise>
                                        </c:choose>
                                    </h5>
                                    <p class="mb-1 text-muted">
                                        <i class="fas fa-phone me-1"></i>
                                        <c:choose>
                                            <c:when test="${rol == 'Cliente'}">${postulacion.usuario.telefono}</c:when>
                                            <c:otherwise>${postulacion.ticket.usuario.telefono}</c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p class="mb-1"><strong>ID Ticket:</strong> ${postulacion.ticket.id}</p>
                                    <p class="mb-1"><strong>Fecha paseo:</strong> ${postulacion.ticket.fecha}</p>
                                    <p class="mb-1"><strong>Hora:</strong> ${postulacion.ticket.hora}</p>
                                    <p class="mb-3"><strong>Duración:</strong> ${postulacion.ticket.duracion} horas</p>
                                </div>

                                <span class="badge rounded-pill fs-6
                                    <c:choose>
                                        <c:when test="${not empty postulacion.aprobado and postulacion.aprobado == true}">bg-success</c:when>
                                        <c:when test="${not empty postulacion.aprobado and postulacion.aprobado == false}">bg-danger</c:when>
                                        <c:otherwise>bg-warning text-dark</c:otherwise>
                                    </c:choose>">
                                    <c:choose>
                                        <c:when test="${not empty postulacion.aprobado and postulacion.aprobado == true}">Aceptado</c:when>
                                        <c:when test="${not empty postulacion.aprobado and postulacion.aprobado == false}">Rechazado</c:when>
                                        <c:otherwise>Pendiente</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>

                            <c:if test="${rol == 'Cliente' && empty postulacion.aprobado}">
                                <div class="d-flex gap-2">
                                    <button class="btn btn-success w-50"
                                            onclick="confirmarAccion(${postulacion.id}, true)">
                                        <i class="fas fa-check me-1"></i>Aceptar
                                    </button>
                                    <button class="btn btn-danger w-50"
                                            onclick="confirmarAccion(${postulacion.id}, false)">
                                        <i class="fas fa-times me-1"></i>Rechazar
                                    </button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>

<!-- ✅ Modal para ver el perfil -->
<div class="modal fade" id="modalPerfil" tabindex="-1" aria-labelledby="modalPerfilLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable modal-lg">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title" id="modalPerfilLabel">Perfil del Paseador</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body" id="perfilContenido">
        <div class="text-center text-muted">Cargando perfil...</div>
      </div>
    </div>
  </div>
</div>

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
                form.action = '${pageContext.request.contextPath}/PostulacionController?route=actualizarEstadoPostulacion';

                const idInput = document.createElement("input");
                idInput.type = "hidden";
                idInput.name = "postulacionId";
                idInput.value = postulacionId;

                const accionInput = document.createElement("input");
                accionInput.type = "hidden";
                accionInput.name = "accion";
                accionInput.value = aceptar ? "aceptar" : "rechazar";

                form.appendChild(idInput);
                form.appendChild(accionInput);
                document.body.appendChild(form);
                form.submit();
            }
        });
    }

    function verPerfil(idUsuario) {
        const modal = new bootstrap.Modal(document.getElementById('modalPerfil'));
        document.getElementById('perfilContenido').innerHTML = '<div class="text-center text-muted">Cargando perfil...</div>';

        fetch('${pageContext.request.contextPath}/PaseadorController?route=verPerfilCliente&id=' + idUsuario)
            .then(response => response.text())
            .then(html => {
                document.getElementById('perfilContenido').innerHTML = html;
            })
            .catch(error => {
                console.error('Error al cargar el perfil:', error);
                document.getElementById('perfilContenido').innerHTML = '<div class="text-danger">Error al cargar el perfil.</div>';
            });

        modal.show();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
