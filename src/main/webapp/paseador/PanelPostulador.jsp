<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entities.Ticket" %>

<%
    List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PetGo – Postular a Tickets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/css/styles.css">
</head>

<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<%@ include file="../includes/headerPostulador.jsp" %>
<!-- Main Content -->
<main class="flex-grow-1 bg-light py-5">
    <div class="container">

        <h1 class="text-success fw-bold text-center mb-5">Tickets Disponibles</h1>

        <div class="row g-4 justify-content-center">
            <c:forEach var="ticket" items="${tickets}">
                <div class="col-md-6 col-lg-4">
                    <div class="card shadow-sm h-100 border-0 rounded-4">
                        <div class="card-body text-center d-flex flex-column justify-content-between">
                            <div>
                                <h5 class="card-title text-success fw-bold mb-3">
                                    <i class="fas fa-calendar-alt me-2"></i> ${ticket.fecha}
                                </h5>
                                <p class="card-text text-muted">
                                    <i class="fas fa-clock me-1"></i> Hora: ${ticket.hora}<br>
                                    <i class="fas fa-hourglass-half me-1"></i> Duración: ${ticket.duracion} horas
                                </p>
                            </div>

                            <!-- Botón Ver Detalles -->
                            <button type="button"
                                class="btn btn-outline-success w-100 rounded-pill mt-3"
                                data-ticket-id="${ticket.id}"
                                data-bs-toggle="modal"
                                data-bs-target="#PanelTicketModal">
                                <i class="fas fa-info-circle me-2"></i> Ver Detalles
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</main>

<!-- Footer -->
<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>

<!-- Modal -->
<div class="modal fade" id="PanelTicketModal" tabindex="-1" aria-labelledby="PanelTicketModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content rounded-4" id="modalTicketContent">
      <!-- Aquí se cargará dinámicamente -->
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- Script carga dinámica -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    const PanelTicketModal = document.getElementById('PanelTicketModal');
    const contextPath = '<%= request.getContextPath() %>';

    PanelTicketModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const ticketId = button.getAttribute('data-ticket-id');
        const modalContent = document.getElementById('modalTicketContent');

        modalContent.innerHTML = `
            <div class="modal-body d-flex justify-content-center align-items-center" style="min-height: 200px;">
                <div class="spinner-border text-success" role="status">
                    <span class="visually-hidden">Cargando...</span>
                </div>
            </div>
        `;

        fetch(contextPath + `/TicketController?route=verDetalles&ticketId=` + ticketId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('No se pudo cargar el detalle del ticket');
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
            })
            .catch(error => {
                console.error('Error cargando el panel:', error);
                modalContent.innerHTML = `
                    <div class="modal-body text-center text-danger">
                        <i class="fas fa-exclamation-triangle fa-2x mb-3"></i>
                        <p>No se pudieron cargar los detalles del ticket. Intenta de nuevo.</p>
                    </div>
                `;
            });
    });
});
</script>

</body>
</html>
