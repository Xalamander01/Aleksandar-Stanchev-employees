package org.businessLogic;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeeIdAndDates {
    private Integer employeeId;
    private Date dateFrom;
    private Date dateTo;


    public static Map<Integer, List<EmployeeIdAndDates>> parseCSVFile(String filePath) {

        Map<Integer,List<EmployeeIdAndDates>> projectEmployeeMap = new HashMap<>();

        try {
            //currently the only supported date format
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            //initialize readers
            FileReader reader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(reader);
            String[] line;

            // skip the first line which contains the column names
            csvReader.readNext();
            //iterate through the rest of the lines in the CSV file
            while ((line = csvReader.readNext()) != null) {

                /*
                 * create a new object to hold the data extracted from the CSV file
                 * populate its fields with the contents of the cells of each row
                 * if end date is "NULL", put today's date instead
                 */

                EmployeeIdAndDates newEmployee = new EmployeeIdAndDates();
                newEmployee.setEmployeeId(Integer.parseInt(line[0]));
                Integer projectId = Integer.parseInt(line[1]);
                newEmployee.setDateFrom(format1.parse(line[2]));
                if (line[3].equals("NULL")) {
                    newEmployee.setDateTo(format1.parse(java.time.LocalDate.now().toString()));
                } else {
                    newEmployee.setDateTo(format1.parse(line[3]));
                }

                if ( projectEmployeeMap.containsKey(projectId) ) {
                    projectEmployeeMap.get(projectId).add(newEmployee);
                } else {
                    List<EmployeeIdAndDates> employeeIdAndDatesList = new ArrayList<>();
                    employeeIdAndDatesList.add(newEmployee);
                    projectEmployeeMap.put(projectId,employeeIdAndDatesList);
                }
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            throw new RuntimeException(e);
        }
        return projectEmployeeMap;
    }

    //=========================constructors, getters and setters below this line========================
    public EmployeeIdAndDates() {
    }

    public EmployeeIdAndDates(Integer employeeId, Date dateFrom, Date dateTo) {
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "EmployeeIdAndDates{" +
                "employeeId=" + employeeId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}