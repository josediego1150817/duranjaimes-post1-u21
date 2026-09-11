package com.patrones.u2;

/** Fabrica abstracta de la familia cuerpo + encabezado/pie. */
public interface ReportFormatFactory {
    ReportBody createBody();
    ReportHeaderFooter createHeaderFooter();
}
