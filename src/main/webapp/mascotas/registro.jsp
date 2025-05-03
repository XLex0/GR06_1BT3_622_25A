<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="model.entities.Mascota" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <title>Registrar Mascota</title>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
        </head>

        <body>
            <div class="navbar">
                <!-- Agrega aquí la barra de navegación si es necesario -->
            </div>

            <div class="form-container">
                <h1>Registrar Nueva Mascota</h1>
                <form action="<%= request.getContextPath() %>/mascotas" method="POST" class="user-form">
                    <input type="hidden" name="route" value="guardarMascota">

                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" required>

                    <label for="raza">Raza:</label>
                    <input type="text" id="raza" name="raza" required>

                    <label for="edad">Edad:</label>
                    <input type="number" id="edad" name="edad" required min="0">

                    <label for="peso">Peso (kg):</label>
                    <input type="number" step="0.1" id="peso" name="peso" required min="0">

                    <label for="comportamiento">Comportamiento:</label>
                    <textarea id="comportamiento" name="comportamiento" required></textarea>

                    <label for="genero">Género:</label>
                    <select id="genero" name="genero" required>
                        <option value="Macho">Macho</option>
                        <option value="Hembra">Hembra</option>
                    </select>

                    <label for="usuario">ID de Usuario:</label>
                    <input type="number" id="usuario" name="usuario" required min="1">

                    <button type="submit">Registrar Mascota</button>
                </form>
            </div>

            <%-- Si hay mensajes flash, los mostramos en la parte inferior --%>
                <% if (session.getAttribute("messageType") !=null) { %>
                    <div class="message <%= session.getAttribute(" messageType") %>">
                        <%= session.getAttribute("message") %>
                    </div>
                    <% session.removeAttribute("messageType"); session.removeAttribute("message"); %>
                        <% } %>

        </body>

        </html>