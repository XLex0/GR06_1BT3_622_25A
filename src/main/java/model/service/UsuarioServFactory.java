package model.service;
import  model.entities.Rol;

public class UsuarioServFactory {

        public static UsuarioServ getServicio(Rol rol) {
            switch (rol) {
                case Cliente:
                    return new ClienteServ();
                case Paseador:
                    return new PaseadorServ();
                default:
                    throw new IllegalArgumentException("Rol no soportado: " + rol);
            }
        }
}


