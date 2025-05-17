package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Ticket;

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
}
