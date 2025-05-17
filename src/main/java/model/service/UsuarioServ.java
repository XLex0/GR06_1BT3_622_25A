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
    public boolean actualizarUsuarioDatosServ(Usuario usuario) {
        if (usuario.getTelefono() == null || !usuario.getTelefono().matches("\\d{10}")) {
            return false;
        }
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean validarContrasena(Usuario usuario, String password) {
        if (usuario.getContrasena() == null ||
                usuario.getContrasena().isEmpty() ||
                !usuario.getContrasena().equals(password)) {
            return false;
        }
        if(password.length() < 6) {
            return false;
        }
        return true;
    }

    public boolean validarCorreo(Usuario usuario) {
        if(usuario!=null) {
            return false;
        }
        return true;
    }




    public abstract Usuario prepararUsuario(Usuario usuario);
    public abstract Usuario ingresar(String email, String password);
}
