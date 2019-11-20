package ar.com.ada.api.sueldos.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.sueldos.entities.Categoria;
import ar.com.ada.api.sueldos.entities.Empleado;
import ar.com.ada.api.sueldos.excepciones.EmpleadoException;
import ar.com.ada.api.sueldos.models.request.EmpleadoRequest;
import ar.com.ada.api.sueldos.models.response.EmpleadoResponse;
import ar.com.ada.api.sueldos.services.CategoriaService;
import ar.com.ada.api.sueldos.services.EmpleadoService;

/**
 * EmpleadoController
 */
@RestController
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleados")
    public ResponseEntity<?> postnewEmpleado(@RequestBody EmpleadoRequest req) {

        EmpleadoResponse e = new EmpleadoResponse();

        Categoria cat = categoriaService.buscarPorId(req.categoriaId);

        try {

            empleadoService.crearEmpleado(req.nombre, req.dni, cat, req.edad, req.sueldo, req.estado);
        } catch (EmpleadoException empEx) {

            e.isOk = false;
            e.message = "Error " + empEx.getErrorType();

            return ResponseEntity.badRequest().body(e);
        }

        catch (Exception ex) {
            e.isOk = false;
            e.message = "Error General";

            return ResponseEntity.badRequest().body(e);
        }

        e.isOk = true;
        e.message = "Creaste un empleado con éxito.";
        return ResponseEntity.ok(e);

    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> getEmpleados() {
        List<Empleado> empleados = empleadoService.getEmpleados();

        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<?> getEmpleadoById(@PathVariable int id) {
        Empleado e = empleadoService.buscarPorId(id);

        if (e == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(e);
    }

    @PutMapping("/empleados/{id}") // hay path int porque lo necesito en el /empleados/{id}
    public ResponseEntity<?> actualizaEmpleado(@PathVariable int id, @RequestBody EmpleadoRequest req)
            throws EmpleadoException {

        EmpleadoResponse e = new EmpleadoResponse();
        Categoria c = categoriaService.buscarPorId(req.categoriaId);

        if (c == null)
            return ResponseEntity.notFound().build();

        try {

            Empleado empleado = empleadoService.actualizarEmpleado(id, req.nombre, req.edad, c);

            if (empleado == null)
                return ResponseEntity.notFound().build();

        } catch (EmpleadoException empEx) {

            e.isOk = false;
            e.message = "Error " + empEx.getErrorType();

            return ResponseEntity.badRequest().body(e);
        }

        e.isOk = true;
        e.message = "Los datos han sido actualizados.";

        return ResponseEntity.ok(e);
    }

    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<?> actualizaSueldoEmpleado(@PathVariable int id, @RequestBody EmpleadoRequest req)
            throws EmpleadoException {
        EmpleadoResponse e = new EmpleadoResponse();

        Empleado empleado = empleadoService.actualizaSueldoEmpleado(id, req.sueldo);

        if (empleado == null)
            return ResponseEntity.notFound().build();

        e.isOk = true;
        e.message = "El sueldo ha sido actualizado.";

        return ResponseEntity.ok(e);
    }

    @DeleteMapping("empleados/{id}")
    public ResponseEntity<?> daDeBajaEmpleado(@PathVariable int id) throws EmpleadoException {
        EmpleadoResponse e = new EmpleadoResponse();
        Empleado empleado = empleadoService.daDeBajaEmpleado(id, "baja", new Date()); // para que me de la fecha actual

        if (empleado == null)
            return ResponseEntity.notFound().build();

        e.isOk = true;
        e.message = "El empleado ha sido dado de baja.";

        return ResponseEntity.ok(e);
    }
}