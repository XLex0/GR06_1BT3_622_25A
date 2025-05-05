package model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.entities.Postulacion;

public class PostulacionDAO {

    private final EntityManager em;

    public PostulacionDAO(EntityManager em) {
        this.em = em;
    }

    // Registrar una nueva postulación
    public void registrarPostulacion(Postulacion postulacion) {
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
        try {
            return em.createQuery("SELECT p FROM Postulacion p", Postulacion.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Listar una postulación por ID
    public Postulacion listarPostulacion(Long id) {
        try {
            return em.find(Postulacion.class, id);
        } finally {
            em.close();
        }
    }
}
