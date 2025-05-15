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
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-light">Cerrar Sesión</a>
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
                                    <a href="${pageContext.request.contextPath}/ticket/registro.jsp" class="btn btn-outline-success rounded-pill mt-2">Crear</a>
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
                                    <h5 class="card-title">Aceptar Postulaciones</h5>
                                    <p class="card-text text-muted">Revisa y acepta postulaciones a tus tickets.</p>
                                    <a href="${pageContext.request.contextPath}/PostulacionController?route=listarPostulacionesCliente" class="btn btn-outline-success rounded-pill mt-2">Ver</a>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
