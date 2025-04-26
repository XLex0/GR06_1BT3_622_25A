package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import controllers.service.MascotaService;

import java.io.IOException;

@WebServlet("/mascotas")
public class GestorMascotas extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MascotaService petService = new MascotaService();

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
            case "list":
                listPets(req, resp);
                break;
            case "saveNew":
                saveNewPet(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void listPets(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pets", petService.getAllPets());
        req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);
    }

    private void saveNewPet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String nombre = req.getParameter("nombre");
            String raza = req.getParameter("raza");
            Integer edad = Integer.valueOf(req.getParameter("edad"));
            Float peso = Float.valueOf(req.getParameter("peso"));
            String comportamiento = req.getParameter("comportamiento");
            String genero = req.getParameter("genero");
            Long usuarioId = Long.parseLong(req.getParameter("usuario"));

            boolean success = petService.createPet(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
            setFlash(req, success, "Mascota registrada con éxito", "Error al registrar la mascota.");
        } catch (Exception e) {
            setFlash(req, false, "", "Error al procesar la solicitud.");
        }
        resp.sendRedirect("GestionarMascotaController?route=list");
    }


    private void setFlash(HttpServletRequest req, boolean success, String ok, String fail) {
        HttpSession session = req.getSession();
        if (success) {
            session.setAttribute("messageType", "success");
            session.setAttribute("message", ok);
        } else {
            session.setAttribute("messageType", "error");
            session.setAttribute("message", fail);
        }
    }
}
