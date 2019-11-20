package ar.com.ada.api.sueldos.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.sueldos.entities.Empleado;

/**
 * SueldoVendedorCalculator
 * En este cao es siempre el sueldo base de la categoria
 * más el 10% de ventas.
 */
public class SueldoVendedorCalculator implements SueldoCalculator {

    @Override
    public double calcularSueldo(Empleado empleado) {
        return empleado.getCategoria().getSueldoBase() +
             empleado.getVentasActuales() * 0.10;
    }

    
}