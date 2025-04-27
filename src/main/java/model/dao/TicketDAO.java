package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Ticket;

import java.util.List;

public class TicketDAO {
    private static final String PERSISTENCE_UNIT = "Pets";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    public void registrarTicket(Ticket ticket) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

   public List<Ticket> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Ticket findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }
}
