<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String route = request.getParameter("route");
    String uri = request.getRequestURI();
%>

<header class="petgo-header shadow-sm fade-in-up border-bottom"
        style="background: linear-gradient(90deg, #4CAF50 0%, #45a049 100%); z-index: 1030;">
    <div class="container d-flex flex-wrap justify-content-between align-items-center py-3">

        <!-- Izquierda: Logo -->
        <div class="d-flex align-items-center gap-2 animate-logo">
            <i class="fas fa-dog fa-2x text-white fa-beat"></i>
            <span class="fw-bold fs-3 text-white">PetGo</span>
        </div>

        <!-- Centro: Menú de navegación -->
        <nav class="d-flex align-items-center gap-3">
            <a class="nav-link text-white px-3 py-2 rounded-pill hover-glow
               <%= (uri.contains("TicketController") && "list".equals(route)) ? "active" : "" %>"
               href="<%= contextPath %>/TicketController?route=list" aria-label="Tickets">
               <i class="fas fa-ticket-alt me-1 fa-fade"></i> Tickets
            </a>

            <a class="nav-link text-white px-3 py-2 rounded-pill hover-glow
               <%= (uri.contains("PostulacionController") && "listarPostulaciones".equals(route)) ? "active" : "" %>"
               href="<%= contextPath %>/PostulacionController?route=listarPostulaciones" aria-label="Postulaciones">
               <i class="fas fa-file-alt me-1 fa-fade"></i> Postulaciones
            </a>

            <a class="nav-link text-white px-3 py-2 rounded-pill hover-glow
               <%= (uri.contains("PaseadorController") && "verPerfil".equals(route)) || uri.contains("perfilPaseador.jsp") ? "active" : "" %>"
               href="<%= contextPath %>/PaseadorController?route=verPerfil" aria-label="Mi Perfil">
               <i class="fas fa-user me-1 fa-fade"></i> Mi Perfil
            </a>
        </nav>

        <!-- Derecha: Acciones -->
        <div class="d-flex gap-2 align-items-center">
            <a href="<%= contextPath %>/cliente/inicio.jsp"
               class="btn btn-outline-light rounded-pill hover-pop-glow"
               aria-label="Inicio">
                <i class="fas fa-house-user me-1"></i> Inicio
            </a>

            <a href="<%= contextPath %>/LoginController?route=logout"
               class="btn btn-outline-light rounded-pill hover-pop-logout"
               aria-label="Cerrar sesión">
                <i class="fas fa-sign-out-alt me-1"></i> Cerrar Sesión
            </a>
        </div>

    </div>
</header>
