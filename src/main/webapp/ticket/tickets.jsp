<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Tickets - PetGo</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            color: #333;
        }
        header {
            background: #4CAF50;
            color: white;
            padding: 20px;
            text-align: center;
        }
        .content {
            max-width: 800px;
            margin: 30px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover {
            background-color: #45A049;
        }
        .mensaje {
            padding: 10px;
            margin-top: 10px;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>

<header>
    <h1>¡Nuevo paseo!</h1>
</header>

<div class="content">

    <% if(session.getAttribute("message") != null) { %>
    <div class="mensaje <%= session.getAttribute("messageType") %>">
        <%= session.getAttribute("message") %>
    </div>
    <% session.removeAttribute("message"); session.removeAttribute("messageType"); } %>

    <h2>Registrar nuevo paseo</h2>
    <!-- ✅ CORREGIDO: el form ahora usa contextPath -->
    <form action="<%= request.getContextPath() %>/TicketController?route=saveNew" method="post">
        <div class="form-group">
            <label>Fecha:</label>
            <input type="date" name="fecha" required>
        </div>
        <div class="form-group">
            <label>Hora:</label>
            <input type="time" name="hora" required>
        </div>
        <div class="form-group">
            <label>Duración:</label>
            <input type="text" name="duracion" placeholder="Ejemplo: 1 hora" required>
        </div>
        <div class="form-group">
            <label>ID Cliente:</label>
            <input type="number" name="usuarioId" required>
        </div>
        <button type="submit">Crear Ticket</button>
    </form>
</div>

</body>
</html>
