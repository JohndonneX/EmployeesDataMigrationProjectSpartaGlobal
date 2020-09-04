package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class WriteInvalidEmployeesToFile {

    private static Logger logger = LogManager.getLogger(WriteInvalidEmployeesToFile.class);

    public static void writeInvalidsToCSV() {
        try {
            logger.info("Writing invalid employee entries to invalidemployees.txt");
            BufferedWriter br = new BufferedWriter(new FileWriter("src/main/resources/invalidemployees.txt"));
            for (Employee e : EmployeesLoad.getInvalidEmployeeList()) {
                String output = e.getId() + "," + e.getPrefix() + "," +  e.getFirstName() + "," + e.getMiddleInitial() + "," + e.getLastName() + "," + e.getGender() + "," + e.getEmail() + "," + e.getDateOfBirth() + "," + e.getDateOfJoining() + "," + e.getSalary();
                br.write(output);
                br.newLine();
            }
            br.close();
            logger.info("Write complete");
        } catch (Exception e) {
            logger.error("Could not write invalid employee to file", e);
        }


    }



}
