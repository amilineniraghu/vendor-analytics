package com.vendoranalytics.xyz.business;

import com.vendoranalytics.xyz.modal.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Process {

    // To Store cecid -> Employee object mapping
    HashMap<String, Employee> employeeHashMap = new HashMap<>();

    // To store final output before writing to Excel file
    ArrayList<Employee> employeeArrayList = new ArrayList<>();

    // To Store employee , manager mapping
    HashMap<String,String> stringHashMap = new HashMap<>();

    public void readAllFiles(String directoryPath){
        System.out.println("in readallfiles method");
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> extractEmployeeInformation(String.valueOf(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractEmployeeInformation(String fileName){
        System.out.println(fileName);
    }
}
