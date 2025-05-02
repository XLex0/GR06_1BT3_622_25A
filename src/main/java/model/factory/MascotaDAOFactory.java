package model.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MascotaDAOFactory implements DAOFactory {
    private static final String PERSISTENCE_UNIT = "Pets";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    @Override
    public EntityManager getDAO() {
        return emf.createEntityManager();
    }
}
