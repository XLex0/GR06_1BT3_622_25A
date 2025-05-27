package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entities.Usuario;
import model.service.PaseadorServ;

import java.io.IOException;

@WebServlet("/PaseadorController")
public class PaseadorController extends HttpServlet {

    private final PaseadorServ paseadorServ = new PaseadorServ();

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
            case "verPerfil":
                verPerfil(req, resp);
                break;
            case "actualizarPerfil":
                actualizarPerfil(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida.");
        }
    }

    private void verPerfil(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario paseador = (Usuario) session.getAttribute("user");

        if (paseador == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        req.setAttribute("paseador", paseador);
        req.getRequestDispatcher("paseador/VerPerfil.jsp").forward(req, resp);
    }

    private void actualizarPerfil(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario paseador = (Usuario) session.getAttribute("user");

        if (paseador == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String experiencia = req.getParameter("experiencia");
        boolean disponible = req.getParameter("disponible") != null;

        paseador.setExperiencia(experiencia);
        paseador.setDisponible(disponible);

        boolean exito = paseadorServ.guardarPerfil(paseador);
        String mensaje = paseadorServ.generarMensaje(exito);

        if (exito) {
            session.setAttribute("user", paseador);
        }

        req.setAttribute("paseador", paseador);
        req.setAttribute("success", exito);
        req.setAttribute("message", mensaje);
        req.getRequestDispatcher("paseador/VerPerfil.jsp").forward(req, resp);
    }
}
