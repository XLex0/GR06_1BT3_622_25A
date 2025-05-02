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
        enrutador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        enrutador(req, resp);
    }

    private void enrutador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = req.getParameter("route");

        switch (ruta) {

            case "create":
                crearUsuario(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void crearUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean exito = false;
        String mensaje = "";

        try {
            Usuario nuevoUsuario = obtenerUsuario(req);

            // Usamos la factory para obtener el servicio adecuado
            UsuarioServ usuarioServ = UsuarioServFactory.getServicio(nuevoUsuario.getRol());
            Usuario usuarioPreparado = usuarioServ.prepararUsuario(nuevoUsuario);
            exito = usuarioServ.crearUsuario(usuarioPreparado);

            mensaje = exito ? "¡Usuario creado con éxito!" : "Error al crear el usuario.";

        } catch (Exception e) {
            exito = false;
            mensaje = "Ocurrió un error: " + e.getMessage();
        }

        HttpSession sesion = req.getSession();
        sesion.setAttribute("success", exito);
        sesion.setAttribute("message", mensaje);

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }


    private Usuario obtenerUsuario(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String contrasena = req.getParameter("contrasena");
        String rolStr = req.getParameter("rol");
        Rol rol = Rol.valueOf(rolStr);
        return new Usuario(nombre, apellido, email, telefono, contrasena, rol);
    }



}