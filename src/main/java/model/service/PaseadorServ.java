package model.service;

import model.dao.UsuarioDAO;
import model.entities.Ticket;
import model.entities.*;
import java.util.ArrayList;

public class PaseadorServ extends UsuarioServ {

    @Override
    public Usuario prepareUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Paseador) {
            usuario.setTickets(new ArrayList<Ticket>());
            usuario.setMascotas(new ArrayList<Mascota>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario != null) {
            if (usuario.getContrasena().equals(password)) {
                if (usuario.getRol() == Rol.Paseador) {
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
}
