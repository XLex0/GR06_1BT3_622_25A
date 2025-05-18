package model.service;

import model.dao.UsuarioDAO;
import model.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UsuarioServicioTest {

    @Mock
    UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "'',ApellidoValido",
            "NombreValido,''",
            "'',''"
    })
    void givenUsuario_whenNombreOApellidoVacio_then_false(String nombre, String apellido) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono("0987654321");
        usuario.setEmail("usuario@example.com");
        usuario.setContrasena("PasswordSeguro123");

        ClienteServ cliente = new ClienteServ(); // Aquí debes pasar el DAO si ClienteServ lo requiere
        assertFalse(cliente.actualizarUsuarioDatosServ(usuario));
    }

    @ParameterizedTest
    @CsvSource({
            "'',NombreValido,ApellidoValido",
            "'12345',NombreValido,ApellidoValido",
            "'abcdefghij',NombreValido,ApellidoValido"
    })
    void given_Usuario_when_telefonoincorrecto_then_false(String telefono, String nombre, String apellido) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setEmail("usuario@example.com");
        usuario.setContrasena("PasswordSeguro123");

        ClienteServ cliente = new ClienteServ();
        assertFalse(cliente.actualizarUsuarioDatosServ(usuario));
    }

    @Test
    void given_UsuarioModifiedContraseña_when_differentPassword_then_false() {
        Usuario usuarioOriginal = new Usuario();
        usuarioOriginal.setId(99L);
        usuarioOriginal.setEmail("usuario@example.com");
        usuarioOriginal.setContrasena("PasswordAntigua123");

        String newPassword = "newPassword";

        when(usuarioDAO.findById(99L)).thenReturn(usuarioOriginal);
        ClienteServ cliente = new ClienteServ();

        Usuario viejo = usuarioDAO.findById(99L);
        boolean resultado = cliente.validarContrasena(viejo, newPassword);

        assertFalse(resultado);
        verify(usuarioDAO).findById(99L);
        verify(usuarioDAO, never()).actualizar(any());
    }

    @Test
    void given_UsuarioModified_when_correoNotUsed_then_false(){
        Usuario usuarioCorreoUsado = new Usuario();
        usuarioCorreoUsado.setId(99L);
        usuarioCorreoUsado.setEmail("usuario@example.com");

        Usuario usuarioModificado = new Usuario();
        usuarioModificado.setEmail("usuario@example.com"); // Inválida
        when(usuarioDAO.findByEmail("usuario@example.com")).thenReturn(usuarioCorreoUsado);

        Usuario userUsed = usuarioDAO.findByEmail("usuario@example.com");
        ClienteServ cliente = new ClienteServ();
        boolean resultado = cliente.validarCorreo(userUsed, usuarioModificado);

        assertFalse(resultado);
        verify(usuarioDAO, never()).actualizar(any());
    }

    @Test
    void given_UsuarioModified_when_easyPassword_then_false() {
        Usuario usuarioOriginal = new Usuario();
        usuarioOriginal.setContrasena("newPassword");

        String newPassword = "newPassword";

        when(usuarioDAO.findById(99L)).thenReturn(usuarioOriginal);
        ClienteServ cliente = new ClienteServ();

        Usuario viejo = usuarioDAO.findById(99L);
        boolean resultado = cliente.esContrasenaSegura(newPassword);

        assertTrue(resultado);
        verify(usuarioDAO).findById(99L);
        verify(usuarioDAO, never()).actualizar(any());
    }
}
