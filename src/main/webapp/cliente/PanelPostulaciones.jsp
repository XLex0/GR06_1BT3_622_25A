<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    Boolean success = (Boolean) session.getAttribute("success");
    String message = (String) session.getAttribute("message");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil – PetGo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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

<main class="flex-grow-1 bg-light py-5">
    <div class="container">
        <h1 class="text-success fw-bold text-center mb-5">Mi Perfil</h1>

        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm rounded-4 border-0">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/PaseadorController?route=actualizarPerfil" method="post">
                            <div class="mb-3">
                                <label for="experiencia" class="form-label">Experiencia</label>
                                <textarea class="form-control" id="experiencia" name="experiencia" rows="4"
                                          placeholder="Ej. Tengo 2 años paseando perros...">${paseador.experiencia}</textarea>
                            </div>

                            <div class="form-check form-switch mb-4">
                                <input class="form-check-input" type="checkbox" id="disponible" name="disponible"
                                ${paseador.disponible ? "checked" : ""}>
                                <label class="form-check-label" for="disponible">Estoy disponible para paseos</label>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-success rounded-pill">Guardar Cambios</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${not empty message}">
            <script>
                Swal.fire({
                    icon: '${success ? "success" : "error"}',
                    title: '${success ? "Éxito" : "Error"}',
                    text: '${message}',
                    confirmButtonColor: '#4CAF50'
                });
            </script>
        </c:if>
    </div>
</main>

<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
