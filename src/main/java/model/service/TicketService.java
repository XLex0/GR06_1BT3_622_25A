package model.service;

import model.entities.Mascota;
import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<Ticket> findTicketPorId(Long userId) {
        List<Ticket> tickets = factoria.obtenerTicketDAO().buscarTodosPorUsuario(userId);

        if (!existenTickets(tickets)) {
            return Collections.emptyList();
        }

        return filtrarTicketsNoCaducados(tickets);
    }

    public List<Ticket> filtrarTicketsNoCaducados(List<Ticket> tickets) {
        LocalDate hoy = LocalDate.now();
        List<Ticket> ticketsNoCaducados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getFecha() != null) {
                if (ticket.getFecha().isAfter(hoy)) {
                    ticketsNoCaducados.add(ticket);
                }
            }
        }
        return ticketsNoCaducados;
    }

    public boolean existenTickets(List<Ticket> tickets) {
        if (tickets == null) {
            return false;
        }

        if (tickets.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean actualizarTicket(LocalDate fecha, LocalTime hora, LocalTime duracion, Long ticketId) {
        if (!validarCampos(fecha, hora, duracion, ticketId)) {
            return false;
        }

        if (!validarFecha(fecha, ticketId)) {
            return false;
        }

        if (!validarDuracion(duracion)) {
            return false;
        }

        return factoria.obtenerTicketDAO().actualizar(fecha, hora, duracion, ticketId);
    }

    public boolean validarCampos(LocalDate fecha, LocalTime hora, LocalTime duracion, Long id) {
        if (fecha == null) {
            return false;
        }
        if (hora == null) {
            return false;
        }
        if (duracion == null) {
            return false;
        }
        return true;
    }

    public boolean validarFecha(LocalDate fechaIngresada, long l) {
        if (fechaIngresada.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public boolean validarDuracion(LocalTime duracion) {
        if (duracion == null) return false;
        int minutosTotales = duracion.getHour() * 60 + duracion.getMinute();
        if (minutosTotales < 30 || minutosTotales > 180) {
            return false;
        }
        return true;
    }
}