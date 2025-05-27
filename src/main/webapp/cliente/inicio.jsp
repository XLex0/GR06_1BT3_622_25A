<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PetGo – Inicio</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="d-flex flex-column min-vh-100">

<nav class="navbar-custom">
    <div class="container d-flex justify-content-between align-items-center">
        <a class="navbar-brand d-flex align-items-center text-white" href="#">
            <i class="fas fa-dog fa-2x me-2"></i>
            <span class="fw-bold">PetGo</span>
        </a>
        <div class="d-flex gap-2 align-items-center">
            <button type="button" class="btn btn-outline-light rounded-circle" data-bs-toggle="modal" data-bs-target="#perfilModal" title="Editar Perfil">
                <i class="fas fa-user"></i>
            </button>
            <a href="${pageContext.request.contextPath}/LoginController?route=logout" class="btn btn-outline-light">Cerrar Sesión</a>

        </div>
    </div>
</nav>


<main class="flex-grow-1 d-flex align-items-center justify-content-center text-center bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h1 class="display-5 fw-bold text-success mb-4 fade-in-up delay-1">
                    Bienvenido a tu espacio PetGo
                </h1>
                <p class="lead text-muted mb-5 fade-in-up delay-2">
                    Aquí podrás gestionar tus mascotas, crear tickets de paseo y postularte fácilmente.
                </p>

                <div class="row justify-content-center g-4 fade-in-up delay-3">

                    <!-- Tarjetas para CLIENTE -->
                    <c:if test="${sessionScope.rol == 'Cliente'}">
                        <div class="col-md-3">
                            <div class="card h-100 shadow-sm border-0 rounded-4">
                                <div class="card-body text-center">
                                    <i class="fas fa-paw fa-3x text-success mb-3"></i>
                                    <h5 class="card-title">Registrar Mascota</h5>
                                    <p class="card-text text-muted">Agrega una nueva mascota a tu perfil.</p>
                                    <a href="${pageContext.request.contextPath}/mascotas/registro.jsp" class="btn btn-outline-success rounded-pill mt-2">Registrar</a>
                                    <a href="${pageContext.request.contextPath}/mascotas?route=list" class="btn btn-outline-success rounded-pill mt-2">Mascotas</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="card h-100 shadow-sm border-0 rounded-4">
                                <div class="card-body text-center">
                                    <i class="fas fa-ticket-alt fa-3x text-success mb-3"></i>
                                    <h5 class="card-title">Crear Ticket</h5>
                                    <p class="card-text text-muted">Solicita paseos para tu mascota.</p>
                                    <a href="${pageContext.request.contextPath}/mascotas?route=listarMascotaTicket" class="btn btn-outline-success rounded-pill mt-2">Crear</a>
                                    <a href="${pageContext.request.contextPath}/TicketController?route=listTicketsByUser" class="btn btn-outline-success rounded-pill mt-2">Mis tickets</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card h-100 shadow-sm border-0 rounded-4">
                                <div class="card-body text-center">
                                    <i class="fas fa-user-check fa-3x text-success mb-3"></i>
                                    <h5 class="card-title">Aceptar Postulaciones</h5>
                                    <p class="card-text text-muted">Revisa y acepta postulaciones a tus tickets.</p>
                                    <a href="${pageContext.request.contextPath}/PostulacionController?route=listarPostulacionesCliente" class="btn btn-outline-success rounded-pill mt-2">Ver</a>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <!-- Tarjetas para PASEADOR -->
                    <c:if test="${sessionScope.rol == 'Paseador'}">
                        <div class="col-md-3">
                            <div class="card h-100 shadow-sm border-0 rounded-4">
                                <div class="card-body text-center">
                                    <i class="fas fa-hand-paper fa-3x text-success mb-3"></i>
                                    <h5 class="card-title">Postular Tickets</h5>
                                    <p class="card-text text-muted">¿Paseador? Postúlate a tickets abiertos.</p>
                                    <a href="${pageContext.request.contextPath}/TicketController?route=list" class="btn btn-outline-success rounded-pill mt-2">Postular</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="card h-100 shadow-sm border-0 rounded-4">
                                <div class="card-body text-center">
                                    <i class="fas fa-user-check fa-3x text-success mb-3"></i>
                                    <h5 class="card-title">Ver Postulaciones</h5>
                                    <p class="card-text text-muted">Revisar estado de las postulaciones.</p>
                                    <a href="${pageContext.request.contextPath}/PostulacionController?route=listarPostulaciones" class="btn btn-outline-success rounded-pill mt-2">Ver</a>
                                </div>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
</main>

<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>

<!-- Modal de Perfil -->
<div class="modal fade" id="perfilModal" tabindex="-1" aria-labelledby="perfilModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title" id="perfilModalLabel">Editar Perfil</h5>
                <button type="button" class="btn-close btn-close-white ms-auto" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
                <div class="d-flex justify-content-center mb-4 gap-3">
                    <button id="btnEditarDatos" class="btn btn-outline-success">
                        <i class="fas fa-user-edit me-2"></i>Actualizar Datos
                    </button>
                    <button id="btnEditarCredenciales" class="btn btn-outline-primary">
                        <i class="fas fa-key me-2"></i>Cambiar Credenciales
                    </button>
                </div>
                <div id="perfil-form-content" class="px-3">
                    <!-- Aquí se cargará el formulario seleccionado -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal de Mensaje -->
<div class="modal fade" id="mensajeModal" tabindex="-1" aria-labelledby="mensajeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header ${sessionScope.successM ? 'bg-success' : 'bg-danger'} text-white">
                <h5 class="modal-title" id="mensajeModalLabel">
                    ${sessionScope.successM ? 'Éxito' : 'Error'}
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
                <p>${sessionScope.messageM}</p>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty sessionScope.messageM}">
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const modal = new bootstrap.Modal(document.getElementById('mensajeModal'));
            modal.show();
        });
    </script>
</c:if>

<!-- Script para cargar formularios dinámicamente -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const contenedor = document.getElementById('perfil-form-content');

        document.getElementById('btnEditarDatos').addEventListener('click', function () {
            fetch('${pageContext.request.contextPath}/auth/editarDatos.jsp')
                .then(res => res.text())
                .then(html => contenedor.innerHTML = html)
                .catch(() => contenedor.innerHTML = '<p class="text-danger">Error al cargar el formulario de datos.</p>');
        });

        document.getElementById('btnEditarCredenciales').addEventListener('click', function () {
            fetch('${pageContext.request.contextPath}/auth/editarCredenciales.jsp')
                .then(res => res.text())
                .then(html => contenedor.innerHTML = html)
                .catch(() => contenedor.innerHTML = '<p class="text-danger">Error al cargar el formulario de credenciales.</p>');
        });
    });
</script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>
