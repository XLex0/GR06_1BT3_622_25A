package model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.entities.Ticket;

public class TicketDAO {
    private final EntityManager em;

    public TicketDAO(EntityManager em) {
        this.em = em;
    }

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
}
