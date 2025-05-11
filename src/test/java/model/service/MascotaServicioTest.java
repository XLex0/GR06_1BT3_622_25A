package model.service;

import model.dao.MascotaDAO;
import model.entities.Mascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MascotaServicioTest {

    @Mock
    MascotaDAO mascotaDAO;

    @InjectMocks
    MascotaServicio mascotaServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_user_id_does_not_exist_when_request_pet_list_then_empty() {
        Long userId = 99L;
        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(Collections.emptyList());

        List<Mascota> mascotas = mascotaServicio.getPetsByUserId(userId);

        assertNotNull(mascotas);
        assertTrue(mascotas.isEmpty());
    }

    @Test
    void given_user_exists_but_has_no_pets_when_request_pet_list_then_empty() {

        Long userId = 2L;
        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(Collections.emptyList());
        List<Mascota> mascotas = mascotaServicio.getPetsByUserId(userId);

        assertNotNull(mascotas);
        assertTrue(mascotas.isEmpty());
    }

    @Test
    void given_user_exists_and_has_pets_when_request_pet_list_then_return_pets() {

        Long userId = 3L;
        Mascota mascota1 = new Mascota("Firulais", "Labrador",
                5, 15.5f, "Amigable", "Macho", userId);
        Mascota mascota2 = new Mascota("Rocky", "Bulldog",
                3, 12.2f, "Protector", "Macho", userId);
        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);

        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(mascotas);

        List<Mascota> resultado = mascotaServicio.getPetsByUserId(userId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void given_user_has_pets_when_request_pet_list_then_verify_pet_structure() {
        Long userId = 3L;
        Mascota mascota = new Mascota("Fido", "Beagle",
                4, 12.5f, "Activo", "Macho", userId);
        List<Mascota> mascotas = Arrays.asList(mascota);
        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(mascotas);
        List<Mascota> resultado = mascotaServicio.getPetsByUserId(userId);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        Mascota m = resultado.get(0);
        assertNotNull(m.getId());
        assertNotNull(m.getNombre());
        assertNotNull(m.getRaza());
        assertNotNull(m.getEdad());
        assertNotNull(m.getPeso());
    }
}
