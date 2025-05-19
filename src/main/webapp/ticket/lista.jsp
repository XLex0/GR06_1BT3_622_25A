<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entities.Mascota" %>
<%@ page import="model.entities.Ticket" %>

<% List<Ticket> listaTicket = (List<Ticket>) request.getAttribute("listatickets");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Mis Tickets</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #ffffff;
            font-family: 'Segoe UI', sans-serif;
            padding: 2rem;
        }

        h1 {
            color: #66bb6a;
            font-weight: bold;
        }

        .card-mascota {
            background-color: #f8fdf6;
            border: 1px solid #d0e9d4;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            transition: transform 0.2s ease;
        }

        .card-mascota:hover {
            transform: translateY(-3px);
        }

        .card-mascota h5 {
            color: #388e3c;
            margin-bottom: 0.5rem;
        }

        .btn-editar {
            background-color: #66bb6a;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 6px;
        }

        .btn-editar:hover {
            background-color: #4caf50;
        }

        .modal-header {
            background-color: #a8e6a3;
            color: #333;
            border-bottom: none;
        }

        .modal-footer {
            border-top: none;
        }

        .btn-agregar {
            background-color: #66bb6a;
            color: white;
            border-radius: 6px;
            padding: 0.6rem 1.2rem;
            font-weight: bold;
        }

        .btn-agregar:hover {
            background-color: #4caf50;
        }

        .no-mascotas {
            margin-top: 3rem;
            text-align: center;
            color: #666;
        }

        .btn-agregar-mascota {
            background-color: #66bb6a;
            color: white;
            border-radius: 6px;
            padding: 0.8rem 2rem;
            font-weight: bold;
            display: block;
            width: 100%;
            margin-top: 2rem;
        }

        .btn-agregar-mascota:hover {
            background-color: #4caf50;
        }
    </style>
</head>

<body>
<div class="container">
    <h1 class="mb-4">Mis Tickets</h1>

    <% if (listaTicket==null || listaTicket.isEmpty()) { %>
    <div class="no-mascotas">
        <p>No tienes tickets registradas aún.</p>
        <a href="<%= contextPath %>/cliente/inicio.jsp" class="btn btn-agregar mt-3">Agegar Ticket</a>
    </div>
    <% } else { %>
    <div class="row">
        <% for (Ticket ticket : listaTicket) { %>
        <div class="col-md-4">
            <div class="card-mascota">
                <h5>
                    <%= ticket.getFecha() %>
                </h5>
                <p><strong>Hora:</strong>
                    <%= ticket.getHora() %>
                </p>
                <p><strong>Duracion:</strong>
                    <%= ticket.getDuracion() %>
                </p>
                <p><strong>Mascota:</strong>
                <ul>
                    <% for (Mascota mascota : ticket.getMascotas()) { %>
                    <li><%= mascota.getNombre() %></li>
                    <% } %>
                </ul>
                </p>
                <button class="btn-editar mt-2"
                        onclick="abrirModal('<%= ticket.getId() %>')">Editar</button>
            </div>
        </div>

        <% } %>
    </div>
    <a href="<%= contextPath %>/cliente/inicio.jsp" class="btn-agregar-mascota">Agregar Nuevo Ticket</a>
    <% } %>

</div>

<script>
    const tickets = {
        <% for (Ticket ticket : listaTicket) { %>
        "<%= ticket.getId() %>": {
            id: "<%= ticket.getId() %>",
            fecha: "<%= ticket.getFecha() %>",
            hora: "<%= ticket.getHora() %>",
            duracion: "<%= ticket.getDuracion() %>",
        },
        <% } %>
    };

    function abrirModal(id) {
        const ticket = tickets[id];
        if (ticket) {
            document.getElementById('ticketId').value = ticket.id;
            document.getElementById('fecha').value = ticket.fecha;
            document.getElementById('hora').value = ticket.hora;
            document.getElementById('duracion').value = ticket.duracion;
            const modal = new bootstrap.Modal(document.getElementById('modalEditarTicket'));
            modal.show();
        }
    }


</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>


<!-- Modal -->
<div class="modal fade" id="modalEditarTicket" tabindex="-1" aria-labelledby="modalEditarTicketLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form id="formEditarTicket" action="${pageContext.request.contextPath}/TicketController?route=update" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEditarTicketLabel">Editar Ticket</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="id" id="ticketId">
                    <div class="mb-3">
                        <label for="fecha" class="form-label">Fecha</label>
                        <input type="date" class="form-control" id="fecha" name="fecha" required>
                    </div>
                    <div class="mb-3">
                        <label for="hora" class="form-label">Hora (hh:mm)</label>
                        <input type="time" class="form-control" id="hora" name="hora" required>
                    </div>
                    <div class="mb-3">
                        <label for="duracion" class="form-label">Duración (hh:mm):</label>
                        <input
                                type="time"
                                class="form-control"
                                name="duracion"
                                id="duracion"
                                step="60"
                                min="00:30"
                                max="03:00"
                                required
                                placeholder="Ejemplo: 01:30">
                        <small id="duracionHelp" class="form-text text-muted">La duración va desde los 30 minutos hasta las 3 horas.</small>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-agregar">Actualizar</button>
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal">Cancelar</button>
                </div>

            </div>
        </form>
    </div>
</div>

</body>

</html>