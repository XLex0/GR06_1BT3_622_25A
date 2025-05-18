package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TicketDAO {
    private final EntityManager em;
    public TicketDAO(EntityManager em){this.em = em;}

    public void registrarTicket(Ticket ticket) {
        try {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Ticket> findAll() {
        try {
            return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Ticket findById(Long id) {
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }
    //Sentencia para traer las mascotas asociadas
    public List<Ticket> buscarTodosPorUsuario(Long usuarioId) {
        try {
            return em.createQuery(
                            "SELECT DISTINCT t FROM Ticket t " +
                                    "JOIN FETCH t.mascotas " +
                                    "WHERE t.usuario.id = :usuarioId", Ticket.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public boolean actualizar(LocalDate fecha, LocalTime hora, LocalTime duracion, Long ticketId) {
        boolean status = false;
        try {
            em.getTransaction().begin();

            Ticket ticket = em.find(Ticket.class, ticketId);
            if (ticket == null) {
                em.getTransaction().rollback();
                return false;
            }

            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setDuracion(duracion);

            em.merge(ticket);

            em.getTransaction().commit();
            status = true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return status;
    }

}
