package org.businessLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI implements ActionListener {

    static JLabel fileNameLabel;

    public GUI() {
    }

    public static void renderUI() {

        GUI gui = new GUI();

        JFrame windowFrame = new JFrame("Collaborations UI");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setSize(600, 550);
        windowFrame.setVisible(true);

        JButton button1 = new JButton("Open CSV");
        button1.addActionListener(gui);
        JPanel fileChooserPanel = new JPanel();
        fileChooserPanel.add(button1);
        fileNameLabel = new JLabel((String) null);
        fileChooserPanel.add(fileNameLabel);

        JPanel tablePanel = new JPanel();
        String[] colNames = new String[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};
        String[][] tableData = getTableData(fileNameLabel.getText());
        JTable table = new JTable(tableData, colNames);
        table.setBounds(30, 40, 200, 300);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        //tablePanel.add(processTableData(fileNameLabel.getText()));

        windowFrame.getContentPane().add(BorderLayout.NORTH, fileChooserPanel);
        windowFrame.getContentPane().add(BorderLayout.SOUTH, tablePanel);
    }

    private static JScrollPane processTableData(String filePath) {
        String[] colNames = new String[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};
        String[][] tableData = getTableData(filePath);
        JTable table = new JTable(tableData, colNames);
        table.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private static String[][] getTableData(String filePath) {
        /*
         * take the desired file path and
         * return a 2d array which can be displayed
         * in a table
         */
        String[][] tableData = new String[0][];
        if (filePath != null) {
            Map<Integer, List<EmployeeIdAndDates>> projectAndEmployees = EmployeeIdAndDates.parseCSVFile(filePath);
            Map<Integer, Collaboration> collaborationsMap = Collaboration.getCollaborationsMap(projectAndEmployees);

            List<String[]> list = new ArrayList<>();

            for (Integer projectId : collaborationsMap.keySet()) {
                Collaboration currentCollab = collaborationsMap.get(projectId);
                String[] tableRow = {String.valueOf(currentCollab.getFirstEmployeeId()),
                        String.valueOf(currentCollab.getSecondEmployeeId()),
                        String.valueOf(projectId),
                        String.valueOf(currentCollab.getDaysTogether())};
                list.add(tableRow);
            }
            tableData = list.toArray(new String[0][]);
        }
        return tableData;
    }

    public void actionPerformed(ActionEvent e) {

        /*
         * if the user has successfully selected a new csv file
         * update the file name label
         * otherwise do nothing
         */
        if (e.getActionCommand() == "Open CSV") {

            JFileChooser j = new JFileChooser("src/test/csv");

            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                fileNameLabel.setText(j.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
