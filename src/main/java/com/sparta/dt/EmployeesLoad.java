package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeesLoad {

    private static Logger logger = LogManager.getLogger(EmployeesLoad.class);


    static ArrayList<Employee> employeeList = new ArrayList<>();
    static ArrayList<Employee> invalidEmployeeList = new ArrayList<>();

    public static ArrayList<Employee> getInvalidEmployeeList() {
        return invalidEmployeeList;
    }

    public static ArrayList<Employee> loadEmployees() throws IOException {
        long start = System.nanoTime();
        logger.info("Attempting to read " + LoadProperties.csvFileName + ".csv...");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/" + LoadProperties.csvFileName + ".csv"));
        String line;
        bufferedReader.readLine(); // skip first line
        // read through the lines
        while ((line = bufferedReader.readLine()) != null) {
            String[] employeeData = getEachEmployeeData(line);
            Employee employee = new Employee(employeeData[0], employeeData[1], employeeData[2], employeeData[3], employeeData[4], employeeData[5], employeeData[6], employeeData[7], employeeData[8], employeeData[9]);
            // check validity of employee entry
            if (employee.isEmployeeValid()) {
                employeeList.add(employee);
            } else {
                invalidEmployeeList.add(employee);
            }
        }
        bufferedReader.close();
        long end = System.nanoTime();
        long time = (end - start) / 1000000;
        StartProgram.arrayOfTimesMillis[0] = time; // store this time to retrieve in main
        logger.info("Finished reading " + LoadProperties.csvFileName + ".csv");
        logger.info("Time taken to read " + LoadProperties.csvFileName + ".csv: " + time + " milliseconds / " + (double) time / 1000 + " seconds");
        logger.info("Number of valid employees identified: " + employeeList.size());
        logger.info("Number of invalid employees identified: " + invalidEmployeeList.size());
        return employeeList;
    }

    public static String[] getEachEmployeeData(String line) {
        String[] employeeData = line.split(",");
        // handles case where a row does not have the expected number of data entries (hardcoded as 10)
        if (employeeData.length != 10) {
            String[] employeeDataTen = Arrays.copyOf(employeeData, 10);
            return employeeDataTen;
        }
        return employeeData;
    }



}

