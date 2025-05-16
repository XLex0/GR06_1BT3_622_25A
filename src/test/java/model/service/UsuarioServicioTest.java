package model.service;


import model.entities.Usuario;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioServicioTest {

    @ParameterizedTest
    @CsvSource({
            "'',ApellidoValido",
            "NombreValido,''",
            "'',''"
    })
    void givenUsuario_whenNombreOApellidoVacio_thenLanzaExcepcion(String nombre, String apellido) {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono("0987654321");
        usuario.setEmail("usuario@example.com");
        usuario.setContrasena("PasswordSeguro123");

        ClienteServ cliente = new ClienteServ();

        assertThrows(IllegalArgumentException.class, () -> {
            cliente.actualizarUserServ(usuario);
        });
    }

}
