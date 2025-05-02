package model.service;

import java.util.ArrayList;

import model.dao.UsuarioDAO;
import model.entities.Mascota;
import model.entities.Rol;
import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactory;

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
        UsuarioDAO usuarioDAO = new DAOFactory().getUsuarioDAO();
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
}
