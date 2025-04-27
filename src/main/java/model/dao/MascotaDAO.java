package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Mascota;

import java.util.List;

public class MascotaDAO {
    private static final String PERSISTENCE_UNIT = "Pets";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    public void create(String nombre, String raza, Integer edad,
                       Float peso, String comportamiento,
                       String genero, Long usuarioId) {

        Mascota pet = new Mascota(nombre, raza, edad, peso, comportamiento, genero, usuarioId);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pet);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Mascota> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Mascota p", Mascota.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Mascota findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }
}
