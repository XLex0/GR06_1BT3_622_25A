package model.service;

import java.util.List;

import model.entities.Mascota;
import model.factory.DAOFactoria;

public class MascotaServicio {
    private final DAOFactoria factoria = new DAOFactoria();

    public boolean crearMascota(String nombre, String raza, Integer edad,
                                Float peso, String comportamiento, String genero, Long usuarioId) {

        try {
            factoria.obtenerMascotaDAO().crear(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear mascota: " + e.getMessage());
            return false;
        }
    }

    public List<Mascota> getAllPets() {
        return factoria.obtenerMascotaDAO().findAll();
    }

    public List<Mascota> getPetsByUserId(Long id) {
        return factoria.obtenerMascotaDAO().buscarTodosPorUsuarioId(id);
    }

    public boolean actualizarMascota(String nombre, String raza, Integer edad,
            Float peso, String comportamiento, String genero, Long id) {
        try {
            factoria.obtenerMascotaDAO().actualizar(nombre, raza, edad, peso, comportamiento, genero, id);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar mascota: " + e.getMessage());
            return false;
        }
    }
}
