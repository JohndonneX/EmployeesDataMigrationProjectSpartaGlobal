package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseAccess {
    private static Logger logger = LogManager.getLogger(DatabaseAccess.class);

    public static Connection connectToDB() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + LoadProperties.hostName + ":" + LoadProperties.port + "/employeedatabase?serverTimezone=GMT", LoadProperties.username, LoadProperties.password);
        logger.info("Connected to mySQL database at " + LoadProperties.hostName + ":" + LoadProperties.port);
        return connection;
    }

    public static void setupTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // check whether table exists
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, LoadProperties.table, null);
        if (tables.next()) {
            // delete table and create new one in case existing table has wrong columns / to overwrite existing entries
            logger.info("Dropping table since it already exists...");
            statement.execute("DROP TABLE " + LoadProperties.table);
        }
        logger.info("Creating table with appropriate columns...");
        statement.execute("CREATE TABLE "+ LoadProperties.table + " (" +
                "emp_id VARCHAR(20)," +
                "    name_prefix VARCHAR(20)," +
                "    first_name VARCHAR(100)," +
                "    middle_initial VARCHAR(20)," +
                "    last_name VARCHAR(100)," +
                "    gender VARCHAR(20)," +
                "    email VARCHAR(100)," +
                "    date_of_birth DATE," +
                "    date_of_joining DATE," +
                "    salary INT" +
                "    )");
    }

    public static void writeToDB(Connection connection, ArrayList<Employee> employees) throws SQLException {
        // insert data
        Statement statement = connection.createStatement();
        String insertQuery = "INSERT INTO " + LoadProperties.table + " VALUES ";
        for (Employee e : employees) {
            // insert with comma at end
            insertQuery += "('" + e.getId() + "', '" + e.getPrefix() + "', '" +  e.getFirstName() + "', '" + e.getMiddleInitial() + "', '" + e.getLastName() + "', '" + e.getGender() + "', '" + e.getEmail() + "', '" + e.getDateOfBirth() + "', '" + e.getDateOfJoining() + "', '" + e.getSalary() + "'),";
        }
        // removes last comma
        insertQuery = insertQuery.substring(0, insertQuery.length() - 1 );
        statement.execute(insertQuery);
        logger.info(Thread.currentThread().getName() + " - " + employees.size() + " employees migrated to mySQL database");
    }

    public static void queryCountFromDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String queryCount = "SELECT count(*) FROM " + LoadProperties.table;
        ResultSet count = statement.executeQuery(queryCount);
        count.next();
        logger.info("MySQL database reports " + count.getInt(1) + " employee entries in table " + LoadProperties.table);
    }






}
