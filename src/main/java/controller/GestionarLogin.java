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
        String route = req.getParameter("route");
        if (route.equals("login")) {
            login(req, resp);
        } else if (route.equals("logout")) {
            logout(req, resp);
        } else {
            resp.getWriter().write("Unknown route");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String route = req.getParameter("route");
        if (route.equals("login")) {
            login(req, resp);
        } else if (route.equals("logout")) {
            logout(req, resp);
        } else {
            resp.getWriter().write("Unknown route");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rolStr = req.getParameter("rol");
        Rol rol = Rol.valueOf(rolStr);
        Usuario user = null;
        boolean login = false;
        String mensaje = "Error al iniciar sesión.";

        if (rol == Rol.Cliente) {
            ClienteServ clienteServ = new ClienteServ();
            user = clienteServ.ingresar(email, password);
            if (user != null) {
                login = true;
                mensaje = "Usuario ingresado correctamente";
                HttpSession session = req.getSession();
                session.setAttribute("usuario", user);
                session.setAttribute("rol", "Cliente");
            }
        } else if (rol == Rol.Paseador) {
            PaseadorServ paseadorServ = new PaseadorServ();
            user = paseadorServ.ingresar(email, password);
            if (user != null) {
                login = true;
                mensaje = "Usuario ingresado correctamente";
                HttpSession session = req.getSession();
                session.setAttribute("usuario", user);
                session.setAttribute("rol", "Paseador");
            }
        }

        HttpSession session = req.getSession();
        session.setAttribute("success", login);
        session.setAttribute("message", mensaje);

        if (login) {
            resp.sendRedirect(req.getContextPath() + "/cliente/inicio.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        req.setAttribute("messageType", "info");
        req.setAttribute("message", "You have successfully logged out.");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
