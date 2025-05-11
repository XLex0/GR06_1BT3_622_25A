package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.entities.Mascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MascotaDAOTest {

    @Mock
    EntityManager em;

    @Mock
    EntityTransaction transaction;

    @InjectMocks
    MascotaDAO mascotaDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(em.getTransaction()).thenReturn(transaction);
    }


    @Test
    void given_mascotaNoExiste_when_actualizar_then_rechazaOperacion() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 99L)).thenReturn(null); // Mascota no existe

        // Act
        boolean resultado = dao.actualizar("Rocky", "Boxer", 5,
                30f, "Activo", "Macho", 99L);

        // Assert
        assertFalse(resultado);
        verify(em, times(1)).find(Mascota.class, 99L);
        verify(em, never()).merge(any());
    }


    @Test
    void given_mascotaInactiva_when_actualizar_then_rechazaOperacion() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        Mascota mascotaInactiva = new Mascota();
        mascotaInactiva.setId(15L);
        mascotaInactiva.setEstado(false); // estado inactivo

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 15L)).thenReturn(mascotaInactiva);

        // Act
        boolean resultado = dao.actualizar("Bobby", "Terrier", 5, 18f, "Tímido", "Macho", 15L);

        // Assert
        assertFalse(resultado);
        verify(em, times(1)).find(Mascota.class, 15L);
        verify(em, never()).merge(any()); // No se debe intentar guardar
    }


    @Test
    void given_mascotaNoExiste_when_inactivar_then_retornaFalse() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 99L)).thenReturn(null); // Mascota no existe

        // Act
        boolean resultado = dao.inactivarMascota(99L);

        // Assert
        assertFalse(resultado);
        verify(em).find(Mascota.class, 99L);
        verify(em, never()).merge(any()); // No debe intentar guardar
    }

    @Test
    void given_mascotaInactiva_when_inactivar_then_retornaFalse() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        Mascota mascota = new Mascota();
        mascota.setId(42L);
        mascota.setEstado(false); // está inactiva

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 42L)).thenReturn(mascota);

        boolean resultado = dao.inactivarMascota(42L);

        // Assert
        assertFalse(resultado);
        verify(em).find(Mascota.class, 42L);
        verify(em, never()).merge(any());
    }

    @Test
    void given_mascotaActiva_when_inactivar_then_estadoCambiaYRetornaTrue() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        Mascota mascota = new Mascota();
        mascota.setId(7L);
        mascota.setEstado(true); // ✅ Activa

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 7L)).thenReturn(mascota);

        // Act
        boolean resultado = dao.inactivarMascota(7L);

        // Assert
        assertTrue(resultado);
        assertFalse(mascota.isEstado());
        verify(em).merge(mascota);
    }

    @Test
    void given_mascotaActivaConDatosValidos_when_actualizar_then_modificacionExitosa() {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        MascotaDAO dao = new MascotaDAO(em);

        Mascota mascota = new Mascota();
        mascota.setId(10L);
        mascota.setEstado(true); // ✅ Activa

        when(em.getTransaction()).thenReturn(tx);
        when(em.find(Mascota.class, 10L)).thenReturn(mascota);

        // Act
        boolean resultado = dao.actualizar(
                "Firulais", "Labrador", 5, 30f,
                "Muy activo", "Macho", 10L
        );

        // Assert
        assertTrue(resultado);
        verify(em).merge(mascota);     // Se guardan los cambios
        verify(tx).commit();           // Transacción finaliza
    }




}


