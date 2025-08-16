package com.GestionRemodelacion.gestion.empleado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.empleado.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Buscar empleado por nombre
    Optional<Empleado> findByNombreCompleto(String nombre);

    // Buscar empleados por estado de actividad
    List<Empleado> findByActivo(Boolean activo);

    // Contar empleados por estado de actividad
    long countByActivo(boolean activo);

    // Método para paginar y filtrar (para la tabla)
    Page<Empleado> findByNombreCompletoContainingIgnoreCaseOrRolCargoContainingIgnoreCaseOrTelefonoContactoContainingIgnoreCase(
            String nombre, String rol, String telefono, Pageable pageable);

    // Método para obtener todos los datos filtrados y ordenados (para la exportación)
    List<Empleado> findByNombreCompletoContainingIgnoreCaseOrRolCargoContainingIgnoreCaseOrTelefonoContactoContainingIgnoreCase(
            String nombre, String rol, String telefono, Sort sort);

   /* Consultas para obtener estadísticas y visualizar en el dashboard */

      // Consulta para calcular el costo promedio por hora
    @Query("SELECT AVG(e.costoPorHora) FROM Empleado e WHERE e.activo = true")
    Double findAvgCostoPorHora();

    // Consulta para contar empleados por rol/cargo
    @Query("SELECT e.rolCargo, COUNT(e) FROM Empleado e GROUP BY e.rolCargo")
    List<Object[]> countEmpleadosByRol();         

}
