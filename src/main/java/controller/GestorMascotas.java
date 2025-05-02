package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.MascotaService;

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
            case "saveNew":
                saveNewPet(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void saveNewPet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean success = false;
        try {
            String nombre = req.getParameter("nombre");
            String raza = req.getParameter("raza");
            Integer edad = Integer.valueOf(req.getParameter("edad"));
            Float peso = Float.valueOf(req.getParameter("peso"));
            String comportamiento = req.getParameter("comportamiento");
            String genero = req.getParameter("genero");
            Long usuarioId = Long.parseLong(req.getParameter("usuario"));

            success = petService.createPet(nombre, raza, edad, peso, comportamiento, genero, usuarioId);
        } catch (Exception e) {
        }
        req.getSession().setAttribute("success", success);
        resp.sendRedirect(req.getContextPath() + "/mascotas/message.jsp");
    }

}
