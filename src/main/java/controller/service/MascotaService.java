package controller.service;


import model.dao.MascotaDAO;
import model.entities.Mascota;

import java.util.List;

public class MascotaService {
    private final MascotaDAO mascotaDAO = new MascotaDAO();

    public boolean createPet(String nombre, String raza, Integer edad,
                             Float peso, String comportamiento,
                             String genero, Long usuarioId) {
        try {
            mascotaDAO.create(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear mascota: " + e.getMessage());
            return false;
        }
    }
}
