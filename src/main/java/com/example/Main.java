package com.example;

import com.example.cli.Cli;
import com.example.web.Web;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new Web(List.of(args)).run();
        new Cli(Arrays.asList(args)).run();
        //
        System.exit(0);
    }
}
