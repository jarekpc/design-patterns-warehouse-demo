package com.example.warehouse;

public interface ReportGeneration {

    Report generateReport(Report.Type type);

    default void checkReportType(Report.Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Report type cannot be null.");
        }
        if (type != Report.Type.DAILY_REVENUE) {
            throw new UnsupportedOperationException(String.format("Report type: %s not yet implemented.", type));
        }
    }
}
