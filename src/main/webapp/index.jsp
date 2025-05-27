<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PetGo – Pasea tu mascota con confianza</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body class="d-flex flex-column min-vh-100">

<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
%>

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

<header class="navbar navbar-expand-lg navbar-light shadow-sm bg-petgo">
    <div class="container d-flex justify-content-between align-items-center py-2">
        <a class="navbar-brand d-flex align-items-center text-white fw-bold" href="#">
            <i class="fas fa-dog fa-2x me-2"></i>PetGo
        </a>
        <div class="d-flex gap-2">
            <button type="button" class="btn btn-light rounded-pill px-4 btn-petgo" data-bs-toggle="modal" data-bs-target="#registroModal">
                <i class="fas fa-user-plus me-2"></i> Registrarse
            </button>
        </div>
    </div>
</header>

<main class="flex-grow-1 d-flex align-items-center justify-content-center text-center bg-white">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <h1 class="display-4 fw-bold text-success mb-4">
                    ¡Pasea a tu mascota con confianza!
                </h1>
                <p class="lead text-muted mb-5">
                    Conecta con paseadores certificados que cuidan de tu mejor amigo.
                </p>
                <div class="mb-5">
                    <i class="fas fa-person-walking fa-8x text-success" style="animation: bounce 2s infinite;"></i>
                </div>
                <div>
                    <button type="button" class="btn btn-success btn-lg rounded-pill px-5 py-3 btn-petgo" data-bs-toggle="modal" data-bs-target="#loginModal">
                        <i class="fas fa-sign-in-alt me-2"></i> Iniciar Sesión
                    </button>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
    <div class="d-flex justify-content-center gap-4">
        <a href="#" class="fs-4" style="color: #1877F2;"><i class="fab fa-facebook"></i></a>
        <a href="#" class="fs-4" style="color: #E1306C;"><i class="fab fa-instagram"></i></a>
        <a href="#" class="fs-4" style="color: #1DA1F2;"><i class="fab fa-twitter"></i></a>
    </div>
</footer>

<!-- Modal de Registro -->
<div class="modal fade" id="registroModal" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-petgo-compact">
        <div class="modal-content">
            <div class="modal-header bg-petgo">
                <button type="button" class="btn-close btn-close-white ms-auto" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body" id="modal-body-content"></div>
        </div>
    </div>
</div>

<!-- Modal de Login -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-petgo-compact">
        <div class="modal-content">
            <div class="modal-header bg-petgo">
                <button type="button" class="btn-close btn-close-white ms-auto" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body" id="login-modal-body-content"></div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Cargar dinámicamente el formulario de registro
    document.addEventListener('DOMContentLoaded', function () {
        var registroModal = document.getElementById('registroModal');
        registroModal.addEventListener('show.bs.modal', function () {
            fetch('${pageContext.request.contextPath}/auth/registro.jsp')
                .then(response => response.text())
                .then(html => {
                    document.getElementById('modal-body-content').innerHTML = html;
                })
                .catch(error => {
                    document.getElementById('modal-body-content').innerHTML = '<p class="text-danger">Error al cargar el formulario de registro.</p>';
                });
        });
    });

    // Cargar dinámicamente el formulario de login
    document.addEventListener('DOMContentLoaded', function () {
        var loginModal = document.getElementById('loginModal');
        loginModal.addEventListener('show.bs.modal', function () {
            fetch('${pageContext.request.contextPath}/auth/login.jsp')
                .then(response => response.text())
                .then(html => {
                    document.getElementById('login-modal-body-content').innerHTML = html;
                })
                .catch(error => {
                    document.getElementById('login-modal-body-content').innerHTML = '<p class="text-danger">Error al cargar el formulario de inicio de sesión.</p>';
                });
        });
    });
</script>

</body>
</html>