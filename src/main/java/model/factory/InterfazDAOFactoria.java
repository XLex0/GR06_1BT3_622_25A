package model.factory;

import model.dao.MascotaDAO;
import model.dao.PostulacionDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

public interface InterfazDAOFactoria {
    UsuarioDAO obtenerUsuarioDAO();

    MascotaDAO obtenerMascotaDAO();

    TicketDAO obtenerTicketDAO();

    PostulacionDAO obtenerPostulacionDAO();
}
