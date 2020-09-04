# Employees Data Migration Project
This program takes a .csv file containing employee data and migrates valid entries to a specified mySQL database.

The user can configure a login.properties file located in src/main/resources to provide the mySQL server details, login details, csv file name to read, and name of the table to migrate data to.

The .csv file is read and valid entries are inserted into the database and table specified. This will overwrite any existing table of the same name. Invalid entries are written to an invalidemployees.txt for manual review.

The time taken to perform the migration is reported to the user.