package model.service;

import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TicketService {
    private final DAOFactoria factoria = new DAOFactoria();

    // 🔥 Cambiado: LocalTime en duracion
    public boolean crearTicket(LocalDate fecha, LocalTime hora, LocalTime duracion, Long usuarioId) {
        try {
            Ticket ticket = new Ticket();
            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setDuracion(duracion); // Ahora es LocalTime
            ticket.setAsignado(false);

            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            ticket.setUsuario(usuario);

            factoria.obtenerTicketDAO().registrarTicket(ticket);

            return true;
        } catch (Exception e) {
            System.err.println("Error al crear ticket: " + e.getMessage());
            return false;
        }
    }

    public Ticket buscarTicketPorId(Long id) {
        return factoria.obtenerTicketDAO().findById(id);
    }

    public List<Ticket> listarTodosTickets() {
        return factoria.obtenerTicketDAO().findAll();
    }
}
