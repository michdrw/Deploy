package ar.com.ada.api.sueldos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.sueldos.entities.Categoria;
import ar.com.ada.api.sueldos.entities.Empleado;
import ar.com.ada.api.sueldos.repo.CategoriaRepository;

/**
 * CategoriaService
 */
@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public List<Categoria> getCategorias() {

        return repo.findAll();
    }

    public Categoria buscarPorNombre(String nombre) {

        return repo.findByNombre(nombre);
    }

    public Categoria buscarPorId(int id) {

        return repo.findById(id);
    }

    public void save(Categoria c) {
        repo.save(c);
    }

    public List<Empleado> calcularProximosSueldos() {
        List<Empleado> empleados = new ArrayList<>();

        this.getCategorias().stream().forEach(categoria -> {

            categoria.getEmpleados().stream().forEach(empleado -> {

                empleado.setSueldo(categoria.calcularSueldo(empleado));
                empleados.add(empleado);
            });

        });

        return empleados;
    }
    //las flechas separan el parametro del cuerpo de la funcion, tiene llaves porque es multilinea

    public List<Empleado> obtenerSueldosActuales() {
        List<Empleado> empleados = new ArrayList<>();

        this.getCategorias().stream().forEach(cat -> empleados.addAll(cat.getEmpleados()));

        return empleados;
    }

    /**
     * Modo normal.
     * 
     * @return
     */
    public List<Categoria> obtenerCategoriasSinEmpleadosEstandard() {

        List<Categoria> categoriasSinEmpleados = this.getCategorias();

        for (Categoria categoria : categoriasSinEmpleados) {

            if (categoria.getEmpleados().size() == 0)
                categoriasSinEmpleados.add(categoria);
        }

        return categoriasSinEmpleados;

    }

    /**
     * Modo funcional, se crea un stream, se le pasa el filter, y luego del filter
     * una condicion, esa condicion se eevalua para cada elemento, devolviendo un
     * stream de aquellos qeu el filtro haya sido verdadero. Finalmente se los toma
     * y se tranforma a una lista Otros metodos interesantes de funcional son
     * anyMatch y allMatch que detecta si hay algun elemento que cumpla una
     * condicion, o todos respectivamente.
     * 
     * @return
     */
    public List<Categoria> obtenerCategoriasSinEmpleados() {

        return this.getCategorias().stream().filter(cat -> cat.getEmpleados().size() == 0).collect(Collectors.toList());

    }

    /**
     * Modo normal, procedural como antes.
     * 
     * @return
     */
    public List<String> obtenerNombresCategoriasEstandar() {

        List<String> nombres = new ArrayList<>();

        for (Categoria categoria : this.getCategorias()) {

            nombres.add(categoria.getNombre());
        }

        return nombres;

    }

    /**
     * Modo funcional, se crea un stream, se mapea cada elemento(recorre) un stream
     * y el segundo, en caso de ser un array de arrays de X, deuvelve un array de X
     * 
     * @return
     */
    public List<String> obtenerNombresCategorias() {

        return this.getCategorias().stream().map(categoria -> categoria.getNombre()).collect(Collectors.toList());

    }
}