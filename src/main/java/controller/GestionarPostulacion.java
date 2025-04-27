package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;
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
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    private void postularTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String ticketIdStr = request.getParameter("ticketId");
            String paseadorIdStr = request.getParameter("paseadorId");

            if (ticketIdStr == null || paseadorIdStr == null) {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "Parámetros inválidos.");
                response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
                return;
            }

            Long ticketId = Long.parseLong(ticketIdStr);
            Long paseadorId = Long.parseLong(paseadorIdStr);

            TicketDAO ticketDAO = new TicketDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Ticket ticket = ticketDAO.findById(ticketId);
            Usuario paseador = usuarioDAO.findById(paseadorId);

            if (ticket != null && paseador != null) {
                Postulacion postulacion = new Postulacion();
                postulacion.setFecha(new java.sql.Date(System.currentTimeMillis()));
                postulacion.setTicket(ticket);
                postulacion.setUsuario(paseador);

                postulacionServ.registrarPostulacion(postulacion);

                request.getSession().setAttribute("success", true);
                request.getSession().setAttribute("message", "¡Postulación exitosa!");
            } else if (ticket == null) {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "Error: Ticket no encontrado.");
            } else {
                request.getSession().setAttribute("success", false);
                request.getSession().setAttribute("message", "Error: Paseador no encontrado.");
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

}
