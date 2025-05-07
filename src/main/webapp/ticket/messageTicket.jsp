<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Boolean success = (Boolean) session.getAttribute("success");
    session.removeAttribute("success"); // Borra para que no se quede
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado del Ticket</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<div class="m-card-container">
    <div class="m-card">
        <h1 class="<%= (success != null && success) ? "success" : "error" %>">
            <% if (success != null && success) { %>
            🎉 ¡Ticket creado con éxito!
            <% } else { %>
            ❌ Hubo un error al crear el ticket.
            <% } %>
        </h1>

        <form action="<%= request.getContextPath() %>/cliente/inicio.jsp" method="GET">
            <button type="submit" class="ok-button">OK</button>
        </form>
    </div>---
</div>

</body>
</html>
