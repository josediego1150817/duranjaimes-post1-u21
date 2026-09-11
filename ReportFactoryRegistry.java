package com.patrones.u2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/** Registro extensible de fábricas, sin switch ni cadena de if/else. */
public final class ReportFactoryRegistry {
    private static final Map<String, Supplier<ReportFormatFactory>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("pdf", PdfReportFactory::new);
        REGISTRY.put("excel", ExcelReportFactory::new);
        REGISTRY.put("html", HtmlReportFactory::new);
    }

    private ReportFactoryRegistry() {
        // Utility class: no representa un objeto de dominio ni un Singleton GoF.
    }

    public static void register(String format, Supplier<ReportFormatFactory> factory) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("format no puede ser vacío");
        }
        if (factory == null) {
            throw new IllegalArgumentException("factory no puede ser null");
        }
        REGISTRY.put(format.toLowerCase(), factory);
    }

    public static ReportFormatFactory resolve(String format) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("format no puede ser vacío");
        }
        Supplier<ReportFormatFactory> factory = REGISTRY.get(format.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException(
                    "Formato de reporte no registrado: " + format +
                    ". Formatos disponibles: " + REGISTRY.keySet());
        }
        return factory.get();
    }
}
