package parkUQ.servicio;

import parkUQ.enums.EstadoVehiculo;
import parkUQ.enums.EstadoEspacio;
import parkUQ.enums.TipoVehiculo;
import parkUQ.modelo.EspacioParqueadero;
import parkUQ.modelo.RegistroSalida;
import parkUQ.modelo.Tarifa;
import parkUQ.modelo.Usuario;
import parkUQ.modelo.UsuarioSistema;
import parkUQ.modelo.Vehiculo;

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
    private List<UsuarioSistema> usuarioSistema;

    public Parqueadero(String nombre) {
        this.nombre = nombre;
        this.espacios = new ArrayList<>();
        this.vehiculosActivos = new ArrayList<>();
        this.historialDia = new ArrayList<>();
        this.usuariosAutorizados = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.usuarioSistema = new ArrayList<>();
    }

    public EspacioParqueadero registrarIngreso(Vehiculo vehiculo, String idConductor) {
        verificarPlacaNoDuplicada(vehiculo.getPlaca());
        EspacioParqueadero espacio = buscarEspacioDisponible(vehiculo.getTipoVehiculo);

        espacio.asignarVehiculo(vehiculo);
        vehiculo.setHoraIngreso(LocalDataTime.now());
        vehiculo.setEspacioAsignado(espacio);
        vehiculo.setEstado(EstadoVehiculo.DENTRO);
        vehiculosActivos.add(vehiculo);

        return espacio;
    }

    public RegistroSalida registrarSalida(String placa) {
        Vehiculo vehiculo = buscarVehiculoActiva(placa);

        LocalDataTime horaSalida = LocalDataTime.now();
        long minutos = ChronoUnit.MINUTES.between(vehiculo.getHoraIngreso(), horaSalida);

        Tarifa tarifa = buscarTarifa(vehiculo.getVehiculo());
        double tarifaBase = tarifa.calcularCosto(minutos);

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
                espacio.setEspacio(nuevoEstado);
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
        return usuariosAutorizados.removeIf(u -> u.getIdeficiacion().equals(identificacion));
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

    
}