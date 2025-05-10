package model.service;

import model.entities.Postulacion;
import model.factory.DAOFactoria;

import java.util.List;

public class PostulacionServ {
    private final DAOFactoria factoria = new DAOFactoria();

    // Registrar una nueva postulación
    public void registrarPostulacion(Postulacion postulacion) {
        factoria.obtenerPostulacionDAO().registrarPostulacion(postulacion);
    }

    // Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        return factoria.obtenerPostulacionDAO().listarPostulaciones();
    }

    public void aceptarPostulacion(Long id) {
        factoria.obtenerPostulacionDAO().aceptarPostulacion(id);
    }

    public List<Postulacion> listarPostulacionesPorCliente(Long clienteId) {
        return factoria.obtenerPostulacionDAO().listarPostulacionesPorCliente(clienteId);
    }
}
