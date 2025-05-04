package model.factory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.dao.MascotaDAO;
import model.dao.PostulacionDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

public class DAOFactoria implements InterfazDAOFactoria {
    private static final String PERSISTENCE_UNIT = "Pets";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    @Override
    public MascotaDAO obtenerMascotaDAO() {
        return new MascotaDAO(emf.createEntityManager());
    }

    @Override
    public TicketDAO obtenerTicketDAO() {
        return new TicketDAO(emf.createEntityManager());
    }

    @Override
    public UsuarioDAO obtenerUsuarioDAO() {
        return new UsuarioDAO(emf.createEntityManager());
    }

    @Override
    public PostulacionDAO obtenerPostulacionDAO() {
        return new PostulacionDAO(emf.createEntityManager());
    }

}