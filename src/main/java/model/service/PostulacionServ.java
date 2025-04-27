package model.service;

import model.dao.PostulacionDAO;
import model.entities.Postulacion;

import java.util.List;

public class PostulacionServ {
    private PostulacionDAO postulacionDAO = new PostulacionDAO();

    // Registrar una nueva postulación
    public void registrarPostulacion(Postulacion postulacion) {
        postulacionDAO.registrarPostulacion(postulacion);
    }

    // Eliminar una postulación por ID
    public void eliminarPostulacion(Long id) {
        postulacionDAO.eliminarPostulacion(id);
    }

    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        return postulacionDAO.listarPostulaciones();
    }

    // Buscar una postulación específica
    public Postulacion listarPostulacion(Long id) {
        return postulacionDAO.listarPostulacion(id);
    }
}
