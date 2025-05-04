package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entities.Ticket;
import model.service.TicketService;

@WebServlet("/TicketController")
public class GestionarTicket extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TicketService ticketService = new TicketService();

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
            case "list":
                listarTickets(req, resp);
                break;
            case "saveNew":
                guardarNuevoTicket(req, resp);
                break;
            case "verDetalles": //
                verDetallesTicket(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void verDetallesTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long ticketId = Long.parseLong(req.getParameter("ticketId"));
            Ticket ticket = ticketService.buscarTicketPorId(ticketId);

            if (ticket != null) {
                req.setAttribute("ticket", ticket);
                req.getRequestDispatcher("paseador/PanelTicket.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket no encontrado");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de ticket no válido");
        }
    }

    private void listarTickets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tickets", ticketService.listarTodosTickets());
        req.getRequestDispatcher("paseador/PanelPostulador.jsp").forward(req, resp);
    }

    private void guardarNuevoTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean exito = false;
        try {
            //Refactoring Inline Variable
            LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
            LocalTime hora = LocalTime.parse(req.getParameter("hora"));
            LocalTime duracion = LocalTime.parse(req.getParameter("duracion"));
            Long usuarioId = Long.parseLong(req.getParameter("usuarioId"));

            exito = ticketService.crearTicket(fecha, hora, duracion, usuarioId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("success", exito);
        resp.sendRedirect(req.getContextPath() + "/ticket/messageTicket.jsp");
    }

}