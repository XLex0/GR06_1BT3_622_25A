<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PetGo – Pasea tu perro con confianza</title>
    <style>
        body {
            margin: 0; padding: 0;
            font-family: Arial, sans-serif;
            background: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .landing {
            text-align: center;
            background: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .landing h1 {
            margin-bottom: 10px;
            color: #4CAF50;
        }
        .landing p {
            margin-bottom: 30px;
            color: #555;
        }
        .buttons a {
            display: inline-block;
            margin: 0 10px;
            padding: 12px 24px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: bold;
            background: #4CAF50;
            color: #fff;
            transition: background 0.3s ease;
        }
        .buttons a:hover {
            background: #45A049;
        }
    </style>
</head>
<body>
<div class="landing">
    <h1>Bienvenido a PetGo</h1>
    <p>Conecta dueños de perros con paseadores de confianza</p>
    <div class="buttons">
        <!-- Ajusta href a la URL de tu página de inicio -->
        <a href="inicio.jsp">Inicio</a>
        <!-- Ajusta href a la URL de tu página de registro -->
        <a href="auth/registro.jsp">Registrarse</a>
    </div>
</div>
</body>
</html>
