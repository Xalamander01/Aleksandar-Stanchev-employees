package org.businessLogic;

public class Main {
    public static void main(String[] args) {

        /*
         * uncomment this section to get the results in the command line
        Map<Integer, List<EmpIdAndDates>> projectAndEmployees = EmpIdAndDates.parseCSVFile("src/test/csv/SampleData2.csv");
        Map<Integer,Collaboration> collaborationsMap = Collaboration.getCollaborationsMap(projectAndEmployees);
        for ( Integer projectId : collaborationsMap.keySet() ) {
            System.out.println(collaborationsMap.get(projectId));
        }
         */

        GUI.renderUI();
    }
}
