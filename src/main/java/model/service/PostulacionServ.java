package model.service;

import jakarta.persistence.EntityManager;
import model.dao.PostulacionDAO;
import model.entities.Postulacion;
import model.factory.DAOFactoria;

import java.util.List;

public class PostulacionServ {

    private final DAOFactoria factoria = new DAOFactoria();

    public void registrarPostulacion(Postulacion postulacion) {
        EntityManager em = factoria.crearEntityManager();
        PostulacionDAO dao = new PostulacionDAO(em);

        try {
            dao.registrarPostulacion(postulacion);
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public List<Postulacion> listarPostulaciones() {
        EntityManager em = factoria.crearEntityManager();
        PostulacionDAO dao = new PostulacionDAO(em);

        try {
            return dao.listarPostulaciones();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public List<Postulacion> listarPostulacionesPorCliente(Long clienteId) {
        EntityManager em = factoria.crearEntityManager();
        PostulacionDAO dao = new PostulacionDAO(em);

        try {
            return dao.listarPostulacionesPorCliente(clienteId);
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public boolean actualizarEstadoPostulacion(Long id, Boolean aprobado) {
        EntityManager em = factoria.crearEntityManager();
        PostulacionDAO dao = new PostulacionDAO(em);

        try {
            Postulacion postulacion = dao.findById(id);

            if (postulacion != null && postulacion.getAprobado() == null) {
                postulacion.setAprobado(aprobado);
                dao.update(postulacion);

                if (Boolean.TRUE.equals(aprobado)) {
                    dao.rechazarOtrasPostulaciones(postulacion.getTicket().getId(), postulacion.getId());
                }

                return true;
            }

            return false;
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
