package parking.ui.controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import parking.enums.EstadoEspacio;
import parking.enums.TipoEspacio;
import parking.enums.TipoUsuario;
import parking.enums.TipoVehiculo;
import parking.model.Administrativo;
import parking.model.Docente;
import parking.model.EspacioParqueadero;
import parking.model.Estudiante;
import parking.model.Tarifa;
import parking.model.Usuario;
import parking.model.Visitante;
import parking.servicio.Parqueadero;


public class AdminControlador {
  // Pestaña: Espacios
  @FXML private TextField campoCodigoEspacio;
  @FXML private ComboBox<String> comboTipoEspacio;
  @FXML private TextField campoCodigoModificar;
  @FXML private ComboBox<String> comboNuevoEstado;
  @FXML private Label etiquetaEspacios;
  @FXML private TableView<EspacioParqueadero> tablaEspacios;
  @FXML private TableColumn<EspacioParqueadero, String> colCodigo;
  @FXML private TableColumn<EspacioParqueadero, String> colTipoEspacio;
  @FXML private TableColumn<EspacioParqueadero, String> colEstado;

  // Pestaña: Tarifas
  @FXML private ComboBox<String> comboTipoVehiculo;
  @FXML private TextField campoNuevoValor;
  @FXML private Label etiquetaTarifas;
  @FXML private TableView<Tarifa> tablaTarifas;
  @FXML private TableColumn<Tarifa, String> colTarifaTipo;
  @FXML private TableColumn<Tarifa, Double> colValorHora;

  // Pestaña: Usuarios autorizados
  @FXML private TextField campoNombreUsuario;
  @FXML private TextField campoIdUsuario;
  @FXML private ComboBox<String> comboTipoUsuario;
  @FXML private Label etiquetaUsuarios;
  @FXML private TableView<Usuario> tablaUsuarios;
  @FXML private TableColumn<Usuario, String> colNombreUsuario;
  @FXML private TableColumn<Usuario, String> colIdUsuario;
  @FXML private TableColumn<Usuario, String> colTipoUsuario;
  @FXML private TableColumn<Usuario, String> colDescuento;

  private Parqueadero parqueadero;
  private Stage stage;

  public void inicializar() {
    // FXCollections actua como un puente que actualiza de manera dinamica
    // Los datos si estos son cambiados
    comboTipoEspacio.setItems(FXCollections.observableArrayList("CARRO", "MOTOCICLETA", "BICICLETA"));
    comboTipoEspacio.setValue("CARRO");

    comboNuevoEstado.setItems(FXCollections.observableArrayList("DISPONIBLE", "FUERA_DE_SERVICIO"));
    comboNuevoEstado.setValue("DISPONIBLE");

    comboTipoVehiculo.setItems(FXCollections.observableArrayList("CARRO", "MOTOCICLETA", "BICICLETA"));
    comboTipoVehiculo.setValue("CARRO");

    comboTipoUsuario.setItems(FXCollections.observableArrayList("ESTUDIANTE", "DOCENTE", "ADMINISTRATIVO", "VISITANTE"));
    comboTipoUsuario.setValue("ESTUDIANTE");

    colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    colTipoEspacio.setCellValueFactory(new PropertyValueFactory<>("tipoEspacio"));
    colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

    colTarifaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
    colValorHora.setCellValueFactory(new PropertyValueFactory<>("valorPorHora"));

    colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
    colTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
    colDescuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));

    tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
        (observable, anterior, seleccionado) -> {
          if (seleccionado != null) {
            campoNombreUsuario.setText(seleccionado.getNombre());
            campoIdUsuario.setText(seleccionado.getIdentificacion());
            comboTipoUsuario.setValue(seleccionado.getTipoUsuario().name());
          }
        }
    );

    actualizarTablas();
  }

  @FXML
  public void agregarEspacio() {
    String codigo = campoCodigoEspacio.getText().trim().toUpperCase();
    String tipoStr = comboTipoEspacio.getValue();

    if (codigo.isEmpty()) {
      etiquetaEspacios.setText("Ingrese el código del espacio.");
      return;
    }

    TipoEspacio tipo = TipoEspacio.valueOf(tipoStr);
    parqueadero.agregarEspacio(new EspacioParqueadero(codigo, tipo));
    etiquetaEspacios.setText("Espacio " + codigo + " agregado correctamente.");
    campoCodigoEspacio.clear();
    actualizarTablas();
  }

  @FXML
  public void modificarEstadoEspacio() {
    String codigo = campoCodigoModificar.getText().trim().toUpperCase();
    String estadoStr = comboNuevoEstado.getValue();

    if (codigo.isEmpty()) {
      etiquetaEspacios.setText("Ingrese el código del espacio a modificar.");
      return;
    }

    EstadoEspacio estado = EstadoEspacio.valueOf(estadoStr);
    parqueadero.modificarEstadoEspacio(codigo, estado);
    etiquetaEspacios.setText("Estado del espacio " + codigo + " actualizado a " + estadoStr + ".");
    campoCodigoModificar.clear();
    actualizarTablas();
  }

  @FXML
  public void modificarTarifa() {
    String tipoStr = comboTipoVehiculo.getValue();
    String valorStr = campoNuevoValor.getText().trim();

    if (valorStr.isEmpty()) {
      etiquetaTarifas.setText("Ingrese el nuevo valor por hora.");
      return;
    }

    try {
      double nuevoValor = Double.parseDouble(valorStr);
      TipoVehiculo tipo = TipoVehiculo.valueOf(tipoStr);
      parqueadero.modificarTarifa(tipo, nuevoValor);
      etiquetaTarifas.setText("Tarifa para " + tipoStr + " actualizada a $" + String.format("%.0f", nuevoValor) + "/hora.");
      campoNuevoValor.clear();
      actualizarTablas();

    } catch (NumberFormatException e) {
      etiquetaTarifas.setText("El valor debe ser un número.");
    }
  }

  @FXML
  public void agregarUsuarioAutorizado() {
    String nombre = campoNombreUsuario.getText().trim();
    String id = campoIdUsuario.getText().trim();
    String tipoStr = comboTipoUsuario.getValue();

    if (nombre.isEmpty() || id.isEmpty() || tipoStr.isEmpty()) {
      etiquetaUsuarios.setText("Los campos nombre, identificación y el tipo de usuario son requeridos");
      return;
    }

    Usuario usuario;
    switch (TipoUsuario.valueOf(tipoStr)) {
      case ESTUDIANTE -> usuario = new Estudiante(nombre, id);
      case DOCENTE -> usuario = new Docente(nombre, id);
      case ADMINISTRATIVO -> usuario = new Administrativo(nombre, id);
      default -> usuario = new Visitante(nombre, id);
    }

    parqueadero.agregarUsuarioAutorizado(usuario);
    etiquetaUsuarios.setText("Usuario " + nombre + " registrado con descuento del "
        + (int)(usuario.getDescuento() * 100) + "%.");
    campoNombreUsuario.clear();
    campoIdUsuario.clear();
    actualizarTablas();
  }

  @FXML
  public void eliminarUsuario() {
    Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
    if (seleccionado == null) {
      etiquetaUsuarios.setText("Seleccione un usuario de la tabla para eliminarlo.");
      return;
    }
    parqueadero.eliminarUsuarioAutorizado(seleccionado.getIdentificacion());
    etiquetaUsuarios.setText("Usuario " + seleccionado.getNombre() + " eliminado.");
    campoNombreUsuario.clear();
    campoIdUsuario.clear();
    actualizarTablas();
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

  private void actualizarTablas() {
    tablaEspacios.getItems().setAll((parqueadero.getEspacios()));
    tablaTarifas.getItems().setAll((parqueadero.getTarifas()));
    tablaUsuarios.getItems().setAll((parqueadero.getUsuariosAutorizados()));
  }

  public void setParqueadero(Parqueadero parqueadero) {
    this.parqueadero = parqueadero;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
