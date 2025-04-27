package model.service;

import model.dao.TicketDAO;
import model.entities.Ticket;
import model.entities.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TicketService {
    private final TicketDAO ticketDAO = new TicketDAO();

    public boolean crearTicket(LocalDate fecha, LocalTime hora, String duracion, Long usuarioId) {
        try {
            Ticket ticket = new Ticket();
            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setDuracion(duracion);
            ticket.setAsignado(false);

            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            ticket.setUsuario(usuario);

            ticketDAO.registrarTicket(ticket);

            return true;
        } catch (Exception e) {
            System.err.println("Error al crear ticket: " + e.getMessage());
            return false;
        }
    }

    public Ticket buscarTicketPorId(Long id) {
        return ticketDAO.findById(id);
    }

    public List<Ticket> listarTodosTickets() {
        return ticketDAO.findAll();
    }
}
