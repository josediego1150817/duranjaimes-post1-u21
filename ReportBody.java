package com.patrones.u2;

import java.util.List;

/** Contrato del cuerpo del reporte. */
public interface ReportBody {
    String render(List<GradeRecord> records);
}
