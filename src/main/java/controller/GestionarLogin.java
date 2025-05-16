package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entities.*;
import model.service.*;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/LoginController")
public class GestionarLogin extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ruta(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ruta(req, resp);
    }
    private void ruta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = req.getParameter("route");
        if ("login".equals(ruta)) {
            login(req, resp);
        } else if ("logout".equals(ruta)) {
            logout(req, resp);
        } else {
            resp.getWriter().write("Unknown route");
        }
    }

        private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String email = req.getParameter(    "email");
            String contrasena = req.getParameter("password");
            String rolStr = req.getParameter("rol");
            Rol rol = Rol.valueOf(rolStr);
            Usuario usuario = null;
            boolean login = false;
            String mensaje = "Error al iniciar sesión.";
            if (rol == Rol.Cliente) {
                ClienteServ clienteServ = new ClienteServ();
                usuario = clienteServ.ingresar(email, contrasena);
            } else if (rol == Rol.Paseador) {
                PaseadorServ paseadorServ = new PaseadorServ();
                usuario = paseadorServ.ingresar(email, contrasena);
            }
            if (usuario != null) {
                login = true;
                mensaje = "Usuario ingresado correctamente";
                HttpSession session = req.getSession();
                session.setAttribute("user", usuario);
                session.setAttribute("rol", rol.name());
            }
            HttpSession sesion = req.getSession();
            sesion.setAttribute("success", login);
            sesion.setAttribute("message", mensaje);

            if(login) {
                resp.sendRedirect(req.getContextPath() + "/cliente/inicio.jsp");
            }else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
            // hola mundo
        }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();
        if (sesion != null) {
            sesion.invalidate();
        }
        req.setAttribute("messageType", "info");
        req.setAttribute("message", "You have successfully logged out.");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
