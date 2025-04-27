package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Ticket;

import java.util.List;

public class TicketDAO {

    private static final String PERSISTENCE_UNIT = "Pets";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    // Crear un nuevo ticket
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

    // Buscar ticket por ID
    public Ticket findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    // Eliminar un ticket por ID
    public void eliminarTicket(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Ticket ticket = em.find(Ticket.class, id);
            if (ticket != null) {
                em.getTransaction().begin();
                em.remove(ticket);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    // Listar todos los tickets
    public List<Ticket> listarTickets() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Listar tickets no asignados
    public List<Ticket> listarTicketsDisponibles() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Ticket t WHERE t.asignado = FALSE", Ticket.class).getResultList();
        } finally {
            em.close();
        }
    }
}
