package ar.com.ada.api.sueldos.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.sueldos.entities.Categoria;
import ar.com.ada.api.sueldos.entities.Empleado;
import ar.com.ada.api.sueldos.excepciones.EmpleadoException;
import ar.com.ada.api.sueldos.repo.EmpleadoRepository;

/**
 * EmpleadoService
 */
@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository repo;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    EmpleadoService empleadoService;

    public Empleado buscarPorId(int id) {

        return repo.findById(id);
    }

    public void grabar(Empleado e) throws EmpleadoException {
        EmpleadoValidationType r = this.verificarEmpleado(e);

        if (r != EmpleadoValidationType.EMPLEADO_OK)
            throw new EmpleadoException(r);

        repo.save(e);

    }

    public Empleado crearEmpleado(String nombre, int dni, Categoria cat, int edad, double sueldo, String estado)
            throws EmpleadoException {
        Empleado empleado = new Empleado();

        empleado.setNombre(nombre);
        empleado.setDni(dni);
        
        empleado.setEdad(edad);
        empleado.setSueldo(sueldo);
        empleado.setEstado(estado);
        empleado.setFechaAlta(new Date());
        cat.agregarEmpleado(empleado);

        this.grabar(empleado);
        return empleado;
    }

    public List<Empleado> getEmpleados() {

        return repo.findAll();
    }

    public Empleado actualizarEmpleado(int id, String nombre, int edad, Categoria c) throws EmpleadoException {

        Empleado empleado = empleadoService.buscarPorId(id);

        c.agregarEmpleado(empleado); // vinculo el id de categoria con la categoria del empleado
        empleado.setNombre(nombre);
        empleado.setEdad(edad);
        empleado.setCategoria(c);

        this.grabar(empleado);
        return empleado;

    }

    public Empleado actualizaSueldoEmpleado(int id, double sueldo) throws EmpleadoException {

        Empleado empleado = empleadoService.buscarPorId(id);

        empleado.setSueldo(sueldo);

        this.grabar(empleado);
        return empleado;
    }

    public Empleado daDeBajaEmpleado(int id, String estado, Date fechaBaja) throws EmpleadoException {

        Empleado empleado = empleadoService.buscarPorId(id);
        empleado.setEstado(estado);
        empleado.setFechaBaja(fechaBaja);

        this.grabar(empleado);
        return empleado;
    }

    public enum EmpleadoValidationType {

        EMPLEADO_OK, 
        EMPLEADO_DUPLICADO, 
        EMPLEADO_INVALIDO, 
        SUELDO_NULO, 
        EDAD_INVALIDA, 
        NOMBRE_INVALIDO,
        EMPLEADO_DATOS_INVALIDOS

    }

    /**
     * Verifica que el nombre no esté nulo, La edad sea mayor a 0, El sueldo sea
     * mayor a 0, El estado, la fecha de alta y baja no estén en nulo.
     * 
     * @param empleado
     * @return
     */

    public EmpleadoValidationType verificarEmpleado(Empleado empleado) {

        if (empleado.getNombre() == null)
            return EmpleadoValidationType.NOMBRE_INVALIDO;

        if (empleado.getEdad() <= 0)
            return EmpleadoValidationType.EDAD_INVALIDA;

        if (empleado.getSueldo() <= 0)
            return EmpleadoValidationType.SUELDO_NULO;
        if (empleado.getEstado() == null)
            return EmpleadoValidationType.EMPLEADO_DATOS_INVALIDOS;
        if (empleado.getFechaAlta() == null)
            return EmpleadoValidationType.EMPLEADO_DATOS_INVALIDOS;
        if (empleado.getDni() <= 0)
            return EmpleadoValidationType.EMPLEADO_INVALIDO;

        Empleado e = repo.findByDni(empleado.getDni());
        if (e != null) {
            if (empleado.getEmpleadoId() != 0) {
                if ((empleado.getEmpleadoId() != e.getEmpleadoId())) {
                    return EmpleadoValidationType.EMPLEADO_DUPLICADO;
                } else {
                    return EmpleadoValidationType.EMPLEADO_OK;
                }
            } else
                return EmpleadoValidationType.EMPLEADO_DUPLICADO;

        }
        return EmpleadoValidationType.EMPLEADO_OK;
    }
}
