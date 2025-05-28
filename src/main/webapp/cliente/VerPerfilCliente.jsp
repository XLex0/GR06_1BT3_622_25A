<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Mostrar mensaje si el perfil no está disponible -->
<c:if test="${noPerfil == true}">
    <div class="container py-5 text-center">
        <i class="fas fa-exclamation-triangle fa-4x text-warning mb-3"></i>
        <h4 class="text-muted">Este paseador aún no ha configurado su perfil o no está disponible.</h4>
        <p class="text-secondary">Vuelve a intentarlo más tarde o elige otro paseador disponible.</p>
    </div>
</c:if>

<!-- Mostrar perfil si está configurado y disponible -->
<c:if test="${noPerfil != true}">
    <div class="container py-5">
        <!-- Avatar -->
        <div class="d-flex justify-content-center mb-4">
            <div class="rounded-circle shadow-lg d-flex align-items-center justify-content-center"
                 style="width: 140px; height: 140px; background-color: #e8f5e9;">
                <i class="fas fa-user fa-4x text-success"></i>
            </div>
        </div>

        <!-- Nombre y teléfono -->
        <div class="text-center">
            <h3 class="fw-bold text-success mb-1">
                <i class="fas fa-user-circle me-2"></i> ${paseador.nombre} ${paseador.apellido}
            </h3>
            <p class="text-muted"><i class="fas fa-phone me-2"></i> ${paseador.telefono}</p>
        </div>

        <hr class="my-4">

        <!-- Información del perfil -->
        <div class="row text-start px-md-5">
            <div class="col-md-6 mb-3">
                <strong>Años de experiencia:</strong> ${anios}
            </div>
            <div class="col-md-6 mb-3">
                <strong>Zonas de trabajo:</strong> ${zonas}
            </div>
            <div class="col-md-6 mb-3">
                <strong>Tipos de mascotas:</strong> ${fn:join(tiposSeleccionados, ", ")}
            </div>
            <div class="col-md-6 mb-3">
                <strong>Servicios ofrecidos:</strong> ${fn:join(serviciosSeleccionados, ", ")}
            </div>
            <div class="col-md-6 mb-3">
                <strong>Horarios disponibles:</strong> ${horarios}
            </div>
            <div class="col-md-12 mb-3">
                <strong>Certificaciones:</strong> ${certificaciones}
            </div>
        </div>
    </div>
</c:if>
