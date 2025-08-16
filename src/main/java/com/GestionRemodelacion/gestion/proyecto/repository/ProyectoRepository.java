package com.GestionRemodelacion.gestion.proyecto.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
  List<Proyecto> findByClienteId(Long clienteId);

  List<Proyecto> findByEmpleadoResponsableId(Long empleadoResponsableId);

  Optional<Proyecto> findByNombreProyecto(String nombreProyecto);

  /* Consultas personalizadas para visualizar en el dashboard */

  // Metodo para obtener todos los proyectos con el cliente y el empleado responsable
  @Query(value = "SELECT p FROM Proyecto p JOIN FETCH p.cliente c LEFT JOIN FETCH p.empleadoResponsable e", countQuery = "SELECT COUNT(p) FROM Proyecto p")
  Page<Proyecto> findAllWithClienteAndEmpleado(Pageable pageable);

    /* ======================================================================= */
    /* MÉTODOS EXCLUSIVOS PARA DASHBOARDSERVICE                                */
    /* ======================================================================= */  

    // -- Consultas Globales --

  // Metodo para contar todos los proyectos
  @Override
  long count();

  // Método para sumar el monto total recibido de todos los proyectos
  @Query("SELECT SUM(p.montoRecibido) FROM Proyecto p")
  BigDecimal sumMontoRecibido();

  // Método para sumar el costo consolidado de materiales de todos los proyectos
  @Query("SELECT SUM(p.costoMaterialesConsolidado) FROM Proyecto p")
  BigDecimal sumCostoMaterialesConsolidado();

  // Método para sumar otros gastos directos consolidados de todos los proyectos
  @Query("SELECT SUM(p.otrosGastosDirectosConsolidado) FROM Proyecto p")
  BigDecimal sumOtrosGastosDirectosConsolidado();

  // Buscar proyectos por estado directamente en la DB para el pie chart
  List<Proyecto> findByEstado(Proyecto.EstadoProyecto estado);

  // Consulta para contar proyectos por estado
  @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estado = :estado")
  Long countByEstado(@Param("estado") Proyecto.EstadoProyecto estado);

  // Consulta para contar proyectos por estado
  @Query("SELECT p.estado, COUNT(p) FROM Proyecto p GROUP BY p.estado")
  List<Object[]> countProyectosByEstado();

}