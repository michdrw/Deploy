package ar.com.ada.api.sueldos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.sueldos.entities.Categoria;
import ar.com.ada.api.sueldos.entities.Empleado;
import ar.com.ada.api.sueldos.models.request.CategoriaRequest;
import ar.com.ada.api.sueldos.models.response.CategoriaReponse;
import ar.com.ada.api.sueldos.models.response.CategoriasNombresResponse;
import ar.com.ada.api.sueldos.services.CategoriaService;

/**
 * CategoriaController
 */
@RestController
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/categorias")
    public ResponseEntity<?> postRegisterUser(@RequestBody CategoriaRequest req) {

        CategoriaReponse r = new CategoriaReponse();
        Categoria c = new Categoria();
        c.setNombre(req.nombre);
        c.setSueldoBase(req.sueldoBase);

        categoriaService.save(c);

        r.isOk = true;
        r.id = c.getCategoriaId();
        r.message = "Creaste una categoria con éxito.";
        return ResponseEntity.ok(r);

    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> getCategorias() {
        List<Categoria> categorias = categoriaService.getCategorias();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/categorias/{catId}/empleados")
    public ResponseEntity<List<Empleado>> getEmpleadoByCatId(@PathVariable int catId) {
        Categoria c = categoriaService.buscarPorId(catId);

        return ResponseEntity.ok(c.getEmpleados());

    }

    @GetMapping("/categorias/sueldos-nuevos")
    public ResponseEntity<List<Empleado>> getSueldosNuevos() {

        return ResponseEntity.ok(categoriaService.calcularProximosSueldos());

    }

    @GetMapping("/categorias/sueldos-actuales")
    public ResponseEntity<List<Empleado>> getSueldosActuales() {

        return ResponseEntity.ok(categoriaService.obtenerSueldosActuales());

    }

    @GetMapping("/categorias/vacias")
    public ResponseEntity<List<Categoria>> getCategoriasSinEmpleados() {

        return ResponseEntity.ok(categoriaService.obtenerCategoriasSinEmpleados());

    }

    @GetMapping("/categorias/nombres")
    public ResponseEntity<CategoriasNombresResponse> getCategoriasNombres() {

        CategoriasNombresResponse r = new CategoriasNombresResponse();

        r.nombres = categoriaService.obtenerNombresCategorias();
        return ResponseEntity.ok(r);

    }

}