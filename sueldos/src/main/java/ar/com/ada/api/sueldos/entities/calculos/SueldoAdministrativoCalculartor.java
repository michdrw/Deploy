package ar.com.ada.api.sueldos.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.sueldos.entities.Empleado;

/**
 * SueldoAdministrativoCalculartor En este caso el resultado es el sueldo actual
 * de la categoria actual Si el sueldo actual es menor al de la categoria se
 * reajusta ej cuando hay inflacion y estan en el minimo
 */
public class SueldoAdministrativoCalculartor implements SueldoCalculator {

    @Override
    public double calcularSueldo(Empleado empleado) {

        double sueldoActual = empleado.getSueldo();
        if (sueldoActual < empleado.getCategoria().getSueldoBase())
            return empleado.getCategoria().getSueldoBase();

        return sueldoActual;
    }

}