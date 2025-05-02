<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Rol" %>
<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
    session.removeAttribute("success");
    session.removeAttribute("message");
%>

<div class="container d-flex flex-column justify-content-center align-items-center min-vh-100">

    <div class="text-center mb-4">
        <i class="fas fa-paw fa-3x text-primary"></i>
        <h1 class="mt-2 text-primary fw-bold">Bienvenido a PetGo</h1>
        <p class="text-muted">🐾 Pasea tu mascota con confianza 🐾</p>
    </div>

    <% if (success != null) { %>
    <div class="alert <%= success ? "alert-success" : "alert-danger" %> alert-dismissible fade show w-100" role="alert" style="max-width: 400px;">
        <i class="fas <%= success ? "fa-check-circle" : "fa-exclamation-triangle" %> me-2"></i>
        <%= (message != null) ? message : (success ? "¡Inicio de sesión exitoso!" : "Error al iniciar sesión.") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
    </div>
    <% } %>

    <div class="card shadow p-4" style="max-width: 400px; width: 100%; border-radius: 1rem;">
        <h2 class="text-center text-primary mb-4"><i class="fas fa-sign-in-alt me-2"></i>Iniciar Sesión</h2>

        <form action="<%= request.getContextPath() %>/LoginController?route=login" method="POST" class="login-form">

            <div class="mb-3">
                <label for="email" class="form-label">Correo Electrónico:</label>
                <input type="email" id="email" name="email" class="form-control rounded-pill" placeholder="ejemplo@correo.com" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Contraseña:</label>
                <input type="password" id="password" name="password" class="form-control rounded-pill" placeholder="••••••••" required>
            </div>

            <div class="mb-4">
                <label for="rol" class="form-label">Rol:</label>
                <select id="rol" name="rol" class="form-select rounded-pill" required>
                    <option value="Cliente">🐾 Cliente</option>
                    <option value="Paseador">🐕 Paseador</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary w-100 rounded-pill">
                <i class="fas fa-paw me-2"></i> Iniciar Sesión
            </button>

        </form>
    </div>

</div>

<% if (success != null && success) { %>
<script>
    setTimeout(function() {
        window.location.href = "<%= request.getContextPath() %>/index.jsp";
    }, 2000);
</script>
<% } %>
