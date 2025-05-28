package model.dao;

import jakarta.persistence.EntityManager;
import model.entities.Postulacion;

import java.util.List;

public class PostulacionDAO {

    private final EntityManager em;

    public PostulacionDAO(EntityManager em) {
        this.em = em;
    }

    public void registrarPostulacion(Postulacion postulacion) {
        em.getTransaction().begin();
        em.persist(postulacion);
        em.getTransaction().commit();
    }

    public List<Postulacion> listarPostulacionesPorPaseador(Long paseadorId) {
        return em.createQuery(
                        "SELECT p FROM Postulacion p WHERE p.usuario.id = :paseadorId", Postulacion.class)
                .setParameter("paseadorId", paseadorId)
                .getResultList();
    }


    public Postulacion findById(Long id) {
        return em.find(Postulacion.class, id);
    }

    public void update(Postulacion postulacion) {
        em.getTransaction().begin();
        em.merge(postulacion);
        em.getTransaction().commit();
    }

    public List<Postulacion> listarPostulacionesPorCliente(Long clienteId) {
        List<Postulacion> lista = em.createQuery(
                        "SELECT p FROM Postulacion p " +
                                "JOIN FETCH p.usuario paseador " +
                                "JOIN FETCH p.ticket t " +
                                "JOIN FETCH t.usuario cliente " +
                                "WHERE t.usuario.id = :clienteId", Postulacion.class)
                .setParameter("clienteId", clienteId)
                .getResultList();

        for (Postulacion p : lista) {
            em.refresh(p);
        }

        return lista;
    }

    public void rechazarOtrasPostulaciones(Long ticketId, Long postulacionAceptadaId) {
        em.getTransaction().begin();
        em.createQuery("UPDATE Postulacion p SET p.aprobado = false " +
                        "WHERE p.ticket.id = :ticketId AND p.id <> :idAceptada AND p.aprobado IS NULL")
                .setParameter("ticketId", ticketId)
                .setParameter("idAceptada", postulacionAceptadaId)
                .executeUpdate();
        em.getTransaction().commit();
    }
}
