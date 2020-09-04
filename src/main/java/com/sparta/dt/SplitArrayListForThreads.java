package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class SplitArrayListForThreads {
    private static Logger logger = LogManager.getLogger(SplitArrayListForThreads.class);

    public static ArrayList<ArrayList<Employee>> splitEmployeesList(ArrayList<Employee> unsplitList) {
        ArrayList<ArrayList<Employee>> splitListOfEmployees = new ArrayList<>();
        logger.info("Partitioning array of valid employees into 10 blocks...");
        // if employee list contains less than 100 entries do not split
        if (unsplitList.size() < 100) {
            splitListOfEmployees.add(unsplitList);
            logger.info("Number of valid employees less than 100, no partition performed");
            return splitListOfEmployees;
        }
        int originalSize = unsplitList.size();
        int numberOfPartitions = 0;
        int blockSize = originalSize / 10; // will round down to nearest integer
        for (int i = 0; i < originalSize; i += blockSize) {
            numberOfPartitions++;
            // when creating 10th partition, add all remaining employees
            if (numberOfPartitions == 10) {
                ArrayList<Employee> partition = new ArrayList<>(unsplitList.subList(i, originalSize));
                splitListOfEmployees.add(partition);
                logger.info("Partition " + numberOfPartitions + ": " + partition.size() + " employees");
                break;
            }
            // partition as normal
            ArrayList<Employee> partition = new ArrayList<>(unsplitList.subList(i, i + blockSize));
            splitListOfEmployees.add(partition);
            logger.info("Partition " + numberOfPartitions + ": " + partition.size() + " employees");

        }
        return splitListOfEmployees;
    }


}
