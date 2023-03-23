package org.businessLogic;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * this is a triplet of employee id, start date and end date
 * this is intended to be the value of a map
 * the keys of this map will be project ids
 * this way it is easier to iterate through each project
 * and find the longest collaboration between two employees
 */
public class EmpIdAndDates {
    private Integer employeeId;
    private Date dateFrom;
    private Date dateTo;

    public static Map<Integer, List<EmpIdAndDates>> parseCSVFile(String filePath) {

        Map<Integer,List<EmpIdAndDates>> projectEmployeeMap = new HashMap<>();

        try {
            DateParser dateParser = new DateParser();
            SimpleDateFormat startDateFormat;
            SimpleDateFormat endDateFormat;

            //initialize readers
            FileReader reader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(reader);
            String[] line;

            // skip the first line which contains the column names
            csvReader.readNext();
            //iterate through the rest of the lines in the CSV file
            while ((line = csvReader.readNext()) != null) {

                // find the format of the start date, it may not be the same as the format of the end date
                startDateFormat = new SimpleDateFormat(dateParser.parseDateString(line[2]));
                // find the format of the end date, end date may be NULL
                // in that case, get a default value
                if (line[3].equals("NULL")) {
                    endDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                } else {
                    endDateFormat = new SimpleDateFormat(dateParser.parseDateString(line[3]));
                }

                /*
                 * create a new object to hold the data extracted from the CSV file
                 * populate its fields with the contents of the cells of each row
                 * if end date is "NULL", put today's date instead
                 * add the new object to the map which we will iterate through to get our final results
                 */

                EmpIdAndDates newEmployee = new EmpIdAndDates();
                newEmployee.setEmployeeId(Integer.parseInt(line[0]));
                Integer projectId = Integer.parseInt(line[1]);
                newEmployee.setDateFrom(startDateFormat.parse(line[2]));

                new DateParser().parseDateString(line[2]);

                if (line[3].equals("NULL")) {
                    newEmployee.setDateTo(endDateFormat.parse(java.time.LocalDate.now().toString()));
                } else {
                    newEmployee.setDateTo(endDateFormat.parse(line[3]));
                }

                if ( projectEmployeeMap.containsKey(projectId) ) {
                    projectEmployeeMap.get(projectId).add(newEmployee);
                } else {
                    List<EmpIdAndDates> empIdAndDatesList = new ArrayList<>();
                    empIdAndDatesList.add(newEmployee);
                    projectEmployeeMap.put(projectId, empIdAndDatesList);
                }
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            throw new RuntimeException(e);
        }
        return projectEmployeeMap;
    }

    //=========================constructors, getters and setters below this line========================
    public EmpIdAndDates() {
    }

    public EmpIdAndDates(Integer employeeId, Date dateFrom, Date dateTo) {
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
        return "EmpIdAndDates{" +
                "employeeId=" + employeeId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}