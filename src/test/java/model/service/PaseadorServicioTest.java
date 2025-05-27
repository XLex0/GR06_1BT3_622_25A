package model.service;

import model.dao.UsuarioDAO;
import model.entities.Rol;
import model.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaseadorServicioTest {

    @Mock
    UsuarioDAO usuarioDAO;

    @InjectMocks
    PaseadorServ paseadorServ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paseadorServ = new PaseadorServ(usuarioDAO);
    }

    @Test
    void given_experienciaValida_when_validar_then_true() {
        String experiencia = "3 años paseando perros grandes y tranquilos.";
        assertTrue(paseadorServ.validarExperiencia(experiencia));
    }

    @Test
    void given_experienciaVacia_when_validar_then_false() {
        String experiencia = "";
        assertFalse(paseadorServ.validarExperiencia(experiencia));
    }

    @ParameterizedTest
    @CsvSource({
            "'Tengo experiencia con mascotas pequeñas y grandes.'",
            "'He trabajado con perros ansiosos y adultos mayores durante 2 años.'"
    })
    void given_experienciaParametricaValida_when_validar_then_true(String experiencia) {
        assertTrue(paseadorServ.validarExperiencia(experiencia));
    }

    @Test
    void given_experienciaLarga_when_validar_then_false() {
        String experiencia = "palabra ".repeat(61).trim();
        assertFalse(paseadorServ.validarExperiencia(experiencia));
    }

    @Test
    void given_usuarioValido_when_guardarPerfil_then_guardado() {
        Usuario u = new Usuario("Pedro", "López", "p@gmail.com", "099", "123", Rol.Paseador);
        u.setId(1L);
        u.setExperiencia("Tengo experiencia.");
        u.setDisponible(true);
        when(usuarioDAO.actualizar(u)).thenReturn(true);
        assertTrue(paseadorServ.guardarPerfil(u));
        verify(usuarioDAO, times(1)).actualizar(u);
    }

    @Test
    void given_usuarioInvalido_when_guardarPerfil_then_noSeGuarda() {
        Usuario u = new Usuario("Pedro", "López", "p@gmail.com", "099", "123", Rol.Cliente);
        u.setExperiencia("");
        u.setDisponible(true);
        assertFalse(paseadorServ.guardarPerfil(u));
        verify(usuarioDAO, never()).actualizar(any());
    }

    @Test
    void given_guardadoExitoso_when_generarMensaje_then_ok() {
        String msg = paseadorServ.generarMensaje(true);
        assertEquals("Perfil configurado con éxito.", msg);
    }

    @Test
    void given_guardadoFallido_when_generarMensaje_then_error() {
        String msg = paseadorServ.generarMensaje(false);
        assertEquals("No se pudo guardar el perfil. Verifica los datos.", msg);
    }
}
