<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Boolean success = (Boolean) session.getAttribute("success");
    session.removeAttribute("success"); // Para que no se quede guardado
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado del Registro</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<div class="card-container">
    <div class="card">
        <h1 class="<%= (success != null && success) ? "success" : "error" %>">
            <% if (success != null && success) { %>
            🎉 ¡Usuario creado con éxito!
            <% } else { %>
            ❌ Hubo un error al crear el usuario.
            <% } %>
        </h1>

        <form action="<%= request.getContextPath() %>/index.jsp" method="GET">
            <button type="submit" class="ok-button">OK</button>
        </form>
    </div>
</div>

</body>
</html>
