package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

        // Act
        boolean resultado = dao.actualizar(usuarioInexistente);

        // Assert
        assertFalse(resultado);
        verify(em).find(Usuario.class, 99L);
        verify(em, never()).merge(any()); // No debe intentar guardar
        verify(transaction, never()).commit(); // No debe cerrar transacción
    }
}
