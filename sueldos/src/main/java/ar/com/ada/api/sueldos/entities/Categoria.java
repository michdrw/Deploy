package ar.com.ada.api.sueldos.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.com.ada.api.sueldos.entities.calculos.SueldoAdministrativoCalculartor;
import ar.com.ada.api.sueldos.entities.calculos.SueldoAuxiliarCalculator;
import ar.com.ada.api.sueldos.entities.calculos.SueldoCalculator;
import ar.com.ada.api.sueldos.entities.calculos.SueldoVendedorCalculator;

/**
 * Categoria
 */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "categoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoriaId;
    private String nombre;
    @Column(name = "sueldo_base")
    private double sueldoBase;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Empleado> empleados = new ArrayList<Empleado>();


    /**
     * - Pattern Desgign Strategy: en este caso, se uso como modelo el 
     * calculo de sueldos de un empleado. 
     * Si el empleado es Administrativo, el sueldo actual no puede ser menor al sueldo de la categoria
     *  cuando haya recalculo de sueldos. 
     * En el caso de un Auxiliar, el sueldo actual siempre es el sueldo de 
     * la categoria . 
     * En caso de vendedores, 
     * se usa el sueldo de la categoria base + 10% de comisiones sobre ventas. Para este caso se puso una interface SueldoCalculator, que se usa en la clase Categoria para calcular el sueldo. Cuando una categoria tenga nombre Administrativo, Pasasante
     */
    @JsonIgnore //evita que se exponga a la api
    @Transient //evita que se mapee a la DB
    private SueldoCalculator sueldoStrategy;

    /**
     * @return the categoriaId
     */
    public int getCategoriaId() {
        return categoriaId;
    }

    /**
     * @param categoriaId the categoriaId to set
     */
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     *     
     * - Pattern Desgign Strategy: en este caso, se uso como modelo el 
     * calculo de sueldos de un empleado. 
     * Si el empleado es Administrativo, el sueldo actual no puede ser menor al sueldo de la categoria
     *  cuando haya recalculo de sueldos. 
     * En el caso de un Auxiliar, el sueldo actual siempre es el sueldo de 
     * la categoria . 
     * En caso de vendedores, 
     * se usa el sueldo de la categoria base + 10% de comisiones sobre ventas. Para este caso se puso una interface SueldoCalculator, que se usa en la clase Categoria para calcular el sueldo. Cuando una categoria tenga nombre Administrativo, Pasasante
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        switch (this.nombre) {

        case "Vendedor":
            this.setSueldoStrategy(new SueldoVendedorCalculator());
            break;
        case "Administrativo":
            this.setSueldoStrategy(new SueldoAdministrativoCalculartor());
            break;
        case "Auxiliar":
            this.setSueldoStrategy(new SueldoAuxiliarCalculator());
            break;

        default:

            // Por ahor default lo ponemos como Administrativo
            this.setSueldoStrategy(new SueldoAdministrativoCalculartor());
            break;
        }
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    /**
     * @return the empleados
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void agregarEmpleado(Empleado empleado) {
        empleado.setCategoria(this);
        this.empleados.add(empleado);

    }

    /**
     * @return the sueldoStrategy
     */
    public SueldoCalculator getSueldoStrategy() {

        // si esta en nulo, le genero la estrategy a traves del nombre
        //Cuando vienen desde la db este valor esta en nulo
        //por lo cual lo reasigno.
        if (this.sueldoStrategy == null)
            this.setNombre(this.getNombre());
        return sueldoStrategy;
    }

    /**
     * @param sueldoStrategy the sueldoStrategy to set
     */
    public void setSueldoStrategy(SueldoCalculator sueldoStrategy) {
        this.sueldoStrategy = sueldoStrategy;
    }

    public double calcularSueldo(Empleado empleado) {
        return this.getSueldoStrategy().calcularSueldo(empleado);
    }

}