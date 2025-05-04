package model.service;

import model.dao.MascotaDAO;
import model.dao.UsuarioDAO;
import model.entities.*;
import model.factory.MascotaDAOFactory;

import java.util.ArrayList;

public class ClienteServ extends UsuarioServ {


    @Override
    public Usuario prepararUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Cliente) {
            usuario.setPostulaciones(new ArrayList<Postulacion>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(new MascotaDAOFactory().getDAO());
        Usuario usuario = usuarioDAO.findByEmail(email);

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

