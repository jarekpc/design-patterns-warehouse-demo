package com.example;

import com.example.cli.Cli;
import com.example.warehouse.*;
import com.example.warehouse.dal.*;
import com.example.web.Web;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        final List<String> arguments = List.of(args);

        checkClientId(arguments);
        final int clientId = parseClientId(arguments.get(0));

        ProductDao productDao = new MemoryProductDao();
        CustomerDao customerDao = new DbCustomerDao();
        InventoryDao inventoryDao = new MemoryInventoryDao(productDao);
        OrderDao orderDao = new MemoryOrderDao(productDao, customerDao);

        ReportGeneration reportGeneration;
        if (clientId == 1) {
            reportGeneration = new DefaultReportGeneration(orderDao);
        } else if (clientId == 2) {
            reportGeneration = new AlternativeReportGeneration(orderDao);
        } else {
            throw new IllegalStateException("Unknown client ID: " + clientId);
        }

        Warehouse warehouse = new Warehouse(productDao, customerDao, inventoryDao, orderDao, reportGeneration);

        ReportDelivery reportDelivery = null;//TODO decide how implementation

        new Web(arguments, warehouse, reportDelivery).run();
        new Cli(arguments, warehouse, reportDelivery).run();

        System.exit(0);
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
