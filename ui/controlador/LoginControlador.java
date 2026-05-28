package parking.ui.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parking.enums.RolSistema;
import parking.model.UsuarioSistema;
import parking.servicio.Parqueadero;

import java.awt.*;
import java.io.IOException;

public class LoginControlador {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoContrasena;
    @FXML private Label etiquetaError;

    private Parqueadero parqueadero;
    private Stage stage;

    @FXML
    public void iniciarSesion() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = campoContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            etiquetaError.setText("Por favor ingrese usuario y contraseña.");
            return;
        }

        UsuarioSistema usuarioSistema = parqueadero.autentificarUsuarioSistema(usuario, contrasena);

        if (usuarioSistema == null) {
            etiquetaError.setText("Usuario o contraseña incorrectos.");
            campoContrasena.clear();
            return;
        }

        abrirVistaPrincipal(usuarioSistema);
    }

    private void abrirVistaPrincipal(UsuarioSistema usuarioSistema) {
        try {
            String vistaArchivo;
            if (usuarioSistema.getRol() == RolSistema.ADMINISTRADOR) {
                vistaArchivo = "admin.fxml";
            } else {
                vistaArchivo = "operador.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/" + vistaArchivo));
            Scene escena = new Scene(loader.load(), 800, 600);

            if (usuarioSistema.getRol() == RolSistema.ADMINISTRADOR) {
                AdminControlador controlador = loader.getController();
                controlador.setParqueadero(parqueadero);
                controlador.setStage(stage);
                controlador.inicializar();
            } else {
                OperadorControlador controlador = loader.getController();
                controlador.setParqueadero(parqueadero);
                controlador.setStage(stage);
                controlador.inicializar();
            }

            stage.setTitle("Park UQ - " + usuarioSistema.getRol());
            stage.setScene(escena);
            stage.setResizable(true);
        } catch (IOException e) {
            etiquetaError.setText("Error al cargar la interfaz: " + e.getMessage());
        }
    }

    public void setParqueadero(Parqueadero parqueadero) { this.parqueadero = parqueadero; }

    public void setStage(Stage stage) { this.stage = stage; }

}
