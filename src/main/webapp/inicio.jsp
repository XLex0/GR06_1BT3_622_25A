<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PetGo – Inicio</title>
    <style>
        body {
            margin: 0; padding: 0;
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
        header h1 {
            margin: 0;
            font-size: 2em;
        }
        .motivacion {
            margin: 20px auto;
            max-width: 800px;
            text-align: center;
            font-size: 1.2em;
            color: #555;
        }
        .pets-container {
            max-width: 800px;
            margin: 20px auto;
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }
        .pet-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            padding: 16px;
            width: 200px;
            text-align: center;
        }
        .pet-card h3 {
            margin-top: 0;
            color: #4CAF50;
        }
        .pet-card p {
            margin: 8px 0;
            font-size: 0.95em;
            color: #666;
        }
    </style>
</head>
<body>

<header>
    <h1>¡Hola, Usuario!</h1>
</header>

<div class="motivacion">
    <p>“Cada paseo es una aventura: ¡sal hoy a descubrir el mundo junto a tu mejor amigo!”</p>
</div>

<section class="pets-container">
    <!-- Datos genéricos -->
    <div class="pet-card">
        <h3>Rocky</h3>
        <p><strong>Raza:</strong> Labrador</p>
        <p><strong>Edad:</strong> 4 años</p>
    </div>
    <div class="pet-card">
        <h3>Luna</h3>
        <p><strong>Raza:</strong> Husky</p>
        <p><strong>Edad:</strong> 2 años</p>
    </div>
    <div class="pet-card">
        <h3>Max</h3>
        <p><strong>Raza:</strong> Beagle</p>
        <p><strong>Edad:</strong> 5 años</p>
    </div>
    <div class="pet-card">
        <h3>Bella</h3>
        <p><strong>Raza:</strong> Poodle</p>
        <p><strong>Edad:</strong> 3 años</p>
    </div>
</section>

<a href="mascotas/registro.jsp">Nueva mascota</a>
<a href="ticket/registro.jsp">Nuevo Ticket</a>
<a href="${pageContext.request.contextPath}/TicketController?route=list">Postular</a>
<a href="index.jsp">Salir</a>

</body>
</html>
