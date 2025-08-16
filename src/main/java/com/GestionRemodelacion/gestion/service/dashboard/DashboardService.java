package com.GestionRemodelacion.gestion.service.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GestionRemodelacion.gestion.cliente.repository.ClienteRepository;
import com.GestionRemodelacion.gestion.dto.response.DashboardSummaryResponse;
import com.GestionRemodelacion.gestion.empleado.repository.EmpleadoRepository;
import com.GestionRemodelacion.gestion.horastrabajadas.repository.HorasTrabajadasRepository;
import com.GestionRemodelacion.gestion.proyecto.repository.ProyectoRepository;

@Service
public class DashboardService {

  private final ProyectoRepository proyectoRepository;
  private final EmpleadoRepository empleadoRepository;
  private final HorasTrabajadasRepository horasTrabajadasRepository;
  private final ClienteRepository clienteRepository;

  public DashboardService(ProyectoRepository proyectoRepository, EmpleadoRepository empleadoRepository, HorasTrabajadasRepository horasTrabajadasRepository, ClienteRepository clienteRepository) {
    this.proyectoRepository = proyectoRepository;
    this.empleadoRepository = empleadoRepository;
    this.horasTrabajadasRepository = horasTrabajadasRepository;
    this.clienteRepository = clienteRepository;
  }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary(Integer year) {
        int targetYear = (year != null) ? year : LocalDate.now().getYear();
        // Métricas de resumen (las que ya tenías)
        Long totalProyectos = proyectoRepository.count();
        Long empleadosActivos = empleadoRepository.countByActivo(true);
        BigDecimal montoRecibido = Optional.ofNullable(proyectoRepository.sumMontoRecibido()).orElse(BigDecimal.ZERO);
        BigDecimal costoMateriales = Optional.ofNullable(proyectoRepository.sumCostoMaterialesConsolidado()).orElse(BigDecimal.ZERO);
        BigDecimal otrosGastos = Optional.ofNullable(proyectoRepository.sumOtrosGastosDirectosConsolidado()).orElse(BigDecimal.ZERO);
        BigDecimal balanceFinanciero = montoRecibido.subtract(costoMateriales).subtract(otrosGastos);
        Double costoPromedioPorHora = Optional.ofNullable(empleadoRepository.findAvgCostoPorHora()).orElse(0.0);

        //NUEVAS CONSULTAS AÑADIDAS
        List<Object[]> clientesPorMes = clienteRepository.countClientesByMonthForYear(targetYear);
        List<Object[]> empleadosPorRol = empleadoRepository.countEmpleadosByRol();
        List<Object[]> horasPorProyecto = horasTrabajadasRepository.sumHorasByProyecto();
        List<Object[]> horasPorEmpleado = horasTrabajadasRepository.sumHorasByEmpleado();
        List<Object[]> proyectosPorEstado = proyectoRepository.countProyectosByEstado();
        List<Object[]> horasPorEmpleadoProyecto = horasTrabajadasRepository.sumHorasByEmpleadoAndProyecto();

        return new DashboardSummaryResponse(
            totalProyectos,
            empleadosActivos,
            balanceFinanciero,
            montoRecibido,
            costoMateriales,
            otrosGastos,
            clientesPorMes,
            empleadosPorRol,
            horasPorProyecto,
            horasPorEmpleado,
            proyectosPorEstado,
            horasPorEmpleadoProyecto,
            costoPromedioPorHora
        );
    }


    // ✅ CAMBIO: Nuevo método para obtener solo la lista de años.
    @Transactional(readOnly = true)
    public List<Integer> getAvailableYears() {
        return clienteRepository.findDistinctYears();
    }
}