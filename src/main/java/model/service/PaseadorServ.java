package model.service;

import java.util.ArrayList;

import model.dao.UsuarioDAO;
import model.entities.Mascota;
import model.entities.Rol;
import model.entities.Ticket;
import model.entities.Usuario;

public class PaseadorServ extends UsuarioServ {

    @Override
    public Usuario prepararUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Paseador) {
            usuario.setTickets(new ArrayList<Ticket>());
            usuario.setMascotas(new ArrayList<Mascota>());
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
        if (usuario.getRol() != Rol.Paseador) {
            return null;
        }
        return usuario;
    }

}
