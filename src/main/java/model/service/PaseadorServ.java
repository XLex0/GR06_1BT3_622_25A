package model.service;

import model.dao.UsuarioDAO;
import model.entities.*;
import model.factory.DAOFactoria;

import java.util.ArrayList;

public class PaseadorServ extends UsuarioServ {
    private DAOFactoria factoria;
    private UsuarioDAO usuarioDAO;

    // Constructor por defecto (producción)
    public PaseadorServ() {
        this.factoria = new DAOFactoria();
        this.usuarioDAO = factoria.obtenerUsuarioDAO();
    }

    // Constructor para test (inyección de DAO simulado)
    public PaseadorServ(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public Usuario prepararUsuario(Usuario usuario) {
        if (usuario.getRol() == Rol.Paseador) {
            usuario.setTickets(new ArrayList<>());
            usuario.setMascotas(new ArrayList<>());
        }
        return usuario;
    }

    public Usuario ingresar(String email, String password) {
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null || !usuario.getContrasena().equals(password) || usuario.getRol() != Rol.Paseador) {
            return null;
        }
        return usuario;
    }

    public boolean validarExperiencia(String experiencia) {
        if (experiencia == null || experiencia.trim().isEmpty()) return false;
        String[] palabras = experiencia.trim().split("\\s+");
        return palabras.length <= 60;
    }

    public boolean guardarPerfil(Usuario paseador) {
        if (!esPaseadorValido(paseador)) return false;
        if (!validarExperiencia(paseador.getExperiencia())) return false;
        return usuarioDAO.actualizar(paseador);
    }

    private boolean esPaseadorValido(Usuario u) {
        return u != null && u.getRol() == Rol.Paseador;
    }

    public String generarMensaje(boolean exito) {
        return exito ? "Perfil configurado con éxito." : "No se pudo guardar el perfil. Verifica los datos.";
    }
}
