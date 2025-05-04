package model.service;

import java.util.ArrayList;

import model.dao.UsuarioDAO;
import model.entities.Postulacion;
import model.entities.Rol;
import model.entities.Usuario;

public class ClienteServ extends UsuarioServ {

    @Override
    public Usuario prepararUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Cliente) {
            usuario.setPostulaciones(new ArrayList<Postulacion>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
        UsuarioDAO usuarioDAO = new DAOFactory().getUsuarioDAO();
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
