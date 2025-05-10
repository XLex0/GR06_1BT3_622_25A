<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="java.util.List" %>
      <%@ page import="model.entities.Mascota" %>

        <% List<Mascota> listaMascotas = (List<Mascota>) request.getAttribute("mascotas");
            String contextPath = request.getContextPath();
            %>

            <!DOCTYPE html>
            <html lang="es">

            <head>
              <meta charset="UTF-8">
              <title>Mis Mascotas</title>
              <!-- Bootstrap CDN -->
              <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

              <style>
                body {
                  background-color: #ffffff;
                  font-family: 'Segoe UI', sans-serif;
                  padding: 2rem;
                }

                h1 {
                  color: #66bb6a;
                  font-weight: bold;
                }

                .card-mascota {
                  background-color: #f8fdf6;
                  border: 1px solid #d0e9d4;
                  border-radius: 12px;
                  padding: 1.5rem;
                  margin-bottom: 1.5rem;
                  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                  transition: transform 0.2s ease;
                }

                .card-mascota:hover {
                  transform: translateY(-3px);
                }

                .card-mascota h5 {
                  color: #388e3c;
                  margin-bottom: 0.5rem;
                }

                .btn-editar {
                  background-color: #66bb6a;
                  color: white;
                  border: none;
                  padding: 0.5rem 1rem;
                  border-radius: 6px;
                }

                .btn-editar:hover {
                  background-color: #4caf50;
                }

                .modal-header {
                  background-color: #a8e6a3;
                  color: #333;
                  border-bottom: none;
                }

                .modal-footer {
                  border-top: none;
                }

                .btn-agregar {
                  background-color: #66bb6a;
                  color: white;
                  border-radius: 6px;
                  padding: 0.6rem 1.2rem;
                  font-weight: bold;
                }

                .btn-agregar:hover {
                  background-color: #4caf50;
                }

                .no-mascotas {
                  margin-top: 3rem;
                  text-align: center;
                  color: #666;
                }

                .btn-agregar-mascota {
                  background-color: #66bb6a;
                  color: white;
                  border-radius: 6px;
                  padding: 0.8rem 2rem;
                  font-weight: bold;
                  display: block;
                  width: 100%;
                  margin-top: 2rem;
                }

                .btn-agregar-mascota:hover {
                  background-color: #4caf50;
                }
              </style>
            </head>

            <body>
              <div class="container">
                <h1 class="mb-4">Mis Mascotas</h1>

                <% if (listaMascotas==null || listaMascotas.isEmpty()) { %>
                  <div class="no-mascotas">
                    <p>No tienes mascotas registradas aún.</p>
                    <a href="<%= contextPath %>/mascotas/formulario.jsp" class="btn btn-agregar mt-3">Agregar
                      Mascota</a>
                  </div>
                  <% } else { %>
                    <div class="row">
                      <% for (Mascota mascota : listaMascotas) { %>
                        <div class="col-md-4">
                          <div class="card-mascota">
                            <h5>
                              <%= mascota.getNombre() %>
                            </h5>
                            <p><strong>Edad:</strong>
                              <%= mascota.getEdad() %> años
                            </p>
                            <p><strong>Raza:</strong>
                              <%= mascota.getRaza() %>
                            </p>
                            <p><strong>Comportamiento:</strong>
                              <%= mascota.getComportamiento() %>
                            </p>
                            <p><strong>Género:</strong>
                              <%= mascota.getGenero() %>
                            </p>
                            <button class="btn-editar mt-2"
                              onclick="abrirModal('<%= mascota.getId() %>')">Editar</button>
                          </div>
                        </div>

                        <!-- Modal personalizado -->
                        <div class="modal fade" id="modal-<%= mascota.getId() %>" tabindex="-1" aria-hidden="true">
                          <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title">Editar Mascota - <%= mascota.getNombre() %>
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                              </div>
                              <form onsubmit="event.preventDefault(); actualizarMascota('<%= mascota.getId() %>');">
                                <div class="modal-body">
                                  <div class="mb-3">
                                    <label class="form-label">Nombre</label>
                                    <input type="text" class="form-control" name="nombre"
                                      value="<%= mascota.getNombre() %>">
                                  </div>
                                  <div class="mb-3">
                                    <label class="form-label">Edad</label>
                                    <input type="number" class="form-control" name="edad"
                                      value="<%= mascota.getEdad() %>">
                                  </div>
                                  <div class="mb-3">
                                    <label class="form-label">Raza</label>
                                    <input type="text" class="form-control" name="raza"
                                      value="<%= mascota.getRaza() %>">
                                  </div>
                                  <div class="mb-3">
                                    <label class="form-label">Peso</label>
                                    <input type="number" step="0.1" class="form-control" name="peso"
                                      value="<%= mascota.getPeso() %>">
                                  </div>
                                  <div class="mb-3">
                                    <label class="form-label">Comportamiento</label>
                                    <input type="text" class="form-control" name="comportamiento"
                                      value="<%= mascota.getComportamiento() %>">
                                  </div>
                                  <div class="mb-3">
                                    <label class="form-label">Género</label>
                                    <input type="text" class="form-control" name="genero"
                                      value="<%= mascota.getGenero() %>">
                                  </div>
                                </div>
                                <div class="modal-footer">
                                  <button type="submit" class="btn btn-agregar">Actualizar</button>
                                  <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Cancelar</button>
                                </div>
                              </form>
                            </div>
                          </div>
                        </div>
                        <% } %>
                    </div>

                    <!-- Botón para agregar una nueva mascota -->
                    <a href="<%= contextPath %>/mascotas/registro.jsp" class="btn-agregar-mascota">Agregar Nueva
                      Mascota</a>
                    <% } %>
              </div>

              <!-- Bootstrap JS -->
              <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
              <script>
                function abrirModal(id) {
                  const modal = new bootstrap.Modal(document.getElementById('modal-' + id));
                  modal.show();
                }

                function actualizarMascota(id) {
                  const modal = document.getElementById('modal-' + id);
                  const inputs = modal.querySelectorAll('input');

                  const formData = new FormData();
                  formData.append('route', 'update');
                  formData.append('id', id);

                  inputs.forEach(input => {
                    formData.append(input.name, input.value);
                  });

                  fetch('<%= contextPath %>/mascotas', {
                    method: 'POST',
                    body: formData
                  })
                    .then(response => {
                      if (response.ok) {
                        window.location.href = '<%= contextPath %>/mascotas?route=list';
                      } else {
                        alert('Error al actualizar mascota');
                      }
                    })
                    .catch(error => {
                      console.error('Error en la actualización:', error);
                      alert('Ocurrió un error al actualizar');
                    });
                }
              </script>
            </body>

            </html>