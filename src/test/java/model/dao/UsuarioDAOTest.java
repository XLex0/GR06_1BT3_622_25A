package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.client.Client;
import model.entities.Usuario;
import model.service.ClienteServ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UsuarioDAOTest {

    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction transaction;

    private UsuarioDAO dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(em.getTransaction()).thenReturn(transaction);
        dao = new UsuarioDAO(em); // Usa el em mockeado
    }

    @Test
    void given_userNoExiste_when_modificar_then_retornaFalse() {
        // Arrange
        Usuario usuarioInexistente = new Usuario();
        usuarioInexistente.setId(99L); // ID que no existe

        when(em.find(Usuario.class, 99L)).thenReturn(null); // Usuario no existe

        boolean resultado = dao.actualizar(usuarioInexistente);

        assertFalse(resultado);
        verify(em).find(Usuario.class, 99L);
        verify(em, never()).merge(any()); // No debe intentar guardar
        verify(transaction, never()).commit(); // No debe cerrar transacción
    }

    @Test
    void given_UsuarioModified_when_iscorrect_true() {
        // Arrange
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);

        when(em.getTransaction()).thenReturn(transaction);

        when(em.find(Usuario.class, 1L)).thenReturn(usuarioExistente);

        UsuarioDAO dao = new UsuarioDAO(em);

        boolean resultado = dao.actualizar(usuarioExistente);

        assertTrue(resultado);
        verify(em).find(Usuario.class, 1L);
        verify(transaction).begin();
        verify(transaction, never()).rollback();
    }



}
