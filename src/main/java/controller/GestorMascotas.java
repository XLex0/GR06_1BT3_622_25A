package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entities.Usuario;
import model.service.MascotaServicio;

@MultipartConfig
@WebServlet("/mascotas")
public class GestorMascotas extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MascotaServicio petService = new MascotaServicio();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String route = req.getParameter("route");

        switch (route) {
            case "saveNew":
                saveNewPet(req, resp);
                break;
            case "list":
                listarMascotas(req, resp);
                break;
            case "update":
                actualizarMascota(req, resp);
                break;
            case "listarMascotaTicket":
                listarMascotaTicket(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void saveNewPet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean success = false;
        try {
            Long idUser = getIdUserLoged(req);
            if (idUser == null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            String nombre = req.getParameter("nombre");
            String raza = req.getParameter("raza");
            Integer edad = Integer.valueOf(req.getParameter("edad"));
            Float peso = Float.valueOf(req.getParameter("peso"));
            String comportamiento = req.getParameter("comportamiento");
            String genero = req.getParameter("genero");

            success = petService.crearMascota(nombre, raza, edad, peso, comportamiento, genero, idUser);
        } catch (Exception e) {
        }
        req.getSession().setAttribute("success", success);
        resp.sendRedirect(req.getContextPath() + "/mascotas/message.jsp");
    }
    
    private void actualizarMascota(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean success = false;
        try {
            String nombre = req.getParameter("nombre");
            String raza = req.getParameter("raza");
            Integer edad = Integer.valueOf(req.getParameter("edad"));
            Float peso = Float.valueOf(req.getParameter("peso"));
            String comportamiento = req.getParameter("comportamiento");
            String genero = req.getParameter("genero");
            Long id = Long.parseLong(req.getParameter("id"));

            success = petService.actualizarMascota(nombre, raza, edad, peso, comportamiento, genero, id);
        } catch (Exception e) {
        }
        req.getSession().setAttribute("success", success);
        resp.sendRedirect(req.getContextPath() + "/mascotas/message.jsp");
    }

    private Long getIdUserLoged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Usuario user = (Usuario) session.getAttribute("user");

        if (user != null) {
            System.out.println("Usuario logueado: " + user.getId());
            return user.getId();
        } else {
            System.out.println("No hay usuario logueado");
            return null;
        }

    }

    private void listarMascotas(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        listarMascotasConDestino(req, resp, "/mascotas/lista.jsp");
    }

    private void listarMascotaTicket(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        listarMascotasConDestino(req, resp, "/ticket/registro.jsp");
    }

    private void listarMascotasConDestino(HttpServletRequest req, HttpServletResponse resp, String jspDestino)
            throws ServletException, IOException {
        boolean success = false;
        try {
            Long idUser = getIdUserLoged(req);
            if (idUser == null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            req.setAttribute("mascotas", petService.getPetsByUserId(idUser));
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al listar mascotas: " + e.getMessage());
            success = false;
        }

        req.getSession().setAttribute("success", success);
        if (success) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(jspDestino);
            dispatcher.forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al listar mascotas");
        }
    }
}
