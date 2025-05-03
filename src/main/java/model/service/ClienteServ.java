package model.service;

import java.util.ArrayList;

import model.dao.UsuarioDAO;
import model.entities.Postulacion;
import model.entities.Rol;
import model.entities.Usuario;
import model.factory.DAOFactoria;

public class ClienteServ extends UsuarioServ {

    @Override
    public Usuario prepareUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Cliente) {
            usuario.setPostulaciones(new ArrayList<Postulacion>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
        UsuarioDAO usuarioDAO = new DAOFactoria().obtenerUsuarioDAO();
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
