package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.TicketService;

import java.io.IOException;

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
        if ("list".equals(route)) {
            listarTickets(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void listarTickets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tickets", ticketService.listarTodosTickets());
        req.getRequestDispatcher("paseador/postularTickets.jsp").forward(req, resp);
    }
}


/*
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
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void listarTickets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tickets", ticketService.listarTodosTickets());
        req.getRequestDispatcher("paseador/postularTickets.jsp").forward(req, resp);
    }

    private void guardarNuevoTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean exito = false;
        try {
            String fechaStr = req.getParameter("fecha");
            String horaStr = req.getParameter("hora");
            String duracion = req.getParameter("duracion");
            Long usuarioId = Long.parseLong(req.getParameter("usuarioId"));

            LocalDate fecha = LocalDate.parse(fechaStr);
            LocalTime hora = LocalTime.parse(horaStr);

            exito = ticketService.crearTicket(fecha, hora, duracion, usuarioId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("success", exito);
        resp.sendRedirect(req.getContextPath() + "/ticket/messageTicket.jsp");
    }
}
*/