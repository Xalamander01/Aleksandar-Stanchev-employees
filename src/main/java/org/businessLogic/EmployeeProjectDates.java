package org.businessLogic;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * USE THIS IF YOU WANT TO CREATE AN OBJECT FOR EACH ROW OF THE CSV FILE
 */
public class EmployeeProjectDates {
    private Integer empId;
    private Integer projectId;
    private Date dateFrom;
    private Date dateTo;

    public static List<EmployeeProjectDates> parseCSVFile(String filePath) {

        List<EmployeeProjectDates> employeeProjectDatesList = new ArrayList<>();

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
                 * if end date is "NULL", put todays date instead
                 */
                EmployeeProjectDates newEmployee = new EmployeeProjectDates();

                newEmployee.setEmpId(Integer.parseInt(line[0]));
                newEmployee.setProjectId(Integer.parseInt(line[1]));
                newEmployee.setDateFrom(format1.parse(line[2]));
                if (line[3].equals("NULL")) {
                    newEmployee.setDateTo(format1.parse(java.time.LocalDate.now().toString()));
                } else {
                    newEmployee.setDateTo(format1.parse(line[3]));
                }
                employeeProjectDatesList.add(newEmployee);
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            throw new RuntimeException(e);
        }
        return employeeProjectDatesList;
    }


    //=========================constructors, getters and setters below this line========================

    public EmployeeProjectDates() {
    }

    public EmployeeProjectDates(Integer empId, Integer projectId, Date dateFrom, Date dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
        return "org.businessLogic.EmployeeProjectDates{" +
                "empId=" + empId +
                ", projectId=" + projectId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
