package model.service;

import model.entities.Ticket;
import model.entities.*;
import model.factory.DAOFactoria;


import java.util.ArrayList;

public class PaseadorServ extends UsuarioServ {
    private final DAOFactoria factoria = new DAOFactoria();

    @Override
    public Usuario prepararUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.Paseador) {
            usuario.setTickets(new ArrayList<Ticket>());
            usuario.setMascotas(new ArrayList<Mascota>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
         Usuario usuario = factoria.obtenerUsuarioDAO().findByEmail(email);
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
