<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil del Paseador</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow rounded-4">
                <div class="card-body">
                    <h3 class="text-center mb-4">Editar Perfil</h3>

                    <form action="${pageContext.request.contextPath}/PaseadorController?route=actualizarPerfil" method="post">
                        <div class="mb-3">
                            <label for="experiencia" class="form-label">Experiencia</label>
                            <textarea class="form-control" id="experiencia" name="experiencia" rows="5"
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

                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-info mt-4 text-center">${mensaje}</div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
