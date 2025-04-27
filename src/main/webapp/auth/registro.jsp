<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entities.Rol" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Usuario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/auth.css">
</head>
<body>
<div class="navbar">
</div>

<div class="form-container">
    <h1>Registrar Usuario</h1>
    <form action="<%= request.getContextPath() %>/UsuarioController?route=create" method="POST" class="user-form">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required>

        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido" required>

        <label for="email">Correo Electrónico:</label>
        <input type="email" id="email" name="email" required>

        <label for="telefono">Teléfono:</label>
        <input type="tel" id="telefono" name="telefono" required pattern="[0-9]{7,15}" title="Ingrese solo números (7 a 15 dígitos)">

        <label for="contrasena">Contraseña:</label>
        <input type="password" id="contrasena" name="contrasena" required>

        <label for="rol">Rol:</label>
        <select id="rol" name="rol" required>
            <option value="Cliente">Cliente</option>
            <option value="Paseador">Paseador</option>
        </select>

        <button type="submit">Registrar Usuario</button>
    </form>
</div>
</body>
</html>
