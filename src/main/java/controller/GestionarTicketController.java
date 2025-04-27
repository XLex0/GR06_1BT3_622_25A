package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.TicketServ;
import model.entities.Ticket;

import java.io.IOException;

@WebServlet("/GestionarTicketController")
public class GestionarTicketController extends HttpServlet {

    private TicketServ ticketServ = new TicketServ();

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
            case "registrar":
                registrarTicket(request, response);
                break;
            case "eliminar":
                eliminarTicket(request, response);
                break;
            case "listar":
                listarTickets(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    private void registrarTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Obtener datos del formulario
            String fechaStr = request.getParameter("fecha");
            String horaStr = request.getParameter("hora");
            String duracionStr = request.getParameter("duracion");
            Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));

            Ticket ticket = new Ticket();
            ticket.setFecha(fechaStr);
            ticket.setHora(horaStr);
            ticket.setDuracion(duracionStr);
            ticket.setAsignado(false);

            ticket.setUsuario(new model.entities.Usuario());
            ticket.getUsuario().setId(usuarioId);

            ticketServ.registrarTicket(ticket);

            response.sendRedirect("GestionarTicketController?accion=listar");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void eliminarTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            ticketServ.eliminarTicket(id);
            response.sendRedirect("GestionarTicketController?accion=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void listarTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("tickets", ticketServ.listarTickets());
            request.getRequestDispatcher("paseador/postularTickets.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
