package model.service;


import model.entities.*;
import model.factory.DAOFactoria;


import java.util.ArrayList;

public class ClienteServ extends UsuarioServ {

    private final DAOFactoria factoria = new DAOFactoria();

    @Override
    public Usuario prepararUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Cliente) {
            usuario.setPostulaciones(new ArrayList<Postulacion>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
         Usuario usuario = factoria.obtenerUsuarioDAO().findByEmail(email);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getContrasena().equals(password)) {
            return null;
        }

        if (usuario.getRol() != Rol.Cliente) {
            return null;
        }

        return usuario;
    }


};

