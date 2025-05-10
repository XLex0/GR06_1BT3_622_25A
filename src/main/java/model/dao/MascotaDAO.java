package model.dao;

import jakarta.persistence.EntityManager;
import model.entities.Mascota;

import java.util.List;

public class MascotaDAO {
    private final EntityManager em;

    public MascotaDAO(EntityManager em) {
        this.em = em;
    }

    public void crear(String nombre, String raza, Integer edad,
                      Float peso, String comportamiento, String genero, Long usuarioId) {

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

    public List<Mascota> buscarTodosPorUsuarioId(Long id) {
        try {
            return em.createQuery("SELECT m FROM Mascota m WHERE m.usuarioId = :id", Mascota.class)
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Boolean actualizar(String nombre, String raza, Integer edad,
                              Float peso, String comportamiento, String genero, Long id) {
        boolean status = false;
        try {
            em.getTransaction().begin();

            Mascota pet = em.find(Mascota.class, id);
            if (pet != null) {
                pet.setNombre(nombre);
                pet.setRaza(raza);
                pet.setEdad(edad);
                pet.setPeso(peso);
                pet.setComportamiento(comportamiento);
                pet.setGenero(genero);

                em.merge(pet);
                em.getTransaction().commit();
                status = true; // ✅ Solo se establece como true si hubo actualización
            } else {
                em.getTransaction().rollback(); // ❗ Cancelamos si no se encuentra la mascota
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Solo hacemos rollback si la transacción sigue activa
            }
            status = false;
        } finally {
            em.close();
        }
        return status;
    }


}
