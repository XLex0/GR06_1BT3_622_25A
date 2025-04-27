package model.service;

import model.dao.TicketDAO;
import model.entities.Ticket;

import java.util.List;

public class TicketServ {

    private TicketDAO ticketDAO = new TicketDAO();

    // Registrar un nuevo ticket
    public void registrarTicket(Ticket ticket) {
        ticketDAO.registrarTicket(ticket);
    }

    // Buscar un ticket por ID
    public Ticket buscarTicketPorId(Long id) {
        return ticketDAO.findById(id);
    }

    // Eliminar un ticket por ID
    public void eliminarTicket(Long id) {
        ticketDAO.eliminarTicket(id);
    }

    // Listar todos los tickets
    public List<Ticket> listarTickets() {
        return ticketDAO.listarTickets();
    }

    // Listar tickets disponibles (no asignados)
    public List<Ticket> listarTicketsDisponibles() {
        return ticketDAO.listarTicketsDisponibles();
    }
}
