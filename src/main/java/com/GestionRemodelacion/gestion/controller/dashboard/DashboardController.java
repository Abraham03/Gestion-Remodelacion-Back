package com.GestionRemodelacion.gestion.controller.dashboard;

import com.GestionRemodelacion.gestion.dto.response.DashboardSummaryResponse;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.service.dashboard.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    // Inyección de dependencias a través del constructor (sin @RequiredArgsConstructor)
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/proyectos-en-curso")
    public ResponseEntity<List<Proyecto>> getProyectosEnCurso() {
        List<Proyecto> proyectos = dashboardService.getProyectosEnCurso();
        return ResponseEntity.ok(proyectos);
    }
}