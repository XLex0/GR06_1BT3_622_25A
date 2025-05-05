package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.TicketDAO;
import model.entities.Postulacion;
import model.entities.Ticket;
import model.entities.Usuario;
import model.service.PostulacionServ;

import java.io.IOException;
import java.util.List;

@WebServlet("/PostulacionController")
public class GestionarPostulacion extends HttpServlet {

    private PostulacionServ postulacionServ = new PostulacionServ();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        router(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        router(request, response);
    }

    private void router(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String route = request.getParameter("route");

        switch (route) {
            case "postular":
                postularTicket(request, response);
                break;
            case "listarPostulaciones":
                listarPostulaciones(request, response);
                break;
            case "aceptarPostulacion":
                actualizarEstadoPostulacion(request, response);
                break;
            case "listarPostulacionesCliente":
                listarPostulacionesByCliente(request, response);

                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    private void postularTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String ticketIdStr = request.getParameter("ticketId");

            HttpSession session = request.getSession(false);
            Usuario paseador = (Usuario) (session != null ? session.getAttribute("usuario") : null);

            if (ticketIdStr == null || paseador == null) {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "Parámetros inválidos o sesión expirada.");
                response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
                return;
            }

            Long ticketId = Long.parseLong(ticketIdStr);

            TicketDAO ticketDAO = new TicketDAO();
            Ticket ticket = ticketDAO.findById(ticketId);

            if (ticket != null) {
                Postulacion postulacion = new Postulacion();
                postulacion.setFecha(new java.sql.Date(System.currentTimeMillis()));
                postulacion.setTicket(ticket);
                postulacion.setUsuario(paseador);

                postulacionServ.registrarPostulacion(postulacion);

                request.getSession().setAttribute("success", true);
                request.getSession().setAttribute("message", "¡Postulación exitosa!");
            } else {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "Error: Ticket no encontrado.");
            }

            response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("success", false);
            request.getSession().setAttribute("message", "Parámetros inválidos.");
            response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
        }
    }

    private void listarPostulaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Postulacion> postulaciones = postulacionServ.listarPostulaciones();
        request.setAttribute("postulaciones", postulaciones);
        request.getRequestDispatcher("paseador/PanelPostulaciones.jsp").forward(request, response);
    }

    private void actualizarEstadoPostulacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String idStr = request.getParameter("postulacionId");

            if (idStr != null) {
                Long id = Long.parseLong(idStr);
                postulacionServ.aceptarPostulacion(id);
                request.getSession().setAttribute("success", true);
                request.getSession().setAttribute("message", "Postulación aceptada correctamente.");
            } else {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "ID de postulación inválido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("success", false);
            request.getSession().setAttribute("message", "Error al aceptar la postulación.");
        }

        response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulacionesCliente");
    }

    private void listarPostulacionesByCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario cliente = (Usuario) session.getAttribute("usuario");

        if (cliente == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        List<Postulacion> postulaciones = postulacionServ.listarPostulacionesPorCliente(cliente.getId());

        request.setAttribute("postulaciones", postulaciones);
        request.getRequestDispatcher("cliente/PanelPostulaciones.jsp").forward(request, response);
    }
}
