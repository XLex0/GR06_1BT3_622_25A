package model.factory;

import model.dao.MascotaDAO;
import model.dao.PostulacionDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

public interface IDAOFactory {

    UsuarioDAO getUsuarioDAO();

    MascotaDAO getMascotaDAO();

    TicketDAO getTicketDAO();

    PostulacionDAO getPostulacionDAO();

}
