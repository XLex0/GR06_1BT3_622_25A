package model.service;

import model.entities.Mascota;
import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private final DAOFactoria factoria = new DAOFactoria();

    //Service para crear ticket asociado a mascotas
    public boolean crearTicket(LocalDate fecha, LocalTime hora, LocalTime duracion, Long usuarioId, List<Long> mascotaIds) {
        try {
            Ticket ticket = new Ticket();
            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setDuracion(duracion);
            ticket.setAsignado(false);

            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            ticket.setUsuario(usuario);

            // Crear lista de mascotas solo con IDs
            List<Mascota> mascotas = new ArrayList<>();
            for (Long id : mascotaIds) {
                Mascota m = new Mascota();
                m.setId(id);
                mascotas.add(m);
            }
            ticket.setMascotas(mascotas);

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
