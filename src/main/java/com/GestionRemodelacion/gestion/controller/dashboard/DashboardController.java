package com.GestionRemodelacion.gestion.controller.dashboard;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.dto.response.DashboardSummaryResponse;
import com.GestionRemodelacion.gestion.service.dashboard.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAuthority('DASHBOARD_VIEW')") 
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary(@RequestParam(name = "year", required = false) Integer year) {
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary(year);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    // ✅ CAMBIO: Nuevo endpoint para obtener solo los años.
    @GetMapping("/years")
    @PreAuthorize("hasAuthority('DASHBOARD_VIEW')")
    public ResponseEntity<ApiResponse<List<Integer>>> getAvailableYears() {
        List<Integer> years = dashboardService.getAvailableYears();
        return ResponseEntity.ok(ApiResponse.success(years));
    }
}