package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Postulacion;

import java.util.List;

public class PostulacionDAO {

    private static final String PERSISTENCE_UNIT = "Pets";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    // Registrar una nueva postulación
    public void registrarPostulacion(Postulacion postulacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(postulacion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Postulacion p", Postulacion.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void aceptarPostulacion(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Postulacion postulacion = em.find(Postulacion.class, id);
            if (postulacion != null) {
                postulacion.setAprobado(true);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public List<Postulacion> listarPostulacionesPorCliente(Long clienteId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Postulacion p " +
                                    "JOIN FETCH p.usuario paseador " +
                                    "JOIN FETCH p.ticket t " +
                                    "JOIN FETCH t.usuario cliente " +
                                    "WHERE t.usuario.id = :clienteId", Postulacion.class)
                    .setParameter("clienteId", clienteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }


}