package com.GestionRemodelacion.gestion.horastrabajadas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.horastrabajadas.model.HorasTrabajadas;

@Repository
public interface HorasTrabajadasRepository extends JpaRepository<HorasTrabajadas, Long> {
  List<HorasTrabajadas> findByEmpleadoId(Long empleadoId);

  List<HorasTrabajadas> findByProyectoId(Long proyectoId);

  List<HorasTrabajadas> findByFechaBetween(Date startDate, Date endDate);

  List<HorasTrabajadas> findByEmpleadoIdAndFechaBetween(Long empleadoId, Date startDate, Date endDate);

  List<HorasTrabajadas> findByProyectoIdAndFechaBetween(Long proyectoId, Date startDate, Date endDate);

  /* Consultas personalizadas paara obtener información para el dashboard */

  // Consulta para sumar las horas por proyecto
  @Query("SELECT h.proyecto.id, h.proyecto.nombreProyecto, SUM(h.horas) FROM HorasTrabajadas h GROUP BY h.proyecto.id, h.proyecto.nombreProyecto")
  List<Object[]> sumHorasByProyecto();

  // Consulta para sumar las horas por empleado
  @Query("SELECT h.empleado.id, h.empleado.nombreCompleto, SUM(h.horas) FROM HorasTrabajadas h GROUP BY h.empleado.id, h.empleado.nombreCompleto")
  List<Object[]> sumHorasByEmpleado();

  // Consulta para sumar las horas por empleado y proyecto
  @Query("SELECT h.empleado.id, h.empleado.nombreCompleto, h.proyecto.id, h.proyecto.nombreProyecto, SUM(h.horas) FROM HorasTrabajadas h GROUP BY h.empleado.id, h.empleado.nombreCompleto, h.proyecto.id, h.proyecto.nombreProyecto ORDER BY h.empleado.nombreCompleto, h.proyecto.nombreProyecto")
  List<Object[]> sumHorasByEmpleadoAndProyecto();
}