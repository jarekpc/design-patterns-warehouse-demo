package com.example.warehouse;

import com.example.warehouse.dal.OrderDao;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class AlternativeReportGeneration implements ReportGeneration{

    private final OrderDao orderDao;

    public AlternativeReportGeneration(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Report generateReport(Report.Type type) {
        checkReportType(type);
        final Report report = new Report();
        report.addLabel("Date");
        report.addLabel("Total products");
        report.addLabel("Total revenue");

        orderDao.getOrders()
                .stream()
                .sorted()
                .collect(groupingBy(Order::getDate, LinkedHashMap::new, toList()))
                .forEach((date, orders) -> report.addRecord(Arrays.asList(
                        date,
                        orders
                                .stream()
                                .sorted()
                                .map(Order::getQuantities)
                                .map(Map::values)
                                .flatMap(Collection::stream)
                                .mapToInt(Integer::intValue)
                                .sum(),
                        orders
                                .stream()
                                .sorted()
                                .mapToInt(Order::getTotalPrice)
                                .sum()
                )));
        return report;
    }
}
