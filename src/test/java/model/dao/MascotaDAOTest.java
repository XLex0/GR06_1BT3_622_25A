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
    void testActualizarMascotaExistente() {
        Long mascotaId = 1L;

        Mascota mascotaExistente = new Mascota();
        mascotaExistente.setId(mascotaId); // Suponiendo que tiene método setId
        mascotaExistente.setNombre("Firulais");

        when(em.find(Mascota.class, mascotaId)).thenReturn(mascotaExistente);

        Boolean resultado = mascotaDAO.actualizar(
                "Max", "Labrador", 5, 22.0f,
                "Tranquilo", "Macho", mascotaId
        );

        // Verifica que se llamó la transacción y se actualizó la mascota
        verify(transaction).begin();
        verify(em).merge(mascotaExistente);
        verify(transaction).commit();
        verify(em).close();

        assertTrue(resultado);
        assertEquals("Max", mascotaExistente.getNombre());
    }

    @Test
    void testActualizarMascotaNoExistente() {
        Long mascotaId = 2L;
        when(em.find(Mascota.class, mascotaId)).thenReturn(null);

        Boolean resultado = mascotaDAO.actualizar(
                "Rocky", "Bulldog", 3, 18.0f,
                "Agresivo", "Macho", mascotaId
        );


        assertFalse(resultado); // sigue retornando true porque no lanza excepción
    }

}
