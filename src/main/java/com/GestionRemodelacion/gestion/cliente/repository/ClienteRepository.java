package com.GestionRemodelacion.gestion.cliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombreCliente(String nombreCliente);
    // ⭐️ Método para paginar y filtrar (para la tabla)
    Page<Cliente> findByNombreClienteContainingIgnoreCaseOrTelefonoContactoContainingIgnoreCase(String nombreCliente, String telefonoContacto, Pageable pageable);
    
    // ⭐️ Método para obtener todos los datos filtrados y ordenados (para la exportación)
    List<Cliente> findByNombreClienteContainingIgnoreCaseOrTelefonoContactoContainingIgnoreCase(String nombreCliente, String telefonoContacto, Sort sort);

/* Consultas para visualización de estadísticas en el dashboard */

    // Query para contar clientes por mes y año
    @Query("SELECT YEAR(c.fechaRegistro) AS anio, MONTH(c.fechaRegistro) AS mes, COUNT(c) FROM Cliente c WHERE YEAR(c.fechaRegistro) = :year GROUP BY anio, mes ORDER BY anio, mes")
    List<Object[]> countClientesByMonthForYear(@Param("year") int year);

    // Query para obtener todos los años únicos donde hay registros.
    @Query("SELECT DISTINCT YEAR(c.fechaRegistro) FROM Cliente c ORDER BY YEAR(c.fechaRegistro) DESC")
    List<Integer> findDistinctYears();

}