// ======= PaseadorController.java (actualizado) =======
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
        HttpSession sesion = req.getSession();
        Usuario paseador = (Usuario) sesion.getAttribute("user");

        if (paseador != null && paseador.getExperiencia() != null) {
            req.setAttribute("anios", extraerDato(paseador.getExperiencia(), "Años de experiencia:"));
            req.setAttribute("zonas", extraerDato(paseador.getExperiencia(), "Zonas de trabajo:"));
            req.setAttribute("horarios", extraerDato(paseador.getExperiencia(), "Horarios disponibles:"));
            req.setAttribute("certificaciones", extraerDato(paseador.getExperiencia(), "Certificaciones:"));
            req.setAttribute("tiposSeleccionados", extraerLista(paseador.getExperiencia(), "Tipos de mascotas:"));
            req.setAttribute("serviciosSeleccionados", extraerLista(paseador.getExperiencia(), "Servicios ofrecidos:"));
        }

        req.setAttribute("paseador", paseador);
        req.getRequestDispatcher("paseador/VerPerfil.jsp").forward(req, resp);
    }

    private String extraerDato(String texto, String clave) {
        try {
            int inicio = texto.indexOf(clave);
            if (inicio == -1) return "";
            int fin = texto.indexOf(".", inicio);
            return texto.substring(inicio + clave.length(), fin).trim();
        } catch (Exception e) {
            return "";
        }
    }

    private String[] extraerLista(String texto, String clave) {
        try {
            int inicio = texto.indexOf(clave);
            if (inicio == -1) return new String[0];
            int fin = texto.indexOf(".", inicio);
            String contenido = texto.substring(inicio + clave.length(), fin).trim();
            return contenido.split("\\s*,\\s*");
        } catch (Exception e) {
            return new String[0];
        }
    }
    private String construirExperiencia(String anios, String tipos, String servicios, String zonas, String horarios, String certificaciones) {
        return "Años de experiencia: " + anios + ". "
                + "Tipos de mascotas: " + tipos + ". "
                + "Servicios ofrecidos: " + servicios + ". "
                + "Zonas de trabajo: " + zonas + ". "
                + "Horarios disponibles: " + horarios + ". "
                + "Certificaciones: " + certificaciones + ".";
    }
    private String getParam(HttpServletRequest req, String name) {
        return req.getParameter(name) != null ? req.getParameter(name).trim() : "";
    }
    private String unirValores(String[] valores) {
        return (valores != null) ? String.join(", ", valores) : "";
    }
    private void actualizarPerfil(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sesion = req.getSession();
        Usuario paseador = (Usuario) sesion.getAttribute("user");

        if (paseador != null) {
            String tiposTexto = unirValores(req.getParameterValues("tipos"));
            String serviciosTexto = unirValores(req.getParameterValues("servicios"));

            String anios = getParam(req, "anios");
            String zonas = getParam(req, "zonas");
            String horarios = getParam(req, "horarios");
            String certificaciones = getParam(req, "certificaciones");

            String experiencia = construirExperiencia(anios, tiposTexto, serviciosTexto, zonas, horarios, certificaciones);

            boolean disponible = req.getParameter("disponible") != null;

            paseador.setExperiencia(experiencia);
            paseador.setDisponible(disponible);

            boolean exito = paseadorServ.guardarPerfil(paseador);
            String mensaje = paseadorServ.generarMensaje(exito);

            if (exito) {
                sesion.setAttribute("user", paseador);
            }

            sesion.setAttribute("successM", exito);
            sesion.setAttribute("messageM", mensaje);
        }

        resp.sendRedirect(req.getContextPath() + "/PaseadorController?route=verPerfil");
    }
}