package com.patrones.u2;

/** Contrato del encabezado y pie de página del reporte. */
public interface ReportHeaderFooter {
    String renderHeader(String institutionName);
    String renderFooter(int pageNumber);
}
