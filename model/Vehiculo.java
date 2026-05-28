package parkUQ.model;

import parkUQ.enums.EstadoVehiculo;
import parkUQ.enums.TipoVehiculo;

import java.time.LocalDateTime;

public abstract class Vehiculo {
    private String placa;
    private TipoVehiculo tipoVehiculo;
    private String nombreConductor;
    private String identificacionConductor;
    private LocalDateTime horaIngreso;
    private EspacioParqueadero espacioAsignado;
    private EstadoVehiculo estado;

    public Vehiculo(String placa, TipoVehiculo tipoVehiculo, String nombreConductor, String identificacionConductor) {
        this.placa = placa.toUpperCase();
        this.tipoVehiculo = tipoVehiculo;
        this.nombreConductor = nombreConductor;
        this.identificacionConductor = identificacionConductor;
        this.estado = EstadoVehiculo.DENTRO;
    }

    public abstract String getTipoDescripcion();

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    public String getNombreConductor() {
        return nombreConductor;
    }
    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }
    public String getIdentificacionConductor() {
        return identificacionConductor;
    }
    public void setIdentificacionConductor(String identificacionConductor) {
        this.identificacionConductor = identificacionConductor;
    }
    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }
    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }
    public EspacioParqueadero getEspacioAsignado() {
        return espacioAsignado;
    }
    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getTipoDescripcion() + " | Placa: " + placa + " | Conductor: " + nombreConductor;
    }
}
