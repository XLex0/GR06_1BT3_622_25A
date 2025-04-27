package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Postulacion;
import model.entities.Ticket;
import model.entities.Usuario;
import model.service.PostulacionServ;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

import java.io.IOException;

@WebServlet("/GestionarPostulacionController")
public class GestionarPostulacionController extends HttpServlet {

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
            Long ticketId = Long.parseLong(request.getParameter("ticketId"));
            HttpSession session = request.getSession();
            Long paseadorId = (Long) session.getAttribute("idUsuario");

            if (paseadorId != null) {
                Postulacion postulacion = new Postulacion();
                postulacion.setFecha(new java.sql.Date(System.currentTimeMillis()).toString());

                TicketDAO ticketDAO = new TicketDAO();
                Ticket ticket = ticketDAO.findById(ticketId);
                postulacion.setTicket(ticket);

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario paseador = usuarioDAO.findById(paseadorId);
                postulacion.setUsuario(paseador);

                postulacion.setAprobado(false);

                postulacionServ.registrarPostulacion(postulacion);

                response.sendRedirect("paseador/postularTickets.jsp?mensaje=PostulacionExitosa");
            } else {
                response.sendRedirect("paseador/postularTickets.jsp?mensaje=ErrorPostulacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void listarPostulaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lógica para listar las postulaciones (si fuera necesario)
        request.setAttribute("postulaciones", postulacionServ.listarPostulaciones());
        request.getRequestDispatcher("paseador/listarPostulaciones.jsp").forward(request, response);
    }
}
