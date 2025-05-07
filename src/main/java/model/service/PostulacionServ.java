package model.service;

import model.dao.PostulacionDAO;
import model.entities.Postulacion;
import model.factory.DAOFactoria;

import java.util.List;

public class PostulacionServ {
    private PostulacionDAO postulacionDAO = new DAOFactoria().obtenerPostulacionDAO();

    // Registrar una nueva postulación
    public void registrarPostulacion(Postulacion postulacion) {
        postulacionDAO.registrarPostulacion(postulacion);
    }

    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        return postulacionDAO.listarPostulaciones();
    }

    public void aceptarPostulacion(Long id) {
        postulacionDAO.aceptarPostulacion(id);
    }

    public List<Postulacion> listarPostulacionesPorCliente(Long clienteId) {
        return postulacionDAO.listarPostulacionesPorCliente(clienteId);
    }
}
