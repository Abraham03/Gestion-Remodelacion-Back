package com.GestionRemodelacion.gestion.service.dashboard;

import com.GestionRemodelacion.gestion.empleado.repository.EmpleadoRepository;
import com.GestionRemodelacion.gestion.dto.response.DashboardSummaryResponse;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.proyecto.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

@Service
public class DashboardService {

    private final ProyectoRepository proyectoRepository;
    private final EmpleadoRepository empleadoRepository;

    // Inyección de dependencias a través del constructor (sin @RequiredArgsConstructor)
    public DashboardService(ProyectoRepository proyectoRepository, EmpleadoRepository empleadoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public DashboardSummaryResponse getDashboardSummary() {
        Long totalProyectos = proyectoRepository.count();
        Long empleadosActivos = empleadoRepository.countByActivo(true);

        BigDecimal montoRecibido = Optional.ofNullable(proyectoRepository.sumMontoRecibido()).orElse(BigDecimal.ZERO);
        BigDecimal costoMateriales = Optional.ofNullable(proyectoRepository.sumCostoMaterialesConsolidado()).orElse(BigDecimal.ZERO);
        BigDecimal otrosGastos = Optional.ofNullable(proyectoRepository.sumOtrosGastosDirectosConsolidado()).orElse(BigDecimal.ZERO);

        BigDecimal balanceFinanciero = montoRecibido.subtract(costoMateriales).subtract(otrosGastos);

        // Creación de la instancia sin el patrón builder
        return new DashboardSummaryResponse(totalProyectos, empleadosActivos, balanceFinanciero);
    }

    public List<Proyecto> getProyectosEnCurso() {
        return proyectoRepository.findByEstado(Proyecto.EstadoProyecto.EN_PROGRESO);
    }
}