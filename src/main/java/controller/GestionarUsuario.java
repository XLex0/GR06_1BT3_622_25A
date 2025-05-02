package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Rol;
import model.entities.Usuario;
import model.service.ClienteServ;
import model.service.PaseadorServ;

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

            case "create":
                createUser(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean success = false;
        String message = "";

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

            if (success) {
                message = "¡Usuario creado con éxito!";
            } else {
                message = "Error al crear el usuario.";
            }

        } catch (Exception e) {
            success = false;
            message = "Ocurrió un error: " + e.getMessage();
        }

        HttpSession session = req.getSession();
        session.setAttribute("success", success);
        session.setAttribute("message", message);

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}