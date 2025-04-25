package model.service;

import model.entities.Ticket;
import model.entities.*;
import java.util.ArrayList;

public class PaseadorServ extends UsuarioServ {

    @Override
    public Usuario prepareUsuario(Usuario usuario) {

        if (usuario.getRol() == Rol.PASEADOR) {
            usuario.setTickets(new ArrayList<Ticket>());
            usuario.setMascotas(new ArrayList<Mascota>());
        }
        return usuario;
    }
}
