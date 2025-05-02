package model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.entities.Usuario;

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
}
