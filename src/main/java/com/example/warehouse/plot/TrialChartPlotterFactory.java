package com.example.warehouse.plot;

import com.example.warehouse.Report;

public class TrialChartPlotterFactory implements ChartPlotterFactory {
    @Override
    public ChartPlotter newPlotter(Report.Type reportType, ChartType chartType) {
        return new DummyChartPlotter();
    }
}
