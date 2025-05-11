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

        if (!esNombreValido(nombre)) return false;
        if (!esRazaValida(raza)) return false;
        if (!esEdadValida(edad)) return false;
        if (!esPesoValido(peso)) return false;

        return  factoria.obtenerMascotaDAO().actualizar(nombre, raza, edad, peso, comportamiento, genero, id);
    }
    public boolean inactivarMascota(Long id) {
        return factoria.obtenerMascotaDAO().inactivarMascota(id);
    }

    private boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    private boolean esRazaValida(String raza) {
        return raza != null && !raza.trim().isEmpty();
    }

    private boolean esEdadValida(Integer edad) {
        return edad != null && edad > 0 && edad <= 25;
    }

    private boolean esPesoValido(Float peso) {
        return peso != null && peso > 0 && peso <= 200;
    }



}
