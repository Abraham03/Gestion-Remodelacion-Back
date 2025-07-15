package com.GestionRemodelacion.gestion.dto.response;

import java.math.BigDecimal;

public class DashboardSummaryResponse {
    private Long totalProyectos;
    private Long empleadosActivos;
    private BigDecimal balanceFinanciero;

    // Constructor con todos los campos
    public DashboardSummaryResponse(Long totalProyectos, Long empleadosActivos, BigDecimal balanceFinanciero) {
        this.totalProyectos = totalProyectos;
        this.empleadosActivos = empleadosActivos;
        this.balanceFinanciero = balanceFinanciero;
    }

    // Getters
    public Long getTotalProyectos() {
        return totalProyectos;
    }

    public Long getEmpleadosActivos() {
        return empleadosActivos;
    }

    public BigDecimal getBalanceFinanciero() {
        return balanceFinanciero;
    }

    // Setters (opcionales si solo se usa para respuesta y no se modifica después de la creación)
    public void setTotalProyectos(Long totalProyectos) {
        this.totalProyectos = totalProyectos;
    }

    public void setEmpleadosActivos(Long empleadosActivos) {
        this.empleadosActivos = empleadosActivos;
    }

    public void setBalanceFinanciero(BigDecimal balanceFinanciero) {
        this.balanceFinanciero = balanceFinanciero;
    }
}