package com.sparta.dt;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;

public class StartProgram {

    private static Logger logger = LogManager.getLogger(StartProgram.class);

    static long[] arrayOfTimesMillis = new long[3];

    public static void start() {
        try {
            long startProgram = System.nanoTime();
            LoadProperties.loadProperties();
            Connection connection = DatabaseAccess.connectToDB();
            ArrayList<Employee> employees = EmployeesLoad.loadEmployees();
            WriteInvalidEmployeesToFile.writeInvalidsToCSV();
            ArrayList<ArrayList<Employee>> splitList = SplitArrayListForThreads.splitEmployeesList(employees);
            DatabaseAccess.setupTable(connection);
            DatabaseWriteThreaded.startThreads(connection, splitList);
            DatabaseAccess.queryCountFromDatabase(connection);
            // output times
            long endProgram = System.nanoTime();
            long totalTimeProgram = endProgram - startProgram;
            long totalTimeMillis = totalTimeProgram / 1000000;
            arrayOfTimesMillis[2] = totalTimeMillis;
            logger.info("Time taken to read " + LoadProperties.csvFileName + ".csv: " + arrayOfTimesMillis[0] + " milliseconds / " + (double) arrayOfTimesMillis[0] / 1000 + " seconds");
            logger.info("Time taken to insert " + EmployeesLoad.employeeList.size() + " employee data entries into database: " + arrayOfTimesMillis[1] + " milliseconds / " + (double) arrayOfTimesMillis[1] / 1000 + " seconds");
            logger.info("Overall Migration Time: " + arrayOfTimesMillis[2] + " milliseconds / " + (double) arrayOfTimesMillis[2] / 1000 + " seconds");

            logger.info("Number of invalid employee entries identified: " + EmployeesLoad.getInvalidEmployeeList().size());
            logger.info("All invalid employee data can be found in src/main/resources/invalidemployees.txt");


        } catch (CommunicationsException e) { // handles connection error
            logger.error("Could not connect to the mySQL database.");
        } catch (FileNotFoundException e) {
            logger.error("Could not find the .csv file specified in login.properties");
        } catch (Exception e) {
            logger.error("Exception occurred", e);
        }

    }

}
