package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Mascota;
import model.entities.Usuario;
import model.service.TicketService;
import model.service.MascotaServicio;
import model.entities.Ticket;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            case "listTicketsByUser":
                listTicketsByUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void verDetallesTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    private Long getIdUserLoged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Usuario user = (Usuario) session.getAttribute("user");

        if (user != null) {
            System.out.println("Usuario logueado: " + user.getId());
            return user.getId();
        } else {
            System.out.println("No hay usuario logueado");
            return null;
        }
    }

    //Guardar ticket asociado con mascotas asociadas
    private void guardarNuevoTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean exito = false;
        try {
            Long usuarioId = getIdUserLoged(req);
            if (usuarioId == null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
            LocalTime hora = LocalTime.parse(req.getParameter("hora"));
            LocalTime duracion = LocalTime.parse(req.getParameter("duracion"));

            String[] mascotasSeleccionadas = req.getParameterValues("mascotas");
            List<Long> idsMascotas = new ArrayList<>();

            if (mascotasSeleccionadas != null) {
                for (String id : mascotasSeleccionadas) {
                    idsMascotas.add(Long.parseLong(id));
                }
            }

            exito = ticketService.crearTicket(fecha, hora, duracion, usuarioId, idsMascotas);

        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getSession().setAttribute("success", exito);
        resp.sendRedirect(req.getContextPath() + "/ticket/messageTicket.jsp");
    }

    //Lista todos los tickets del usuario. El ticket muestra las mascotas asociadas.
    private void listTicketsByUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean success = false;
        try {
            Long idUser = getIdUserLoged(req);
            if (idUser == null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            req.setAttribute("listatickets", ticketService.findTicketPorId(idUser));

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al listar tickets: " + e.getMessage());
            success = false;
        }

        req.getSession().setAttribute("success", success);
        if (success) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/ticket/lista.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al listar tickets");
        }
    }

}