package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entities.*;
import model.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/UsuarioController")
public class GestionarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String route = req.getParameter("route");

        switch (route) {
            case "list":
                listUsers(req, resp);
                break;
            case "create":
                createUser(req, resp);
                break;
            case "find":
                findUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rol = req.getParameter("Rol");

        if (rol != null) {
            if (Objects.equals(rol, Rol.Cliente.toString())) {

                List<Usuario> clientes = new ClienteServ().getAllUsuarios();
                req.setAttribute("clientes", clientes);
                req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

            } else if (Objects.equals(rol, Rol.Paseador.toString())) {

                List<Usuario> paseadores = new PaseadorServ().getAllUsuarios();
                req.setAttribute("paseadores", paseadores);
                req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

            } else {

                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rol no reconocido.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'Rol' es obligatorio.");
        }
    }


    private void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rol = req.getParameter("Rol");

        if (rol != null) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                try {
                    Long id = Long.parseLong(idStr);
                    Usuario usuario = null;

                    // Dependiendo del rol, buscamos el usuario
                    if (Objects.equals(rol, Rol.Cliente.toString())) {
                        usuario = new ClienteServ().findUsuarioById(id); // Llamada para buscar cliente por ID
                        req.setAttribute("cliente", usuario); // Pasamos el cliente a la vista
                    } else if (Objects.equals(rol, Rol.Paseador.toString())) {
                        usuario = new PaseadorServ().findUsuarioById(id); // Llamada para buscar paseador por ID
                        req.setAttribute("paseador", usuario); // Pasamos el paseador a la vista
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rol no reconocido.");
                        return; // Salimos si el rol no es válido
                    }

                    req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID proporcionado no es válido.");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' es obligatorio.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'Rol' es obligatorio.");
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean success = false;

        try {
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String email = req.getParameter("email");
            String telefono = req.getParameter("telefono");
            String contrasena = req.getParameter("contrasena");
            String rolStr = req.getParameter("rol");

            Rol rol = Rol.valueOf(rolStr);

            Usuario nuevoUsuario = new Usuario(nombre, apellido, email, telefono, contrasena, rol);

            if (rol == Rol.Cliente) {
                ClienteServ clienteServ = new ClienteServ();
                Usuario clientePreparado = clienteServ.prepareUsuario(nuevoUsuario);
                success = clienteServ.createUsuario(clientePreparado);
            } else if (rol == Rol.Paseador) {
                PaseadorServ paseadorServ = new PaseadorServ();
                Usuario paseadorPreparado = paseadorServ.prepareUsuario(nuevoUsuario);
                success = paseadorServ.createUsuario(paseadorPreparado);
            }

        } catch (Exception e) {

        }

        req.getSession().setAttribute("success", success);
        resp.sendRedirect(req.getContextPath() + "/auth/message.jsp");
    }

    private void setFlash(HttpServletRequest req, boolean success, String ok, String fail) {
        HttpSession session = req.getSession();
        if (success) {
            session.setAttribute("messageType", "success");
            session.setAttribute("message", ok);
        } else {
            session.setAttribute("messageType", "error");
            session.setAttribute("message", fail);
        }
    }

}