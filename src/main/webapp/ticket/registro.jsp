<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entities.Mascota" %>

<% List<Mascota> listaMascotas = (List<Mascota>) request.getAttribute("mascotas");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Ticket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f5f9f6;
            font-family: 'Segoe UI', sans-serif;
            padding: 2rem;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background-color: #fff;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
        }

        h1 {
            color: #2e7d32;
            font-weight: 600;
            margin-bottom: 1.5rem;
            text-align: center;
        }

        .form-group label {
            font-weight: 500;
        }

        .form-control {
            border-radius: 8px;
        }

        .checkbox-container {
            margin-top: 1.5rem;
            padding: 1rem;
            background-color: #f0f7f4;
            border-radius: 10px;
            border: 1px solid #c8e6c9;
        }

        .checkbox-container label {
            margin-bottom: 0.5rem;
            display: block;
            font-weight: 500;
        }

        .form-check {
            margin-left: 1rem;
        }

        .btn-submit {
            margin-top: 1.5rem;
            background-color: #4caf50;
            border: none;
            color: white;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-submit:hover {
            background-color: #43a047;
        }

        .no-tickets {
            text-align: center;
            padding: 2rem;
            color: #888;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Crear Ticket</h1>

    <% if (listaMascotas == null || listaMascotas.isEmpty()) { %>
    <div class="no-tickets">
        <p>No tienes mascotas registradas aún.</p>
    </div>
    <% } else { %>
    <form action="<%= contextPath %>/TicketController?route=saveNew" method="post">
        <div class="form-group mb-3">
            <label for="fecha">Fecha:</label>
            <input type="date" class="form-control" name="fecha" id="fecha" required>
        </div>

        <div class="form-group mb-3">
            <label for="hora">Hora:</label>
            <input type="time" class="form-control" name="hora" id="hora" required>
        </div>

        <div class="form-group mb-3">
            <label for="duracion">Duración (hh:mm):</label>
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

        <div class="checkbox-container">
            <label><strong>Selecciona las mascotas para tu paseo:</strong></label>
            <% for (Mascota mascota : listaMascotas) { %>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="<%= mascota.getId() %>" id="mascota-<%= mascota.getId() %>" name="mascotas">
                <label class="form-check-label" for="mascota-<%= mascota.getId() %>">
                    <%= mascota.getNombre() %>
                </label>
            </div>
            <% } %>
        </div>

        <button type="submit" class="btn btn-submit mt-4">
            Crear Ticket
        </button>
    </form>
    <% } %>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
