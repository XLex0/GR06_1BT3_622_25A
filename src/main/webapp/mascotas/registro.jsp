<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PetGo – Inicio</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="d-flex flex-column min-vh-100">

<header class="navbar navbar-expand-lg navbar-light shadow-sm" style="background: linear-gradient(90deg, #4CAF50 0%, #45a049 100%);">
    <div class="container d-flex justify-content-between align-items-center py-2">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/index.jsp">
            <i class="fas fa-dog fa-2x text-white me-2"></i>
            <span class="fw-bold text-white">PetGo</span>
        </a>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-light rounded-pill px-4">Cerrar Sesión</a>
        </div>
    </div>
</header>

<!-- Botón Añadir Mascota -->
<div class="bg-white shadow-sm">
    <div class="container py-3 d-flex justify-content-center">
        <a href="${pageContext.request.contextPath}/mascotas/registro.jsp" class="btn btn-success rounded-pill px-5 py-2">
            <i class="fas fa-plus me-2"></i> Añadir Mascota
        </a>
    </div>
</div>

<main class="flex-grow-1 bg-light py-5">
    <div class="container text-center">
        <h1 class="text-success fw-bold mb-4">¡Bienvenido, Usuario!</h1>
        <p class="lead text-muted mb-5">
            “Cada paseo es una aventura: ¡sal hoy a descubrir el mundo junto a tu mejor amigo!”
        </p>

        <div class="row justify-content-center g-4">

            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h5 class="card-title text-success fw-bold">Rocky</h5>
                        <p class="card-text"><strong>Raza:</strong> Labrador</p>
                        <p class="card-text"><strong>Edad:</strong> 4 años</p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h5 class="card-title text-success fw-bold">Luna</h5>
                        <p class="card-text"><strong>Raza:</strong> Husky</p>
                        <p class="card-text"><strong>Edad:</strong> 2 años</p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h5 class="card-title text-success fw-bold">Max</h5>
                        <p class="card-text"><strong>Raza:</strong> Beagle</p>
                        <p class="card-text"><strong>Edad:</strong> 5 años</p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h5 class="card-title text-success fw-bold">Bella</h5>
                        <p class="card-text"><strong>Raza:</strong> Poodle</p>
                        <p class="card-text"><strong>Edad:</strong> 3 años</p>
                    </div>
                </div>
            </div>

        </div>

    </div>
</main>

<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
