package model.service;

import model.dao.UsuarioDAO;
import model.entities.Usuario;
import model.factory.DAOFactory;

public abstract class UsuarioServ {
    private final UsuarioDAO usuarioDAO = new DAOFactory().getUsuarioDAO();

    public boolean crearUsuario(Usuario usuario) {
        try {
            usuarioDAO.create(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public abstract Usuario prepararUsuario(Usuario usuario);
    public abstract Usuario ingresar(String email, String password);
}
