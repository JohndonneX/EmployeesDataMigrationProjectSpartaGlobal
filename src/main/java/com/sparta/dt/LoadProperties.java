package com.sparta.dt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

    private static Logger logger = LogManager.getLogger(LoadProperties.class);

    public static String csvFileName, hostName, port, username, password, table;

    public static void loadProperties() throws IOException {
        logger.info("Loading login.properties...");
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/login.properties"));
        csvFileName = properties.getProperty("csvfilename");
        hostName = properties.getProperty("hostname");
        port = properties.getProperty("port");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        table = properties.getProperty("sqltable");
        logger.info("Successfully loaded login.properties");
    }
}
