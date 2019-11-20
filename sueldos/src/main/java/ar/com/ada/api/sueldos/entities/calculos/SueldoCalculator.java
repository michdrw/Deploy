package ar.com.ada.api.sueldos.entities.calculos;


import ar.com.ada.api.sueldos.entities.Empleado;

/**
 * ueldoCalculator
 * Interface Strategy para calculo de sueldo
 */
public interface SueldoCalculator {

    double calcularSueldo(Empleado empleado);


}