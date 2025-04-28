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

    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        return postulacionDAO.listarPostulaciones();
    }

}
