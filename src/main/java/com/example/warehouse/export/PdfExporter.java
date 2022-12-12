package com.example.warehouse.export;

import com.example.warehouse.Report;

import java.io.PrintStream;
import java.util.List;

public class PdfExporter extends AbstractExporter {

    public PdfExporter(Report report, PrintStream out) {
        super(report, out);
    }

    @Override
    protected void handleLabels(PrintStream out, List<String> labels) {
        //
    }

    @Override
    protected void handleRecord(PrintStream out, List<String> records, boolean first, boolean last) {
        //
    }
}
