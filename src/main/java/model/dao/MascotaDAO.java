package model.dao;

import jakarta.persistence.EntityManager;
import model.entities.Mascota;

import java.util.List;

public class MascotaDAO {
    private final EntityManager em;

    public MascotaDAO(EntityManager em) {
        this.em = em;
    }

    public void create(String nombre, String raza, Integer edad,
            Float peso, String comportamiento,
            String genero, Long usuarioId) {

        Mascota pet = new Mascota(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
        try {
            em.getTransaction().begin();
            em.persist(pet);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Mascota> findAll() {
        try {
            return em.createQuery("SELECT p FROM Mascota p", Mascota.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Mascota findById(Long id) {
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }
}
