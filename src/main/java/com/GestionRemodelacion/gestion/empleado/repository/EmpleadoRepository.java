package com.GestionRemodelacion.gestion.empleado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.empleado.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByNombreCompleto(String nombre);
    // Buscar empleados por estado de actividad
    List<Empleado> findByActivo(Boolean activo);
    long countByActivo(boolean activo);
}