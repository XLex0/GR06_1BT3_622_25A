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
            Mascota pet = obtenerMascotaActiva(id);

            if (pet != null) {
                aplicarActualizacion(pet, nombre, raza, edad, peso, comportamiento, genero);
                em.merge(pet);
                em.getTransaction().commit();
                status = true;
            } else {
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            status = false;
        }
        return status;
    }

    public boolean inactivarMascota(Long id) {
        try {
            em.getTransaction().begin();
            Mascota pet = obtenerMascotaActiva(id);

            if (pet == null) {
                em.getTransaction().rollback();
                return false;
            }
            pet.setEstado(false);
            em.merge(pet);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }



    private Mascota obtenerMascotaActiva(Long id) {
        Mascota pet = em.find(Mascota.class, id);
        return (pet != null && pet.isEstado()) ? pet : null;
    }


    private void aplicarActualizacion(Mascota pet, String nombre, String raza, Integer edad,
                                      Float peso, String comportamiento, String genero) {
        pet.setNombre(nombre);
        pet.setRaza(raza);
        pet.setEdad(edad);
        pet.setPeso(peso);
        pet.setComportamiento(comportamiento);
        pet.setGenero(genero);
    }




}
