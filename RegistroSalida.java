package trabajoFinal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroSalida {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Vehiculo vehiculo;
    private final LocalDateTime horaIngreso;
    private final LocalDateTime horaSalida;
    private final long minutosEstadia;
    private final double tarifaBase;
    private final double porcentajeDescuento; // Nombre más claro
    private final double totalCobrado;

    public RegistroSalida(Vehiculo vehiculo, LocalDateTime horaIngreso, LocalDateTime horaSalida,
                          long minutosEstadia, double tarifaBase, double porcentajeDescuento, double totalCobrado) {
        
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        if (minutosEstadia < 0) {
            throw new IllegalArgumentException("Los minutos de estadía no pueden ser negativos.");
        }

        this.vehiculo = vehiculo;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.minutosEstadia = minutosEstadia;
        this.tarifaBase = tarifaBase;
        this.porcentajeDescuento = porcentajeDescuento;
        this.totalCobrado = totalCobrado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public long getMinutosEstadia() {
        return minutosEstadia;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public double getTotalCobrado() {
        return totalCobrado;
    }

    public String generarRecibo() {
        StringBuilder recibo = new StringBuilder();

        recibo.append("========================================\n");
        recibo.append("         RECIBO DE SALIDA — ParkUQ      \n");
        recibo.append("========================================\n");
        recibo.append("Vehículo  : ").append(vehiculo.getTipoDescripcion()).append("\n");
        recibo.append("Placa     : ").append(vehiculo.getPlaca()).append("\n");
        recibo.append("Conductor : ").append(vehiculo.getNombreConductor()).append("\n");
        recibo.append("Ingreso   : ").append(horaIngreso.format(FORMATO)).append("\n");
        recibo.append("Salida    : ").append(horaSalida.format(FORMATO)).append("\n");
        recibo.append("Tiempo    : ").append(minutosEstadia).append(" minutos\n");
        recibo.append("----------------------------------------\n");

        recibo.append(String.format("Tarifa    : $%.0f\n", tarifaBase));

        if (porcentajeDescuento > 0) {
            int porcentajeVisual = (int) (porcentajeDescuento * 100);
            recibo.append("Descuento : ").append(porcentajeVisual).append("%\n");
        }

        recibo.append(String.format("TOTAL     : $%.0f\n", totalCobrado));
        recibo.append("========================================\n");

        return recibo.toString();
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %d min | $%.0f",
                vehiculo.getPlaca(),
                vehiculo.getTipoDescripcion(),
                minutosEstadia,
                totalCobrado);
    }
}