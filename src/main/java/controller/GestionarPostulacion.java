package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Postulacion;
import model.service.PostulacionServ;

@WebServlet("/PostulacionController")
public class GestionarPostulacion extends HttpServlet {

    private PostulacionServ postulacionServ = new PostulacionServ();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        router(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    private void postularTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean success = false;
        try {
            Long ticketId = Long.parseLong(request.getParameter("ticketId"));
            HttpSession session = request.getSession();
            Long paseadorId = (Long) session.getAttribute("idUsuario");

            if (paseadorId != null) {
                // Delegar la creación y lógica al servicio
                success = postulacionServ.registrarPostulacion(ticketId, paseadorId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("success", success);
        if (success) {
            response.sendRedirect("paseador/PanelPostulador.jsp?mensaje=PostulacionExitosa");
        } else {
            response.sendRedirect("paseador/PanelPostulador.jsp?mensaje=ErrorPostulacion");
        }
    }

    private void listarPostulaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lógica para listar las postulaciones (si fuera necesario)
        request.setAttribute("postulaciones", postulacionServ.listarPostulaciones());
        request.getRequestDispatcher("paseador/listarPostulaciones.jsp").forward(request, response);
    }
}
