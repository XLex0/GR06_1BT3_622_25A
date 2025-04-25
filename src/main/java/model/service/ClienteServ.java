package model.service;

import model.entities.*;

import java.util.ArrayList;

public class ClienteServ extends UsuarioServ {

    @Override
    public Usuario prepareUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Cliente) {
            usuario.setPostulaciones(new ArrayList<Postulacion>());
        }
        return usuario;
    }
}
