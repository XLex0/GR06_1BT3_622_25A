package model.factory;
import  model.entities.Rol;
import model.service.ClienteServ;
import model.service.PaseadorServ;
import model.service.UsuarioServ;

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


