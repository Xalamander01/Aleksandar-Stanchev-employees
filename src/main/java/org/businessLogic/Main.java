package org.businessLogic;

public class Main {
    public static void main(String[] args) {

        /*
        Map<Integer, List<EmployeeIdAndDates>> projectAndEmployees = EmployeeIdAndDates.parseCSVFile("src/test/csv/SampleData.csv");
        Map<Integer,Collaboration> collaborationsMap = Collaboration.getCollaborationsMap(projectAndEmployees);
        for ( Integer projectId : collaborationsMap.keySet() ) {
            System.out.println(collaborationsMap.get(projectId));
        }

         */
        GUI.renderUI();
    }
}