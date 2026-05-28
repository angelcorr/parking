package parking.servicio;

import parking.enums.EstadoVehiculo;
import parking.enums.EstadoEspacio;
import parking.enums.TipoVehiculo;
import parking.model.EspacioParqueadero;
import parking.model.RegistroSalida;
import parking.model.Tarifa;
import parking.model.Usuario;
import parking.model.UsuarioSistema;
import parking.model.Vehiculo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero {

    private String nombre;
    private List<EspacioParqueadero> espacios;
    private List<Vehiculo> vehiculosActivos;
    private List<RegistroSalida> historialDia;
    private List<Usuario> usuariosAutorizados;
    private List<Tarifa> tarifas;
    private List<UsuarioSistema> usuariosSistema;

    public Parqueadero(String nombre) {
        this.nombre = nombre;
        this.espacios = new ArrayList<>();
        this.vehiculosActivos = new ArrayList<>();
        this.historialDia = new ArrayList<>();
        this.usuariosAutorizados = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.usuariosSistema = new ArrayList<>();
    }

    public EspacioParqueadero registrarIngreso(Vehiculo vehiculo, String idConductor) {
        verificarPlacaNoDuplicada(vehiculo.getPlaca());
        EspacioParqueadero espacio = buscarEspacioDisponible(vehiculo.getTipoVehiculo());

        espacio.asignarVehiculo(vehiculo);
        vehiculo.setHoraIngreso(LocalDateTime.now());
        vehiculo.setEspacioAsignado(espacio);
        vehiculo.setEstado(EstadoVehiculo.DENTRO);
        vehiculosActivos.add(vehiculo);

        return espacio;
    }

    public RegistroSalida registrarSalida(String placa) {
        Vehiculo vehiculo = buscarVehiculoActivo(placa);

        LocalDateTime horaSalida = LocalDateTime.now();
        long minutos = ChronoUnit.MINUTES.between(vehiculo.getHoraIngreso(), horaSalida);

        Tarifa tarifa = buscarTarifa(vehiculo.getTipoVehiculo());
        double tarifaBase = tarifa.calcularCosto(minutos);

        double descuento = buscarDescuentoConductor(vehiculo.getIdentificacionConductor());
        double totalCobrado = tarifaBase * (1 - descuento);

        RegistroSalida registro = new RegistroSalida(
            vehiculo,
            vehiculo.getHoraIngreso(),
            horaSalida,
            minutos,
            tarifaBase,
            descuento,
            totalCobrado
        );

        vehiculo.getEspacioAsignado().liberarEspacio();
        vehiculo.setEstado(EstadoVehiculo.SALIO);
        vehiculosActivos.remove(vehiculo);
        historialDia.add(registro);

        return registro;
    }

    public List<Vehiculo> consultarVehiculosActivos() { return new ArrayList<>(vehiculosActivos); }

    public List<EspacioParqueadero> consultarEspaciosDisponibles() {
        List<EspacioParqueadero> disponibles = new ArrayList<>();
        for (EspacioParqueadero espacio : espacios) {
            if(espacio.estaDisponible()) {
                disponibles.add(espacio);
            }
        }
        return disponibles;
    }

    public void agregarEspacio(EspacioParqueadero espacio) { espacios.add(espacio); }

    public void modificarEstadoEspacio(String codigo, EstadoEspacio nuevoEstado) {
        for (EspacioParqueadero espacio : espacios) {
            if (espacio.getCodigo().equalsIgnoreCase(codigo)) {
                espacio.setEstado(nuevoEstado);
                return;
            }
        }
    }

    public void agregarTarifa(Tarifa tarifa) { tarifas.add(tarifa); }

    public void modificarTarifa(TipoVehiculo tipo, double nuevoValor) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getTipoVehiculo() == tipo) {
                tarifa.setValorPorHora(nuevoValor);
                return;
            }
        }
    }

    public void agregarUsuarioAutorizado(Usuario usuario) { usuariosAutorizados.add(usuario); }

    public boolean eliminarUsuarioAutorizado(String identificacion) {
        return usuariosAutorizados.removeIf(u -> u.getIdentificacion().equals(identificacion));
    }

    public UsuarioSistema autentificarUsuarioSistema(String user, String pass) {
        for (UsuarioSistema u : usuariosSistema) {
            if (u.autenticar(user, pass)) {
                return u;
            }
        }
        return null;
    }

    public void agregarUsuarioSistema(UsuarioSistema usuario) { usuariosSistema.add(usuario); }

    private void verificarPlacaNoDuplicada(String placa) {
        for (Vehiculo v : vehiculosActivos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                throw new PlacaDuplicadaException(placa);
            }
        }
    }

    private EspacioParqueadero buscarEspacioDisponible(TipoVehiculo tipo) {
        for (EspacioParqueadero espacio : espacios) {
            if (espacio.getTipoEspacio().name().equals(tipo.name()) && espacio.estaDisponible()) {
                return espacio;
            }
        }
        throw new SinEspacioDisponibleException(tipo.name());
    }

    private Vehiculo buscarVehiculoActivo(String placa) {
        for (Vehiculo v : vehiculosActivos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        throw new VehiculoNoEncontradoException(placa);
    }

    private Tarifa buscarTarifa(TipoVehiculo tipo) {
        for (Tarifa t : tarifas) {
            if (t.getTipoVehiculo() == tipo) {
                return t;
            }
        }
        return new Tarifa(tipo, 0.0);
    }

    private double buscarDescuentoConductor(String identificacion) {
        for (Usuario u : usuariosAutorizados) {
            if (u.getIdentificacion().equals(identificacion)) {
                return u.getDescuento();
            }
        }
        return 0.0;
    }

    public String getNombre() { return nombre; }

    public List<EspacioParqueadero> getEspacios() { return new ArrayList<>(espacios); }

    public List<RegistroSalida> getHistorialDia() { return new ArrayList<>(historialDia); }

    public List<Usuario> getUsuariosAutorizados() { return new ArrayList<>(usuariosAutorizados); }

    public List<Tarifa> getTarifas() { return new ArrayList<>(tarifas); }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
