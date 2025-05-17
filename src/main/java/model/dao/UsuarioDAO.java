package model.dao;

import jakarta.persistence.EntityManager;
import model.entities.Usuario;

import java.util.List;

public class UsuarioDAO {
    private final EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public List<Usuario> findAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean actualizar(Usuario usuario) {
        boolean status = false;
        try {
            em.getTransaction().begin();
            Usuario user = findById(usuario.getId());

            if (user != null) {
                em.merge(usuario);
                em.getTransaction().commit();
                status = true;
            } else {
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("❌ Error en actualizar(): " + e);
            status = false;
        }
        return status;
    }
}
