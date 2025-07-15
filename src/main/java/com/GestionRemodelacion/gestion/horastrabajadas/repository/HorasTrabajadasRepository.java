package com.GestionRemodelacion.gestion.horastrabajadas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GestionRemodelacion.gestion.horastrabajadas.model.HorasTrabajadas;

@Repository
public interface HorasTrabajadasRepository extends JpaRepository<HorasTrabajadas, Integer> {
    List<HorasTrabajadas> findByEmpleadoId(Integer empleadoId);
    List<HorasTrabajadas> findByProyectoId(Integer proyectoId);
    List<HorasTrabajadas> findByFechaBetween(Date startDate, Date endDate);
    List<HorasTrabajadas> findByEmpleadoIdAndFechaBetween(Integer empleadoId, Date startDate, Date endDate);
    List<HorasTrabajadas> findByProyectoIdAndFechaBetween(Integer proyectoId, Date startDate, Date endDate);
}