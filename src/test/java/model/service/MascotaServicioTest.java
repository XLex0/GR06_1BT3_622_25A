package model.service;

import model.dao.MascotaDAO;
import model.entities.Mascota;
import model.service.MascotaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MascotaServicioTest {

    @Mock
    MascotaDAO mascotaDAO;

    @InjectMocks
    MascotaServicio mascotaServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "0", "26"
    })
    void given_mascotaActiva_when_edadInvalida_then_actualizacionRechazada(int edad) {
        boolean resultado = mascotaServicio.actualizarMascota(
                "Toby", "Beagle", edad, 10f, "Activo", "Macho", 1L
        );
        assertFalse(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "-5", "0", "250"
    })
    void given_mascotaActiva_when_pesoInvalido_then_actualizacionRechazada(float peso) {
        boolean resultado = mascotaServicio.actualizarMascota(
                "Luna", "Golden", 5, peso, "Tranquila", "Hembra", 1L
        );
        assertFalse(resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "'', Labrador", "Rocky, ''", "null, Labrador", "Rocky, null"
    })
    void given_camposObligatoriosInvalidos_when_actualizarMascota_then_rechaza(String nombre, String raza) {
        boolean resultado = mascotaServicio.actualizarMascota(
                "null".equals(nombre) ? null : nombre,
                "null".equals(raza) ? null : raza,
                5, 20f, "Sociable", "Macho", 1L
        );
        assertFalse(resultado);
    }

//    @Test
//    void given_user_id_does_not_exist_when_request_pet_list_then_empty() {
//        Long userId = 99L;
//        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(Collections.emptyList());
//        List<Mascota> mascotas = mascotaServicio.getPetsByUserId(userId);
//        assertNotNull(mascotas);
//        assertTrue(mascotas.isEmpty());
//    }
//
//    @Test
//    void given_user_exists_but_has_no_pets_when_request_pet_list_then_empty() {
//        Long userId = 2L;
//        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(Collections.emptyList());
//        List<Mascota> mascotas = mascotaServicio.getPetsByUserId(userId);
//        assertNotNull(mascotas);
//        assertTrue(mascotas.isEmpty());
//    }
//
//    @Test
//    void given_user_exists_and_has_pets_when_request_pet_list_then_return_pets() {
//        Long userId = 3L;
//        Mascota mascota1 = new Mascota("Firulais", "Labrador", 5, 15.5f, "Amigable", "Macho", userId);
//        Mascota mascota2 = new Mascota("Rocky", "Bulldog", 3, 12.2f, "Protector", "Macho", userId);
//        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(Arrays.asList(mascota1, mascota2));
//
//        List<Mascota> resultado = mascotaServicio.getPetsByUserId(userId);
//        assertNotNull(resultado);
//        assertEquals(2, resultado.size());
//    }
//
//    @Test
//    void given_user_has_pets_when_request_pet_list_then_verify_pet_structure() {
//        Long userId = 3L;
//        Mascota mascota = new Mascota("Fido", "Beagle", 4, 12.5f, "Activo", "Macho", userId);
//        when(mascotaDAO.buscarTodosPorUsuarioId(userId)).thenReturn(List.of(mascota));
//
//        List<Mascota> resultado = mascotaServicio.getPetsByUserId(userId);
//        assertNotNull(resultado);
//        assertFalse(resultado.isEmpty());
//        Mascota m = resultado.get(0);
//        assertNotNull(m.getNombre());
//        assertNotNull(m.getRaza());
//        assertNotNull(m.getEdad());
//        assertNotNull(m.getPeso());
//    }
}
