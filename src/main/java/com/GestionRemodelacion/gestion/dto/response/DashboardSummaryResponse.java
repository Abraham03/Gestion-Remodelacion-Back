package com.GestionRemodelacion.gestion.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryResponse {

    // Métricas de resumen (las que ya tenías)
    private final Long totalProyectos;
    private final Long empleadosActivos;
    private final BigDecimal balanceFinanciero;
    private final BigDecimal montoRecibido;
    private final BigDecimal costoMateriales;
    private final BigDecimal otrosGastos;
    private final Double costoPromedioPorHora;
    // Nuevas métricas
    private final List<Object[]> clientesPorMes;
    private final List<Object[]> empleadosPorRol;
    private final List<Object[]> horasPorProyecto;
    private final List<Object[]> horasPorEmpleado;
    private final List<Object[]> proyectosPorEstado;
    private final List<Object[]> horasPorEmpleadoProyecto;

    // Constructor completo
    public DashboardSummaryResponse(Long totalProyectos, Long empleadosActivos, BigDecimal balanceFinanciero,BigDecimal montoRecibido, BigDecimal costoMateriales, BigDecimal otrosGastos,
                                 List<Object[]> clientesPorMes, List<Object[]> empleadosPorRol,
                                 List<Object[]> horasPorProyecto, List<Object[]> horasPorEmpleado,
                                 List<Object[]> proyectosPorEstado,
                                 List<Object[]> horasPorEmpleadoProyecto, Double costoPromedioPorHora) {
        this.totalProyectos = totalProyectos;
        this.empleadosActivos = empleadosActivos;
        this.balanceFinanciero = balanceFinanciero;
        this.montoRecibido = montoRecibido;
        this.costoMateriales = costoMateriales;
        this.otrosGastos = otrosGastos;
        this.clientesPorMes = clientesPorMes;
        this.empleadosPorRol = empleadosPorRol;
        this.horasPorProyecto = horasPorProyecto;
        this.horasPorEmpleado = horasPorEmpleado;
        this.proyectosPorEstado = proyectosPorEstado;
        this.horasPorEmpleadoProyecto = horasPorEmpleadoProyecto;
        this.costoPromedioPorHora = costoPromedioPorHora;
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

    public BigDecimal getMontoRecibido() {
        return montoRecibido;
    }

    public BigDecimal getCostoMateriales() {
        return costoMateriales;
    }

    public BigDecimal getOtrosGastos() {
        return otrosGastos;
    }

    public List<Object[]> getClientesPorMes() {
        return clientesPorMes;
    }

    public List<Object[]> getEmpleadosPorRol() {
        return empleadosPorRol;
    }

    public List<Object[]> getHorasPorProyecto() {
        return horasPorProyecto;
    }

    public List<Object[]> getHorasPorEmpleado() {
        return horasPorEmpleado;
    }

    public List<Object[]> getProyectosPorEstado() {
        return proyectosPorEstado;
    }

    public List<Object[]> getHorasPorEmpleadoProyecto() {
        return horasPorEmpleadoProyecto;
    }

    public Double getCostoPromedioPorHora() {
        return costoPromedioPorHora;
    }
}