package model.dao;

import jakarta.persistence.EntityManager;
import model.entities.Mascota;
import model.entities.Usuario;

import java.util.List;

public class UsuarioDAO {
    private final EntityManager em;
    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Usuario> findAll() {
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findById(Long id) {
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public Usuario findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Boolean actualizar(Usuario usuario) {
        boolean status = false;
        try {
            em.getTransaction().begin();
            Usuario user = findById(usuario.getId());

            if (user != null) {
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

}
