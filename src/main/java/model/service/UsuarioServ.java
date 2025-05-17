package model.service;

import model.dao.UsuarioDAO;
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
        System.out.println("llego aqui");
         return factoria.obtenerUsuarioDAO().actualizar(usuario);
    }

    public boolean actualizarUsuarioCredencialesServ(Usuario usuario, String credenciales) {
        UsuarioDAO dao = factoria.obtenerUsuarioDAO();
        Usuario usuarioViejo=dao.findById(usuario.getId());
        if(!validarContrasena(usuarioViejo, credenciales)){
            return false;
        }
        if(!seguridadContrasena(usuario.getContrasena())){
            return false;
        }
        if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return false;
        }
        Usuario user = dao.findByEmail(usuario.getEmail());

        if (!validarCorreo(user, usuario)){
            return false;
        }

        return factoria.obtenerUsuarioDAO().actualizar(usuario);

    }

    public boolean validarContrasena(Usuario usuarioViejo, String password) {
        return usuarioViejo.getContrasena().equals(password);
    }

    public boolean seguridadContrasena(String credenciales) {
        return credenciales.length()>6;
    }

    public boolean validarCorreo(Usuario usuarioViejo, Usuario usuario) {
        if (usuarioViejo.getEmail().equals(usuario.getEmail())&&usuarioViejo.getId().equals(usuario.getId())) {
            return true;
        }
        return false;
    }


    public abstract Usuario prepararUsuario(Usuario usuario);
    public abstract Usuario ingresar(String email, String password);
}
