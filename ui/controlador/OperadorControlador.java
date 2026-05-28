package parking.ui.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import parking.excepcion.ParkUQException;
import parking.model.Bicicleta;
import parking.model.Carro;
import parking.model.EspacioParqueadero;
import parking.model.Motocicleta;
import parking.model.RegistroSalida;
import parking.model.Vehiculo;
import parking.servicio.GestorReportes;
import parking.servicio.Parqueadero;

public class OperadorControlador {

  // Pestaña: Ingreso
  @FXML private TextField campoPlacaIngreso;
  @FXML private ComboBox<String> combotipoVehiculo;
  @FXML private TextField campoNombreConductor;
  @FXML private TextField campoIdConductor;
  @FXML private TextField campoCilindraje;
  @FXML private Label etiquetaResultadoIngreso;

  // Pestaña: Salida
  @FXML private TextField campoPlacaSalida;
  @FXML private TextArea areaRecibo;

  // Pestaña: Consultas
  @FXML private TableView<Vehiculo> tablaVehiculos;
  @FXML private TableColumn<Vehiculo, String> colPlaca;
  @FXML private TableColumn<Vehiculo, String> colTipo;
  @FXML private TableColumn<Vehiculo, String> colConductor;
  @FXML private TableColumn<Vehiculo, String> colEspacio;
  @FXML private TableColumn<Vehiculo, String> colHoraIngreso;
  @FXML private Label etiquetaEspaciosDisponibles;

  // Pestaña: Reportes
  @FXML private TextArea areaReporte;

  private Parqueadero parqueadero;
  private Stage stage;
  private GestorReportes gestorReportes;

  public void inicializar() {
    gestorReportes = new GestorReportes(parqueadero);
    combotipoVehiculo.setItems(FXCollections.observableArrayList("CARRO", "MOTOCICLETA", "BICICLETA"));
    combotipoVehiculo.setValue("CARRO");

    colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
    colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoDescripcion"));
    colConductor.setCellValueFactory(new PropertyValueFactory<>("nombreConductor"));

    actualizarConsultas();
  }

  @FXML
  public void registrarIngreso() {
    String placa = campoPlacaIngreso.getText().trim().toUpperCase();
    String nombre = campoNombreConductor.getText().trim();
    String id = campoIdConductor.getText().trim();
    String tipoStr = combotipoVehiculo.getValue();

    if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty()) {
      etiquetaResultadoIngreso.setText("Complete todos los campos obligatorios.");
      return;
    }

    try {
      Vehiculo vehiculo;
      if (tipoStr.equals("MOTOCICLETA")) {
        int cilindraje = 0;
        String cilStr = campoCilindraje.getText().trim();
        if (!cilStr.isEmpty()) {
          cilindraje = Integer.parseInt(cilStr);
        }
        vehiculo = new Motocicleta(placa, nombre, id, cilindraje);
      } else if (tipoStr.equals("BICICLETA")) {
        vehiculo = new Bicicleta(placa, nombre, id);
      } else {
        vehiculo = new Carro(placa, nombre, id);
      }

      EspacioParqueadero espacio = parqueadero.registrarIngreso(vehiculo, id);
      etiquetaResultadoIngreso.setText("Ingreso registrado. Espacio asignado: " + espacio.getCodigo());
      limpiarCamposIngreso();
      actualizarConsultas();

    } catch (NumberFormatException e) {
      etiquetaResultadoIngreso.setText("El cilindraje debe ser un número entero.");
    } catch (ParkUQException e) {
      etiquetaResultadoIngreso.setText("Error: " + e.getMessage());
    }
  }

  @FXML
  public void registrarSalida() {
    String placa = campoPlacaSalida.getText().trim().toUpperCase();

    if (placa.isEmpty()) {
      areaRecibo.setText("Ingrese la placa del vehículo.");
      return;
    }

    try {
      RegistroSalida registro = parqueadero.registrarSalida(placa);
      areaRecibo.setText(registro.generarRecibo());
      campoPlacaSalida.clear();
      actualizarConsultas();

    } catch (ParkUQException e) {
      areaRecibo.setText("Error: " + e.getMessage());
    }
  }

  @FXML
  public void actualizarConsultas() {
    ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList(
        parqueadero.consultarVehiculosActivos()
    );
    tablaVehiculos.setItems(vehiculos);

    int disponibles = parqueadero.consultarEspaciosDisponibles().size();
    int total = parqueadero.getEspacios().size();
    etiquetaEspaciosDisponibles.setText("Espacios disponibles: " + disponibles + " / " + total);
  }

  @FXML
  public void generarReporte() {
    areaReporte.setText(gestorReportes.generarResumenDia());
  }

  private void limpiarCamposIngreso() {
    campoPlacaIngreso.clear();
    campoNombreConductor.clear();
    campoIdConductor.clear();
    campoCilindraje.clear();
    combotipoVehiculo.setValue("CARRO");
  }

  @FXML
  public void cerrarSesion() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/login.fxml"));
      Scene escena = new Scene(loader.load(), 400, 300);
      LoginControlador controlador = loader.getController();
      controlador.setParqueadero(parqueadero);
      controlador.setStage(stage);
      stage.setScene(escena);
      stage.setTitle("ParkUQ — Sistema de Gestión de Parqueadero");
      stage.setResizable(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setParqueadero(Parqueadero parqueadero) {
    this.parqueadero = parqueadero;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

}
