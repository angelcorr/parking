package parkUQ.model;

import parkUQ.enums.RolSistema;

public class UsuarioSistema {
    private String nombreUsuario;
    private String contrasena;
    private RolSistema rol;

    public UsuarioSistema(String nombreUsuario, String contrasena, RolSistema rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public RolSistema getRol() {
        return rol;
    }
    public void setRol(RolSistema rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return nombreUsuario + " [" + rol + "]";
    }
}
