package com.GestionRemodelacion.gestion.controller.dashboard;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestionRemodelacion.gestion.dto.response.DashboardSummaryResponse;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.service.dashboard.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    // Inyección de dependencias a través del constructor (sin @RequiredArgsConstructor)
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/proyectos-en-curso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Proyecto>> getProyectosEnCurso() {
        List<Proyecto> proyectos = dashboardService.getProyectosEnCurso();
        return ResponseEntity.ok(proyectos);
    }
}