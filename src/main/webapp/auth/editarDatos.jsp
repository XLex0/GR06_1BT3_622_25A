<%@ page import="model.entities.Usuario" %>
<%
  Usuario usuario = (Usuario) session.getAttribute("user");
%>

<form action="${pageContext.request.contextPath}/UsuarioController?route=datos" method="POST">
  <div class="mb-3">
    <label for="nombre" class="form-label">Nombre:</label>
    <input type="text" name="nombre" id="nombre" class="form-control" required
           value="<%= usuario != null ? usuario.getNombre() : "" %>">
  </div>
  <div class="mb-3">
    <label for="apellido" class="form-label">Apellido:</label>
    <input type="text" name="apellido" id="apellido" class="form-control" required
           value="<%= usuario != null ? usuario.getApellido() : "" %>">
  </div>
  <div class="mb-3">
    <label for="telefono" class="form-label">Teléfono:</label>
    <input type="tel" name="telefono" id="telefono" class="form-control" required pattern="\d{10}"
           value="<%= usuario != null ? usuario.getTelefono() : "" %>"
           title="Ingrese exactamente 10 dígitos">
  </div>

  <button type="submit" class="btn btn-success w-100">Guardar Cambios</button>
</form>
