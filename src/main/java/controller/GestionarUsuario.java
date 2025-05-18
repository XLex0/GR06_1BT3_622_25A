package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entities.*;
import model.factory.UsuarioServFactory;
import model.service.*;

import java.io.IOException;

@WebServlet("/UsuarioController")
public class GestionarUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        enrutador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        enrutador(req, resp);
    }

    private void enrutador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = req.getParameter("route");

        switch (ruta) {

            case "create":
                crearUsuario(req, resp);
                break;
            case "datos":
                editarDatos(req,resp);
                break;
            case "credenciales":
                editarCredenciales(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void editarDatos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean exito = false;
        String mensaje = "";

        HttpSession sesion = req.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("user");

        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String telefono = req.getParameter("telefono");

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);

        try {
            UsuarioServ serv;
            if (usuario.getRol().equals(Rol.Paseador)) {
                serv = new PaseadorServ();
            } else {
                serv = new ClienteServ();
            }

            exito = serv.actualizarUsuarioDatosServ(usuario);
            mensaje = exito ? "Datos actualizados correctamente." : "Error: datos inválidos.";

            if (exito) {
                sesion.setAttribute("user", usuario);
            }

        } catch (Exception e) {
            mensaje = "Ocurrió un error al actualizar los datos.";
        }

        sesion.setAttribute("successM", exito);
        sesion.setAttribute("messageM", mensaje);
        resp.sendRedirect(req.getContextPath() + "/cliente/inicio.jsp");
    }


    private void editarCredenciales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean exito = false;
        String mensaje = "";

        HttpSession sesion = req.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("user");

        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String lastPassword = req.getParameter("lastPassword");

        usuario.setContrasena(password);
        usuario.setEmail(email);

        try {
            UsuarioServ serv;
            if (usuario.getRol().equals(Rol.Paseador)) {
                serv = new PaseadorServ();
            } else {
                serv = new ClienteServ();
            }

            exito = serv.actualizarUsuarioCredencialesServ(usuario, lastPassword);
            mensaje = exito ? "Credenciales actualizadas correctamente." : "Error: contraseña actual incorrecta o correo en uso.";

            // Si fue exitoso, puedes actualizar la sesión
            if (exito) {
                sesion.setAttribute("user", usuario);
            }

        } catch (Exception e) {
            mensaje = "Ocurrió un error al actualizar las credenciales.";
        }

        sesion.setAttribute("successM", exito);
        sesion.setAttribute("messageM", mensaje);
        resp.sendRedirect(req.getContextPath() + "/cliente/inicio.jsp");
    }

    private void crearUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean exito = false;
        String mensaje = "";

        try {
            Usuario nuevoUsuario = obtenerUsuario(req);

            // Usamos la factory para obtener el servicio adecuado
            UsuarioServ usuarioServ = UsuarioServFactory.getServicio(nuevoUsuario.getRol());
            Usuario usuarioPreparado = usuarioServ.prepararUsuario(nuevoUsuario);
            exito = usuarioServ.crearUsuario(usuarioPreparado);

            mensaje = exito ? "¡Usuario creado con éxito!" : "Error al crear el usuario.";

        } catch (Exception e) {
            exito = false;
            mensaje = "Ocurrió un error: " + e.getMessage();
        }

        HttpSession sesion = req.getSession();
        sesion.setAttribute("success", exito);
        sesion.setAttribute("message", mensaje);

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }


    private Usuario obtenerUsuario(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String contrasena = req.getParameter("contrasena");
        String rolStr = req.getParameter("rol");
        Rol rol = Rol.valueOf(rolStr);
        return new Usuario(nombre, apellido, email, telefono, contrasena, rol);
    }



}