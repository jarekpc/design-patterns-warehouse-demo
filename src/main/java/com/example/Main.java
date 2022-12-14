package com.example;

import com.example.cli.Cli;
import com.example.warehouse.*;
import com.example.warehouse.export.ExporterFactory;
import com.example.warehouse.export.FullExporterFactory;
import com.example.warehouse.export.TrialExporterFactory;
import com.example.warehouse.plot.ChartPlotterFactory;
import com.example.warehouse.plot.FullChartPlotterFactory;
import com.example.warehouse.plot.TrialChartPlotterFactory;
import com.example.web.Web;

import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final boolean FULL_VERSION = Boolean.valueOf(System.getProperty("FULL_VERSION", "true"));

    public static void main(String[] args) {
        List<String> arguments = List.of(args);

        checkClientId(arguments);
        int clientId = parseClientId(arguments.get(0));

        Warehouse warehouse = Warehouses.newDbWarehouse(clientId);

        List<ReportDelivery> reportDeliveries;
        try {
            reportDeliveries = createReportDeliveries(clientId);
        } catch (AddressException ex) {
            System.err.println("Wrong email address:" + ex.getMessage());
            System.exit(1);
            return;
        }

        ExporterFactory exporterFactory;
        ChartPlotterFactory plotterFactory;
        if (FULL_VERSION) {
            exporterFactory = new FullExporterFactory();
            plotterFactory = new FullChartPlotterFactory();
        } else {
            exporterFactory = new TrialExporterFactory();
            plotterFactory = new TrialChartPlotterFactory();
        }
        new Web(arguments, exporterFactory, plotterFactory, warehouse, reportDeliveries).run();
        new Cli(arguments, exporterFactory, plotterFactory, warehouse, reportDeliveries).run();
        // INFO: Needed because when Cli exists the Web
        // interface's thread will keep the app hanging.
        System.exit(0);
        System.exit(0);
    }

    private static List<ReportDelivery> createReportDeliveries(int clientId) throws AddressException {
        List<ReportDelivery> result = new ArrayList<>();
        result.add(new NoReportDelivery());
        if (clientId == 1) {
            result.add(new EmailReportDelivery("destination@demo.com"));
            result.add(new DirectoryReportDelivery("."));
        } else {
            result.add(new DirectoryReportDelivery("."));
        }
        return result;
    }

    private static void checkClientId(List<String> arguments) {
        if (arguments.size() < 1) {
            System.err.println("The client ID must be specialized");
            System.exit(1);
        }
    }

    private static int parseClientId(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException ex) {
            System.err.println(String.format("Illegal client id ", ex));
            System.exit(1);
            //
            return 0;
        }
    }
}
