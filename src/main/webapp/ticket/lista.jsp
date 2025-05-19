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
</body>

</html>