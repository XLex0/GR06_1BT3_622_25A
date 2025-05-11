package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.TicketDAO;
import model.entities.Postulacion;
import model.entities.Ticket;
import model.entities.Usuario;
import model.factory.DAOFactoria;
import model.service.PostulacionServ;

import java.io.IOException;
import java.util.List;

@WebServlet("/PostulacionController")
public class GestionarPostulacion extends HttpServlet {

    private final PostulacionServ postulacionServ = new PostulacionServ();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        router(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        router(request, response);
    }

    private void router(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String route = request.getParameter("route");

        switch (route) {
            case "postular":
                postularTicket(request, response);
                break;
            case "listarPostulaciones":
                listarPostulaciones(request, response);
                break;
            case "actualizarEstadoPostulacion":
                actualizarEstadoPostulacion(request, response);
                break;
            case "listarPostulacionesCliente":
                listarPostulacionesByCliente(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    private void postularTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticketIdStr = request.getParameter("ticketId");
        HttpSession session = request.getSession(false);
        Usuario paseador = (session != null) ? (Usuario) session.getAttribute("user") : null;

        if (ticketIdStr == null || paseador == null) {
            setMensaje(request, false, "Parámetros inválidos o sesión expirada.");
            response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
            return;
        }

        try {
            Long ticketId = Long.parseLong(ticketIdStr);
            TicketDAO ticketDAO = new DAOFactoria().obtenerTicketDAO();
            Ticket ticket = ticketDAO.findById(ticketId);

            if (ticket == null) {
                setMensaje(request, false, "Error: Ticket no encontrado.");
                response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
                return;
            }

            Postulacion postulacion = new Postulacion();
            postulacion.setFecha(new java.sql.Date(System.currentTimeMillis()));
            postulacion.setTicket(ticket);
            postulacion.setUsuario(paseador);

            postulacionServ.registrarPostulacion(postulacion);
            setMensaje(request, true, "¡Postulación exitosa!");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            setMensaje(request, false, "Parámetros inválidos.");
        }

        response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulaciones");
    }

    private void actualizarEstadoPostulacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("postulacionId");
        String accion = request.getParameter("accion");

        if (idStr == null || accion == null) {
            setMensaje(request, false, "Parámetros inválidos.");
            response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulacionesCliente");
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Boolean aprobado = "aceptar".equalsIgnoreCase(accion);
            boolean actualizado = postulacionServ.actualizarEstadoPostulacion(id, aprobado);

            if (!actualizado) {
                setMensaje(request, false, "No se pudo actualizar la postulación.");
            } else {
                setMensaje(request, true, aprobado ? "Postulación aceptada correctamente." : "Postulación rechazada correctamente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            setMensaje(request, false, "Error al actualizar la postulación.");
        }

        response.sendRedirect(request.getContextPath() + "/PostulacionController?route=listarPostulacionesCliente");
    }

    private void listarPostulaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Postulacion> postulaciones = postulacionServ.listarPostulaciones();
        request.setAttribute("postulaciones", postulaciones);
        request.getRequestDispatcher("paseador/PanelPostulaciones.jsp").forward(request, response);
    }

    private void listarPostulacionesByCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario cliente = (Usuario) session.getAttribute("user");

        if (cliente == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        List<Postulacion> postulaciones = postulacionServ.listarPostulacionesPorCliente(cliente.getId());
        request.setAttribute("postulaciones", postulaciones);
        request.getRequestDispatcher("cliente/PanelPostulaciones.jsp").forward(request, response);
    }

    private void setMensaje(HttpServletRequest request, boolean success, String message) {
        request.getSession().setAttribute("success", success);
        request.getSession().setAttribute("message", message);
    }
}
