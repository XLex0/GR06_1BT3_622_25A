package model.service;

import model.entities.Usuario;
import model.factory.DAOFactoria;


public abstract class UsuarioServ {
    private final DAOFactoria factoria = new DAOFactoria();

    public boolean crearUsuario(Usuario usuario) {
        try {
            factoria.obtenerUsuarioDAO().create(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }


    public abstract Usuario prepararUsuario(Usuario usuario);
    public abstract Usuario ingresar(String email, String password);
}
