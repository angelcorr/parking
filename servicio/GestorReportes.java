package parking.servicio;


import parking.model.RegistroSalida;
import parking.model.Vehiculo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorReportes {

    private final Parqueadero parqueadero;

    public GestorReportes (Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

    public double calcularIngresosTotales() {
        double total = 0;
        for (RegistroSalida registro : parqueadero.getHistorialDia()) {
            total += registro.getTotalCobrado();
        }
        return total;
    }

    public double calcularTiempoPromedio() {
        List<RegistroSalida> historial = parqueadero.getHistorialDia();
        if (historial.isEmpty()) {
            return 0;
        }
        long sumaMinutos = 0;
        for (RegistroSalida registro : historial) {
            sumaMinutos += registro.getMinutosEstadia();
        }
        return (double) sumaMinutos / historial.size();
    }

    public List<Vehiculo> listarVehiculosSobreTiempo(int limiteMinutos) {
        List<Vehiculo> resultado = new ArrayList<>();
        LocalDateTime ahora =  LocalDateTime.now();
        for (Vehiculo vehiculo : parqueadero.consultarVehiculosActivos()) {
            long minutos = ChronoUnit.MINUTES.between(vehiculo.getHoraIngreso(), ahora);
            if (minutos > limiteMinutos) {
                resultado.add(vehiculo);
            }
        }
        return resultado;
    }

    public String generarResumenDia() {
        List<RegistroSalida> historial = parqueadero.getHistorialDia();
        int totalVehiculos = historial.size();
        double ingresos = calcularIngresosTotales();
        double tiempoPromedio = calcularTiempoPromedio();

        int carros = 0, motos = 0, bicicletas = 0;
        for (RegistroSalida r : historial) {
            switch (r.getVehiculo().getTipoVehiculo()) {
                case CARRO -> carros++;
                case MOTO -> motos++;
                case BICICLETA -> bicicletas++;
            }
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("========================================\n");
        resumen.append("         REPORTE DEL DÍA - ParkUQ       \n");
        resumen.append("========================================\n");
        resumen.append("Total vehículos atendidos : ").append(totalVehiculos).append("\n");
        resumen.append("  Automóviles             : ").append(carros).append("\n");
        resumen.append("  Motocicletas            : ").append(motos).append("\n");
        resumen.append("  Bicicletas              : ").append(bicicletas).append("\n");
        resumen.append("Ingresos generados        : $").append(String.format("%.0f", ingresos)).append("\n");
        resumen.append("Tiempo promedio estadía   : ").append(String.format("%.1f", tiempoPromedio)).append(" min\n");
        resumen.append("Vehículos activos ahora   : ").append(parqueadero.consultarVehiculosActivos().size()).append("\n");
        resumen.append("========================================\n");
        return resumen.toString();
    }
}
