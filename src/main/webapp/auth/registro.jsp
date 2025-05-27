<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Rol" %>
<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
    session.removeAttribute("success");
    session.removeAttribute("message");
%>

<div class="p-3">
    <h2 class="text-petgo mb-4 text-center"><i class="fas fa-user-plus me-2 text-petgo"></i>Registrar Usuario</h2>

    <% if (success != null) { %>
    <div class="alert <%= success ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
        <i class="fas <%= success ? "fa-check-circle" : "fa-exclamation-triangle" %> me-2"></i>
        <%= (message != null) ? message : (success ? "¡Usuario registrado exitosamente!" : "Error al registrar usuario.") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
    </div>
    <% } %>

    <form action="<%= request.getContextPath() %>/UsuarioController?route=create" method="POST" class="user-form" style="max-width: 400px; margin: 0 auto;">

        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" id="nombre" name="nombre" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="apellido" class="form-label">Apellido:</label>
            <input type="text" id="apellido" name="apellido" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Correo Electrónico:</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="telefono" class="form-label">Teléfono:</label>
            <input type="tel" id="telefono" name="telefono" class="form-control" required pattern="[0-9]{7,15}" title="Ingrese solo números (7 a 15 dígitos)">
        </div>

        <div class="mb-3">
            <label for="contrasena" class="form-label">Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" class="form-control" required>
        </div>

        <div class="mb-4">
            <label for="rol" class="form-label">Rol:</label>
            <select id="rol" name="rol" class="form-select" required>
                <option value="Cliente">🐾 Cliente</option>
                <option value="Paseador">🐕 Paseador</option>
            </select>
        </div>

        <button type="submit" class="btn btn-petgo w-100 rounded-pill">
            <i class="fas fa-user-plus me-2"></i> Registrar Usuario
        </button>

    </form>
</div>

<% if (success != null && success) { %>
<script>
    setTimeout(function() {
        var modal = bootstrap.Modal.getInstance(document.getElementById('registroModal'));
        if (modal) {
            modal.hide();
        }
        window.location.href = "<%= request.getContextPath() %>/index.jsp";
    }, 3000);
</script>
<% } %>
