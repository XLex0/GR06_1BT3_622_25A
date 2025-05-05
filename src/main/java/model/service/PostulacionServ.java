package model.service;

import model.dao.PostulacionDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;
import model.entities.Postulacion;
import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactoria;

import java.util.List;

public class PostulacionServ {
    private PostulacionDAO postulacionDAO = new DAOFactoria().obtenerPostulacionDAO();
    private TicketDAO ticketDAO = new DAOFactoria().obtenerTicketDAO();
    private UsuarioDAO usuarioDAO = new DAOFactoria().obtenerUsuarioDAO();

    // Método refactorizado que maneja la lógica de negocio
    public boolean registrarPostulacion(Long ticketId, Long paseadorId) {
        try {
            Ticket ticket = ticketDAO.findById(ticketId);
            Usuario paseador = usuarioDAO.findById(paseadorId);

            if (ticket != null && paseador != null) {
                Postulacion postulacion = new Postulacion();
                postulacion.setFecha(new java.sql.Date(System.currentTimeMillis()).toString());
                postulacion.setTicket(ticket);
                postulacion.setUsuario(paseador);
                postulacion.setAprobado(false);

                postulacionDAO.registrarPostulacion(postulacion);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
