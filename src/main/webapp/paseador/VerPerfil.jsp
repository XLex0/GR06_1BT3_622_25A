<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.entities.Usuario" %>
<%
    String contextPath = request.getContextPath();
%>
<%
    HttpSession sesion = request.getSession();
    Usuario paseador = (Usuario) sesion.getAttribute("user");

    String[] tipos = (String[]) request.getAttribute("tiposSeleccionados");
    String[] servicios = (String[]) request.getAttribute("serviciosSeleccionados");

    request.setAttribute("tiposSeleccionadosAsString", tipos != null ? String.join(",", tipos) : "");
    request.setAttribute("serviciosSeleccionadosAsString", servicios != null ? String.join(",", servicios) : "");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil de Paseador – PetGo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
<%@ include file="../includes/headerPostulador.jsp" %>

<main class="flex-grow-1 py-5">
    <div class="container">
        <div class="mx-auto p-4 shadow bg-white rounded-4" style="max-width: 650px;">
            <h2 class="text-center text-success mb-4 fw-bold"><i class="fas fa-user-edit me-2"></i>Mi Perfil como Paseador</h2>
            <form action="${pageContext.request.contextPath}/PaseadorController?route=actualizarPerfil" method="POST">
                <div class="mb-3">
                    <label class="form-label">Años de experiencia:</label>
                    <input type="number" min="0" max="50" class="form-control rounded-pill" name="anios" value="${anios}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tipos de mascotas:</label><br>
                    <c:set var="tipos" value="${tiposSeleccionadosAsString}" />
                    <c:forEach var="tipo" items="${{'Perros pequeños','Perros grandes','Gatos','Otras mascotas'}}">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" name="tipos" value="${tipo}" <c:if test="${fn:contains(tipos, tipo)}">checked</c:if>>
                            <label class="form-check-label">${tipo}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <label class="form-label">Servicios ofrecidos:</label><br>
                    <c:set var="servicios" value="${serviciosSeleccionadosAsString}" />
                    <c:forEach var="servicio" items="${{'Paseos individuales','Paseos grupales','Juegos','Medicamentos'}}">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" name="servicios" value="${servicio}" <c:if test="${fn:contains(servicios, servicio)}">checked</c:if>>
                            <label class="form-check-label">${servicio}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <label class="form-label">Zonas de trabajo:</label>
                    <input type="text" class="form-control rounded-pill" name="zonas" value="${zonas}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Horarios disponibles:</label>
                    <input type="text" class="form-control rounded-pill" name="horarios" value="${horarios}" required>
                </div>
                <div class="mb-4">
                    <label class="form-label">Certificaciones:</label>
                    <input type="text" class="form-control rounded-pill" name="certificaciones" value="${certificaciones}">
                </div>
                <div class="form-check form-switch mb-4">
                    <input class="form-check-input" type="checkbox" id="disponible" name="disponible" <%= paseador.isDisponible() ? "checked" : "" %>>
                    <label class="form-check-label" for="disponible">Estoy disponible para paseos</label>
                </div>
                <button type="submit" class="btn btn-success w-100 rounded-pill">
                    <i class="fas fa-save me-2"></i>Guardar Perfil
                </button>
            </form>
        </div>
    </div>
</main>
<footer class="bg-light text-center text-muted py-4 mt-auto">
    <p class="mb-2">&copy; 2025 <span class="text-success">PetGo</span> – Paseos con Amor</p>
</footer>
<c:if test="${not empty successM && not empty messageM}">
    <script>
        Swal.fire({
            icon: '${successM ? "success" : "error"}',
            title: '${successM ? "Éxito" : "Error"}',
            text: '${messageM}',
            confirmButtonColor: '#4CAF50'
        });
    </script>
</c:if>
<script>
document.querySelector("form").addEventListener("submit", function (e) {
    const anios = document.querySelector("input[name='anios']").value.trim();
    const zonas = document.querySelector("input[name='zonas']").value.trim();
    const horarios = document.querySelector("input[name='horarios']").value.trim();
    const certificaciones = document.querySelector("input[name='certificaciones']").value.trim();

    const tipos = Array.from(document.querySelectorAll("input[name='tipos']:checked")).map(el => el.value);
    const servicios = Array.from(document.querySelectorAll("input[name='servicios']:checked")).map(el => el.value);

    // Validar que al menos un tipo y un servicio esté seleccionado
    if (tipos.length === 0 || servicios.length === 0) {
        e.preventDefault();
        Swal.fire({
            icon: 'warning',
            title: 'Faltan campos por seleccionar',
            text: 'Debes seleccionar al menos un tipo de mascota y un servicio ofrecido.',
            confirmButtonColor: '#4CAF50'
        });
        return;
    }

    // Validar límite de palabras (máx 60 palabras)
    const textoCompleto = [anios, zonas, horarios, certificaciones, ...tipos, ...servicios].join(" ");
    const palabraCount = textoCompleto.trim().split(/\s+/).length;

    if (palabraCount > 60) {
        e.preventDefault();
        Swal.fire({
            icon: 'error',
            title: 'Demasiadas palabras',
            text: 'Tu información excede el límite de 60 palabras. Por favor resume tu perfil.',
            confirmButtonColor: '#4CAF50'
        });
    }
});
</script>

</body>
</html>
