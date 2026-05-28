package parking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.enums.RolSistema;
import parking.enums.TipoEspacio;
import parking.enums.TipoVehiculo;
import parking.model.EspacioParqueadero;
import parking.model.Tarifa;
import parking.model.UsuarioSistema;
import parking.servicio.Parqueadero;
import parking.ui.controlador.LoginControlador;

import java.io.IOException;

public class AppParkUQ  extends Application {

    private static Parqueadero parqueadero;

    @Override
    public void start(Stage stage) throws IOException {
        parqueadero = inicializarParqueadero();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista/login.fxml"));
        Scene escena = new Scene(loader.load(), 400, 300);

        LoginControlador controlador = loader.getController();
        controlador.setParqueadero(parqueadero);
        controlador.setStage(stage);

        stage.setTitle("ParkUQ - Sistema de gestión de Parqueadero");
        stage.setScene(escena);
        stage.setResizable(false);
        stage.show();
    }

    private Parqueadero inicializarParqueadero() {
        Parqueadero p = new Parqueadero("Parqueadero Universidad del Quindio");

        for (int i = 1; i <= 10; i++) {
            p.agregarEspacio(new EspacioParqueadero("C-" + String.format("%02d", i), TipoEspacio.CARRO));
        }

        for (int i = 1; i <= 10; i++) {
            p.agregarEspacio(new EspacioParqueadero("M-" + String.format("%02d", i), TipoEspacio.MOTO));
        }

        for (int i = 1; i <= 5; i++) {
            p.agregarEspacio(new EspacioParqueadero("B-" + String.format("%02d", i), TipoEspacio.BICICLETA));
        }

        p.agregarTarifa(new Tarifa(TipoVehiculo.CARRO, 3000));
        p.agregarTarifa(new Tarifa(TipoVehiculo.MOTO, 2000));
        p.agregarTarifa(new Tarifa(TipoVehiculo.BICICLETA, 500));

        p.agregarUsuarioSistema(new UsuarioSistema("operador", "1234", RolSistema.OPERADOR));
        p.agregarUsuarioSistema(new UsuarioSistema("admin", "admin", RolSistema.ADMINISTRADOR));

        return p;
    }

    public static Parqueadero getParqueadero() { return parqueadero; }

    public static void main(String[] args) { launch(args); }
}
