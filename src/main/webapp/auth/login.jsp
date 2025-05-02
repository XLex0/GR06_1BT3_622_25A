<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Rol" %>
<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
    session.removeAttribute("success");
    session.removeAttribute("message");
%>

<div class="p-3">
    <h2 class="text-primary mb-4 text-center">Iniciar Sesión</h2>

    <% if (success != null) { %>
    <div class="alert <%= success ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
        <i class="fas <%= success ? "fa-check-circle" : "fa-exclamation-triangle" %> me-2"></i>
        <%= (message != null) ? message : (success ? "¡Inicio de sesión exitoso!" : "Error al iniciar sesión.") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
    </div>
    <% } %>

    <form action="<%= request.getContextPath() %>/LoginController?route=login" method="POST" class="login-form" style="max-width: 400px; margin: 0 auto;">

        <div class="mb-3">
            <label for="email" class="form-label">Correo Electrónico:</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Contraseña:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>

        <div class="mb-4">
            <label for="rol" class="form-label">Rol:</label>
            <select id="rol" name="rol" class="form-select" required>
                <option value="Cliente">Cliente</option>
                <option value="Paseador">Paseador</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary w-100 rounded-pill">
            <i class="fas fa-sign-in-alt me-2"></i> Iniciar Sesión
        </button>

    </form>
</div>

<% if (success != null && success) { %>
<script>
    setTimeout(function() {
        window.location.href = "<%= request.getContextPath() %>/index.jsp";
    }, 2000);
</script>
<% } %>
