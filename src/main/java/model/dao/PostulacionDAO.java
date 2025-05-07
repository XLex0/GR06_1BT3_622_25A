package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Postulacion;

import java.util.List;
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


    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        try {
            return em.createQuery("SELECT p FROM Postulacion p", Postulacion.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void aceptarPostulacion(Long id) {
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