package com.example.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Report {

    public enum Type {
        DAILY_REVENUE;
    }

    private List<String> labels;
    private List<List<String>> records;

    public Report() {
        this.labels = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    void addLabel(String label){
        labels.add(label);
    }

    void addRecord(List<Object> record){
        records.add(record.stream().map(String::valueOf).collect(Collectors.toList()));
    }
}
