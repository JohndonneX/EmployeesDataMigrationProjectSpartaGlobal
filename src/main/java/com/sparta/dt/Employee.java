package com.sparta.dt;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Employee {

    String id, prefix, firstName, middleInitial, lastName, gender, email, dateOfBirthString, dateOfJoiningString;
    Date dateOfBirth, dateOfJoining;
    String salaryString;
    int salary;

    public Employee(String id, String prefix, String firstName, String middleInitial, String lastName, String gender, String email, String dateOfBirthString, String dateOfJoiningString, String salary) {
        this.id = id;
        this.prefix = prefix;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        // store dates as string first
        this.dateOfBirthString = dateOfBirthString;
        this.dateOfJoiningString = dateOfJoiningString;

        this.salaryString = salary;
    }

    // converts date strings to sql.date type
    // assumes csv date formatting is consistent with employees.csv given to test project
    public void stringDateToSQLDate() throws DateTimeParseException, NullPointerException {
        LocalDate dateOfBirthAsLocalDate = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ofPattern("M/d/yyyy"));
        dateOfBirth =  Date.valueOf(dateOfBirthAsLocalDate);
        LocalDate dateOfJoiningAsLocalDate = LocalDate.parse(dateOfJoiningString, DateTimeFormatter.ofPattern("M/d/yyyy"));
        dateOfJoining =  Date.valueOf(dateOfJoiningAsLocalDate);
    }

    public void salaryToInt() {
        salary = Integer.parseInt(salaryString);
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id = '" + id + '\'' +
                ", prefix = '" + prefix + '\'' +
                ", firstName = '" + firstName + '\'' +
                ", middleInitial = '" + middleInitial + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", gender = '" + gender + '\'' +
                ", email = '" + email + '\'' +
                ", dateOfBirth = '" + dateOfBirth + '\'' +
                ", dateOfJoining = '" + dateOfJoining + '\'' +
                ", salary = '" + salaryString + '\'' +
                '}';
    }

    // check if employee fields are not null (assumes all employees must have a salary greater than 0)
    public boolean allFieldsNotNull() {
        boolean notNull = (this.getId() != null && this.getPrefix() != null && this.getFirstName() != null && this.getMiddleInitial() != null && this.getLastName() != null && this.getGender() != null && this.getEmail() != null && this.getDateOfBirth() != null && this.getDateOfJoining() != null && this.salary > 0);
        return notNull;
    }

    // check if any employee string fields are blank
    public boolean allStringsNotBlank() {
        boolean notBlank = (!this.getId().isBlank() && !this.getPrefix().isBlank() && !this.getFirstName().isBlank() && !this.getMiddleInitial().isBlank() && !this.getLastName().isBlank() && !this.getGender().isBlank() && !this.getEmail().isBlank());
        return notBlank;
    }

    // email must contain only one "@" and at least one "."
    public boolean emailIsProperlyFormatted() {
        if (this.getEmail().contains("@") && this.getEmail().indexOf("@") == this.getEmail().lastIndexOf("@") && this.getEmail().contains(".")) {
            return true;
        }
        return false;
    }

    public boolean isEmployeeValid() {
        try {
            stringDateToSQLDate(); // convert date strings to sql.date type
            salaryToInt(); // convert salary string to int
        } catch (Exception e) { // if cannot perform the above operations
            return false;
        }
        if (allFieldsNotNull() && allStringsNotBlank() && emailIsProperlyFormatted()) {
            return true;
        }
        return false;
    }
}
