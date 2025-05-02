package model.factory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.dao.MascotaDAO;
import model.dao.PostulacionDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

public class DAOFactory implements IDAOFactory {
    private static final String PERSISTENCE_UNIT = "Pets";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    @Override
    public MascotaDAO getMascotaDAO() {
        return new MascotaDAO(emf.createEntityManager());
    }

    @Override
    public TicketDAO getTicketDAO() {
        return new TicketDAO(emf.createEntityManager());
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO(emf.createEntityManager());
    }

    @Override
    public PostulacionDAO getPostulacionDAO() {
        return new PostulacionDAO(emf.createEntityManager());
    }

}
