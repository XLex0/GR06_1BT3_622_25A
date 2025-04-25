package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entities.*;
import model.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/UsuarioController")
public class GestionarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                listUsers(req, resp);
                break;
            case "create":
                createUser(req, resp);
                break;
            case "find":
                findUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rol = req.getParameter("Rol");

        if (rol != null) {
            if (Objects.equals(rol, Rol.CLIENTE.toString())) {

                List<Usuario> clientes = new ClienteServ().getAllUsuarios();
                req.setAttribute("clientes", clientes);
                req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

            } else if (Objects.equals(rol, Rol.PASEADOR.toString())) {

                List<Usuario> paseadores = new PaseadorServ().getAllUsuarios();
                req.setAttribute("paseadores", paseadores);
                req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

            } else {

                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rol no reconocido.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'Rol' es obligatorio.");
        }
    }


    private void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rol = req.getParameter("Rol");

        if (rol != null) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                try {
                    Long id = Long.parseLong(idStr);
                    Usuario usuario = null;

                    // Dependiendo del rol, buscamos el usuario
                    if (Objects.equals(rol, Rol.CLIENTE.toString())) {
                        usuario = new ClienteServ().findUsuarioById(id); // Llamada para buscar cliente por ID
                        req.setAttribute("cliente", usuario); // Pasamos el cliente a la vista
                    } else if (Objects.equals(rol, Rol.PASEADOR.toString())) {
                        usuario = new PaseadorServ().findUsuarioById(id); // Llamada para buscar paseador por ID
                        req.setAttribute("paseador", usuario); // Pasamos el paseador a la vista
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rol no reconocido.");
                        return; // Salimos si el rol no es válido
                    }

                    req.getRequestDispatcher("jsp/pets.jsp").forward(req, resp);

                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID proporcionado no es válido.");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' es obligatorio.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'Rol' es obligatorio.");
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}





