package model.service;

import model.dao.UsuarioDAO;
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

    public Usuario ingresar(String email, String password) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario != null) {
            if (usuario.getContrasena().equals(password)) {
                if (usuario.getRol() == Rol.Cliente) {
                    return usuario;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

};

