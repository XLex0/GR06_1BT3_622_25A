package model.factory;

import jakarta.persistence.EntityManager;

public interface DAOFactory {
    public EntityManager getDAO();
}
