<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Ticket" %>

<%
    Ticket ticket = (Ticket) request.getAttribute("ticket");
%>

<% if (ticket != null) { %>
    <form action="<%= request.getContextPath() %>/PostulacionController" method="post">
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

            <hr>
            <div class="mb-3">
                <label for="paseadorId" class="form-label">ID Paseador:</label>
                <input type="number" class="form-control" id="paseadorId" name="paseadorId" required>
            </div>

            <input type="hidden" name="route" value="postular">
            <input type="hidden" name="ticketId" value="<%= ticket.getId() %>">
        </div>

        <div class="modal-footer">
            <button type="submit" class="btn btn-success w-100 rounded-pill">
                <i class="fas fa-check-circle me-2"></i> Confirmar Postulación
            </button>
        </div>
    </form>
<% } else { %>
    <div class="modal-body text-center text-danger">
        <p>No se pudo cargar la información del ticket.</p>
    </div>
<% } %>
