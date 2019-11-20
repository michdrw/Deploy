package ar.com.ada.api.sueldos.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.sueldos.entities.Empleado;

/**
 * Strategy SueldoAuxiliarCalculator
 * En este caso el sueldo siempre es el base de la categoria.
 */
public class SueldoAuxiliarCalculator implements SueldoCalculator {

    @Override
    public double calcularSueldo(Empleado empleado) {
        return empleado.getCategoria().getSueldoBase();
    }

    
}