<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Ticket" %>

<%
    Ticket ticket = (Ticket) request.getAttribute("ticket");
    Long idUsuario = (Long) session.getAttribute("idUsuario"); // 🔥 Capturamos el id del usuario en sesión
%>

<% if (ticket != null) { %>
    <div class="modal-header bg-success text-white">
        <h5 class="modal-title"><i class="fas fa-dog me-2"></i> Detalles del Paseo</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Cerrar"></button>
    </div>
    <div class="modal-body">
        <p><strong>Fecha:</strong> <%= ticket.getFecha() %></p>
        <p><strong>Hora:</strong> <%= ticket.getHora() %></p>
        <p><strong>Duración:</strong> <%= ticket.getDuracion() %> horas</p>

        <% if (ticket.getUsuario() != null) { %>
            <hr>
            <p><strong>Cliente:</strong> <%= ticket.getUsuario().getNombre() %> <%= ticket.getUsuario().getApellido() %></p>
            <p><strong>Teléfono:</strong> <%= ticket.getUsuario().getTelefono() %></p>
        <% } else { %>
            <p class="text-danger"><strong>Cliente:</strong> No disponible</p>
        <% } %>
    </div>
    <div class="modal-footer">
        <form action="<%= request.getContextPath() %>/PostulacionController" method="post">
            <input type="hidden" name="route" value="postular"> <!-- 🔥 Agregamos route -->
            <input type="hidden" name="ticketId" value="<%= ticket.getId() %>">
            <input type="hidden" name="usuarioId" value="<%= idUsuario %>"> <!-- 🔥 Nuevo: id del usuario -->
            <button type="submit" class="btn btn-success w-100 rounded-pill">
                <i class="fas fa-check-circle me-2"></i> Confirmar Postulación
            </button>
        </form>
    </div>
<% } else { %>
    <div class="modal-body text-center text-danger">
        <p>No se pudo cargar la información del ticket.</p>
    </div>
<% } %>
