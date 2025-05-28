<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${noPerfil == true}">
    <div class="container py-5 text-center">
        <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
        <h4 class="text-muted">Este paseador aún no ha configurado su perfil.</h4>
        <p class="text-secondary">Vuelve a intentarlo más tarde o elige otro paseador disponible.</p>
    </div>
</c:if>

<c:if test="${noPerfil != true}">
    <div class="container py-4 text-center">
        <!-- Avatar -->
        <div class="mb-3">
            <div class="rounded-circle mx-auto shadow d-flex justify-content-center align-items-center"
                 style="width: 120px; height: 120px; background-color: #e8f5e9;">
                <i class="fas fa-user fa-3x text-success"></i>
            </div>
        </div>

        <!-- Nombre y teléfono -->
        <h4 class="fw-bold text-success">
            <i class="fas fa-user-circle me-2"></i> ${paseador.nombre} ${paseador.apellido}
        </h4>
        <p class="text-muted"><i class="fas fa-phone me-1"></i> ${paseador.telefono}</p>

        <hr class="my-4">

        <!-- Info -->
        <div class="row text-start px-4">
            <div class="col-md-6 mb-2">
                <strong>Años de experiencia:</strong> ${anios}
            </div>
            <div class="col-md-6 mb-2">
                <strong>Zonas de trabajo:</strong> ${zonas}
            </div>
            <div class="col-md-6 mb-2">
                <strong>Tipos de mascotas:</strong> ${fn:join(tiposSeleccionados, ", ")}
            </div>
            <div class="col-md-6 mb-2">
                <strong>Servicios ofrecidos:</strong> ${fn:join(serviciosSeleccionados, ", ")}
            </div>
            <div class="col-md-6 mb-2">
                <strong>Horarios disponibles:</strong> ${horarios}
            </div>
            <div class="col-12 mt-2">
                <strong>Certificaciones:</strong> ${certificaciones}
            </div>
        </div>
    </div>
</c:if>
