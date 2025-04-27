<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar Ticket</title>
</head>
<body>

<h1>Registrar un nuevo Ticket</h1>

<form action="${pageContext.request.contextPath}/GestionarTicketController" method="post">
    <input type="hidden" name="accion" value="registrar" />

    <label for="fecha">Fecha (YYYY-MM-DD):</label><br>
    <input type="text" id="fecha" name="fecha" required><br><br>

    <label for="hora">Hora (HH:MM:SS):</label><br>
    <input type="text" id="hora" name="hora" required><br><br>

    <label for="duracion">Duración (por ejemplo, '1 hour'):</label><br>
    <input type="text" id="duracion" name="duracion" required><br><br>

    <label for="usuarioId">ID del Cliente:</label><br>
    <input type="number" id="usuarioId" name="usuarioId" required><br><br>

    <button type="submit">Registrar Ticket</button>
</form>

</body>
</html>
