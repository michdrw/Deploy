package ar.com.ada.api.sueldos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.sueldos.entities.Categoria;

/**
 * CategoriaRepository
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    Categoria findByNombre (String nombreCategoria);
    Categoria findById (int id);
    
}