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

    // Eliminar una postulación
    public void eliminarPostulacion(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Postulacion postulacion = em.find(Postulacion.class, id);
            if (postulacion != null) {
                em.getTransaction().begin();
                em.remove(postulacion);
                em.getTransaction().commit();
            }
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

    // Listar una postulación por ID
    public Postulacion listarPostulacion(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Postulacion.class, id);
        } finally {
            em.close();
        }
    }
}
