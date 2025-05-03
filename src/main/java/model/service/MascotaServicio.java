package model.service;

import model.dao.MascotaDAO;
import model.factory.DAOFactoria;

public class MascotaServicio {
    MascotaDAO mascotaDAO = new DAOFactoria().obtenerMascotaDAO();

    public boolean crearMascota(String nombre, String raza, Integer edad,
            Float peso, String comportamiento,
            String genero, Long usuarioId) {
        try {
            mascotaDAO.crear(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear mascota: " + e.getMessage());
            return false;
        }
    }
}
