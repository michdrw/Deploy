package ar.com.ada.api.sueldos.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.sueldos.entities.Empleado;

/**
 * EmpleadoRepository
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
    
    Empleado findById (int id);
    Empleado findByNombre (String nombre);
    Empleado findByDni (int id);

    
}