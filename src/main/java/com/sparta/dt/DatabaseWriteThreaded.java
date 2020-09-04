package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseWriteThreaded implements Runnable {
    ArrayList<Employee> employees;
    Connection connection;
    static long threadStart;

    private static Logger logger = LogManager.getLogger(DatabaseWriteThreaded.class);

    public DatabaseWriteThreaded(Connection connection, ArrayList<Employee> array) {
        this.employees = array;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            long start = System.nanoTime();
            DatabaseAccess.writeToDB(connection, employees);
            long end = System.nanoTime();
            long timeMillis = (end - start) / 1000000;
            synchronized (this) {
                // stores time taken for current thread to complete since start of all threads (will correspond to total time taken to complete all threads once last thread reaches here
                StartProgram.arrayOfTimesMillis[1] = (end - threadStart) / 1000000;
            }
            logger.info(Thread.currentThread().getName() + " - Time taken to write partition to mySQL database: " + timeMillis + " milliseconds / " + (double) timeMillis / 1000 + " seconds");
        } catch (SQLException e) {
            logger.error("Exception occurred", e);
        }
    }

    public static void startThreads(Connection connection, ArrayList<ArrayList<Employee>> splitList) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        int threadNumber = 0;
        for (ArrayList array : splitList) {
            threadNumber++;
            DatabaseWriteThreaded databaseWriteThreaded = new DatabaseWriteThreaded(connection, array);
            Thread thread = new Thread(databaseWriteThreaded);
            thread.setName("SQL Write Thread " + threadNumber);
            threads.add(thread);
        }
        threadStart = System.nanoTime(); // take this time as start time for all threads
        for (Thread t : threads) {
            t.start();
        }

        // waits for all threads to complete before main thread can continue

        if (threadNumber == 1) { // case where no partitions are created, waits for single thread
            while (threads.get(0).isAlive()) {
                Thread.sleep(1);
            }
        } else {
            // all other cases have 10 threads
            while (threads.get(0).isAlive() || threads.get(1).isAlive() || threads.get(2).isAlive() || threads.get(3).isAlive() || threads.get(4).isAlive() || threads.get(5).isAlive() || threads.get(6).isAlive() || threads.get(7).isAlive() || threads.get(8).isAlive() || threads.get(9).isAlive()) {
                Thread.sleep(1);
            }
        }
    }
}
