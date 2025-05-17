<%@ page import="model.entities.Usuario" %>
<%
  Usuario usuario = (Usuario) session.getAttribute("user");
%>

<form action="${pageContext.request.contextPath}/UsuarioController?route=credenciales" method="POST">
  <div class="mb-3">
    <label for="email" class="form-label">Correo Actual:</label>
    <input type="email" name="email" id="email" class="form-control" required
           value="<%= usuario != null ? usuario.getEmail() : "" %>">
  </div>
  <div class="mb-3">
    <label for="password" class="form-label">Nueva Clave:</label>
    <input type="password" name="password" id="password" class="form-control" required>
  </div>
  <div class="mb-3">
    <label for="lastPassword" class="form-label">Clave Actual:</label>
    <input type="password" name="lastPassword" id="lastPassword" class="form-control" required>
  </div>
  <button type="submit" class="btn btn-primary w-100">Actualizar Credenciales</button>
</form>
