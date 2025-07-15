package com.GestionRemodelacion.gestion.proyecto.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByClienteId(Integer clienteId);
    List<Proyecto> findByEmpleadoResponsableId(Integer empleadoResponsableId);
    List<Proyecto> findByEstado(Proyecto.EstadoProyecto estado);
    Optional<Proyecto> findByNombreProyecto(String nombreProyecto);
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

}