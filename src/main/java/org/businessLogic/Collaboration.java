package org.businessLogic;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Collaboration {
    Integer firstEmployeeId;
    Integer secondEmployeeId;
    long daysTogether;

    public static Map<Integer,Collaboration> getCollaborationsMap(Map<Integer, List<EmployeeIdAndDates>> projectAndEmployees) {
        Map<Integer,Collaboration> collaborationsMap = new HashMap<>();

        /*
         * for each project for each employee
         * check if this employee has worked together with any of the following employees
         * on a specific project
         * check if any other couple of employees has collaborated more than the couples we discover
         */
        for ( Integer projectId : projectAndEmployees.keySet()) {

            List<EmployeeIdAndDates> employeesInProject = projectAndEmployees.get(projectId);
            for ( EmployeeIdAndDates employeeIdAndDates : employeesInProject) {

                // explicitly declare each parameter to avoid confusion
                EmployeeIdAndDates firstEmployee = employeeIdAndDates;
                int firstEmployeeIndex = employeesInProject.indexOf(firstEmployee);
                int firstEmployeeId = firstEmployee.getEmployeeId();
                long firstEmployeeStartTime = firstEmployee.getDateFrom().getTime();
                long firstEmployeeEndTime = firstEmployee.getDateTo().getTime();

                for ( int i=firstEmployeeIndex+1; i<employeesInProject.size() ; i++) {

                    // explicitly declare each parameter to avoid confusion
                    int secondEmployeeIndex = i;
                    EmployeeIdAndDates secondEmployee = employeesInProject.get(secondEmployeeIndex);
                    Integer secondEmployeeId = secondEmployee.getEmployeeId();
                    long secondEmployeeStartTime = secondEmployee.getDateFrom().getTime();
                    long secondEmployeeEndTime = secondEmployee.getDateTo().getTime();

                    /*
                     * if employee 2 started work before employee 1's work ended
                     * or vice versa
                     * then they've collaborated on a project
                     */
                    if ( firstEmployeeEndTime > secondEmployeeStartTime
                            && firstEmployeeStartTime < secondEmployeeEndTime ) {
                        Date maxStartDate = firstEmployeeStartTime > secondEmployeeStartTime ? firstEmployee.getDateFrom() : secondEmployee.getDateFrom() ;
                        Date minEndDate = firstEmployeeEndTime > secondEmployeeEndTime ? secondEmployee.getDateTo() : firstEmployee.getDateTo() ;
                        long millisTogether = Math.abs(minEndDate.getTime() - maxStartDate.getTime());
                        long daysTogether = TimeUnit.DAYS.convert(millisTogether, TimeUnit.MILLISECONDS);

                        if ( (collaborationsMap.containsKey(projectId) && daysTogether > collaborationsMap.get(projectId).getDaysTogether() )
                                    || !collaborationsMap.containsKey(projectId) ) {
                            collaborationsMap.put(projectId,new Collaboration(firstEmployeeId, secondEmployeeId, daysTogether));
                        }
                    }
                }
            }
        }
        return collaborationsMap;
    }


    //=========================constructors, getters and setters below this line========================
    public Collaboration() {
    }

    public Collaboration(Integer firstEmployeeId, Integer secondEmployeeId, long daysTogether) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.daysTogether = daysTogether;
    }

    public Integer getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(Integer firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public Integer getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(Integer secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public long getDaysTogether() {
        return daysTogether;
    }

    public void setDaysTogether(long daysTogether) {
        this.daysTogether = daysTogether;
    }

    @Override
    public String toString() {
        return "Collaboration{" +
                "firstEmployeeId=" + firstEmployeeId +
                ", secondEmployeeId=" + secondEmployeeId +
                ", daysTogether=" + daysTogether +
                '}';
    }
}