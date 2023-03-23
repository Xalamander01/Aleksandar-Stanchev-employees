package org.businessLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI {
    public static void renderUI(String csvFilePath) {

        JFrame windowFrame = new JFrame("Collaborations UI");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setSize(800, 400);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter CSV File Path");
        JTextField textField = new JTextField(50);
        JButton parse = new JButton("Parse");
        panel.add(label);
        panel.add(textField);
        panel.add(parse);

        String[] colNames = new String[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};
        String[][] tableData = getTableData(csvFilePath);

        JTable table = new JTable(tableData, colNames);
        table.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        windowFrame.getContentPane().add(BorderLayout.NORTH, panel);
        windowFrame.setVisible(true);
    }

    private static String[][] getTableData(String filePath) {

        Map<Integer, List<EmployeeIdAndDates>> projectAndEmployees = EmployeeIdAndDates.parseCSVFile(filePath);
        Map<Integer,Collaboration> collaborationsMap = Collaboration.getCollaborationsMap(projectAndEmployees);

        String[][] tableData;
        List<String[]> list = new ArrayList<>();

        for ( Integer projectId : collaborationsMap.keySet()) {
            Collaboration currentCollab = collaborationsMap.get(projectId);
            String[] tableRow = {String.valueOf(currentCollab.getFirstEmployeeId()),
                    String.valueOf(currentCollab.getSecondEmployeeId()),
                    String.valueOf(projectId),
                    String.valueOf(currentCollab.getDaysTogether())};
            list.add(tableRow);
        }
        tableData = list.toArray(new String[0][]);

        return tableData;
    }
}
