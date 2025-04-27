package model.service;

import model.dao.UsuarioDAO;
import model.entities.Usuario;

import java.util.List;

public abstract class UsuarioServ {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean createUsuario(Usuario usuario) {
        try {
            usuarioDAO.create(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }


    public abstract Usuario prepareUsuario(Usuario usuario);
}
