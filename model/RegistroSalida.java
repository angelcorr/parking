package parkUQ.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroSalida {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final Vehiculo vehiculo;
    private final LocalDateTime horaIngreso;
    private final LocalDateTime horaSalida;
    private final long minutosEstadia;
    private final double tarifaBase;
    private final double descuentoAplicado;
    private final double totalCobrado;

    public RegistroSalida(Vehiculo vehiculo, LocalDateTime horaIngreso, LocalDateTime horaSalida,
                            long minutosEstadia, double tarifaBase, double descuentoAplicado, double totalCobrado) {
        this.vehiculo = vehiculo;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.minutosEstadia = minutosEstadia;
        this.tarifaBase = tarifaBase;
        this.descuentoAplicado = descuentoAplicado;
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

    public double getDescuentoAplicado() {
        return descuentoAplicado;
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
        recibo.append("Tarifa    : $").append(String.format("%.0f", tarifaBase)).append("\n");
        if (descuentoAplicado > 0) {
            recibo.append("Descuento : ").append((int)(descuentoAplicado * 100)).append("%\n");
        }
        recibo.append("TOTAL     : $").append(String.format("%.0f", totalCobrado)).append("\n");
        recibo.append("========================================\n");
        return recibo.toString();
    }

    @Override
    public String toString() {
        return vehiculo.getPlaca() + " | " + vehiculo.getTipoDescripcion()
                + " | " + minutosEstadia + " min | $" + String.format("%.0f", totalCobrado);
    }

}
