package com.example.warehouse.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public static final String DEFAULT_SEPERATOR = ".";

    private final Scanner scanner;
    private final String seperator;

    public CsvReader(InputStream is){
        this(is, DEFAULT_SEPERATOR);
    }

    private CsvReader(InputStream is, String separator){
        this.scanner = new Scanner(is);
        this.seperator = separator;
    }

    public boolean hasNextRow(){
        return scanner.hasNextLine();
    }

    public List<String> nextRow(){
        String line = scanner.nextLine();
        if(line.isBlank()){
            return Collections.emptyList();
        }
        return Arrays.asList(line.split(seperator));
    }
}
