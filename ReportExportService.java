package com.patrones.u2;

import java.util.List;

public class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(format);
        return render(factory, records, institutionName, null);
    }

    public String export(ExportConfig config, List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(config.getFormat());
        return render(factory, records, institutionName, config);
    }

    private String render(ReportFormatFactory factory, List<GradeRecord> records,
                          String institutionName, ExportConfig config) {
        ReportBody body = factory.createBody();
        ReportHeaderFooter headerFooter = factory.createHeaderFooter();
        StringBuilder out = new StringBuilder();

        if (config != null) {
            out.append(String.format(
                    "[config] pageSize=%s orientation=%s locale=%s watermark=%s includeLogo=%s compress=%s maxRowsPerPage=%d%n",
                    config.getPageSize(), config.getOrientation(), config.getLocale(),
                    config.getWatermarkText() == null ? "ninguna" : config.getWatermarkText(),
                    config.isIncludeLogo(), config.isCompress(), config.getMaxRowsPerPage()));
        }

        out.append(headerFooter.renderHeader(institutionName)).append("\n");
        out.append(body.render(records));
        out.append(headerFooter.renderFooter(1));
        return out.toString();
    }
}
