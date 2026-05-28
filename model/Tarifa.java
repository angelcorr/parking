package parkUQ.model;

import parkUQ.enums.TipoVehiculo;

public class Tarifa {

    private TipoVehiculo tipoVehiculo;
    private double valorPorHora;

    public Tarifa(TipoVehiculo tipoVehiculo, double valorPorHora) {
        this.tipoVehiculo = tipoVehiculo;
        this.valorPorHora = valorPorHora;
    }

    public double calcularCosto(long minutos) {
        double horas = minutos / 60.0;
        double horasEfectivas = Math.max(1.0, horas);
        return horasEfectivas * valorPorHora;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    @Override
    public String toString() {
        return tipoVehiculo + ": $" + String.format("%.0f", valorPorHora) + "/hora";
    }
}
