package com.example.warehouse.plot;

import com.example.warehouse.Report;

import java.io.IOException;
import java.io.OutputStream;

public class DummyChartPlotter implements ChartPlotter {
    @Override
    public void plot(Report report, OutputStream out) throws IOException {
        throw new UnsupportedOperationException("Chart plotting not available.");
    }
}
