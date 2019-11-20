package ar.com.ada.api.sueldos.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Empleado
 */
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @Column(name = "empleado_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empleadoId;
    private String nombre;
    private int edad;
    private double sueldo;
    private String estado;
    private Date fechaAlta;
    private Date fechaBaja;
    private int dni;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id")
    // @MapsId
    private Categoria categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }


    @JsonIgnore
    public double getVentasActuales() {

        Random randomGenerator = new Random();

        // Genero un numero rando hasta 10000
        double venta = randomGenerator.nextDouble() * 10000 + 1;
        // redondeo en 2 decimales el random truncando
        venta = ((long) (venta * 100)) / 100d;

        return venta;
    }

}