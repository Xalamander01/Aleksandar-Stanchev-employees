package org.businessLogic;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUI implements ActionListener {

    static JLabel fileNameLabel = new JLabel((String) null);
    static JFrame windowFrame;
    static JScrollPane scrollPane = new JScrollPane(null);

    public GUI() {
    }

    public static void renderUI() {

        /*
         * create a new window titled "Collaborations UI"
         * add a button and attach an action listener to it
         * add a label that will show the path to the chosen file
         */

        GUI gui = new GUI();

        windowFrame = new JFrame("Collaborations UI");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setSize(800, 550);

        JButton button1 = new JButton("Open CSV");
        button1.addActionListener(gui);

        windowFrame.getContentPane().add(BorderLayout.NORTH, button1);
        windowFrame.getContentPane().add(BorderLayout.CENTER, fileNameLabel);
        //windowFrame.getContentPane().add(BorderLayout.SOUTH, scrollPane);

        windowFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*
         * when triggered, if the "Open CSV" button is pressed (we don't have any other buttons),
         * open a file chooser window
         * if the user has successfully selected a file
         * update the label, populate the table model and render it in the window
         */
        if (e.getActionCommand().equals("Open CSV")) {

            JFileChooser fileChooser = new JFileChooser("src/test/csv");

            int state = fileChooser.showSaveDialog(null);
            if (state == JFileChooser.APPROVE_OPTION) {
                fileNameLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                FileTableModel fileTableModel = new FileTableModel(fileChooser.getSelectedFile().getAbsolutePath());
                JTable table = new JTable(fileTableModel);
                windowFrame.remove(scrollPane);
                scrollPane = new JScrollPane(table);
                windowFrame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
            }
        }
    }

    /*
     * Create an abstract table model to hold the data which will be displayed in our table
     * implement each method of the AbstractTableModel method
     */
    static class FileTableModel extends AbstractTableModel {
        protected String filePath;
        protected Map<Integer, Collaboration> collaborationsMap;
        protected String[] columnNames = new String[]{"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};
        protected Class[] columnClasses = new Class[]{Integer.class, Integer.class, Integer.class, Long.class};

        public FileTableModel(String filePath) {
            this.filePath = filePath;
            Map<Integer, List<EmpIdAndDates>> projectAndEmployees = EmpIdAndDates.parseCSVFile(filePath);
            this.collaborationsMap = Collaboration.getCollaborationsMap(projectAndEmployees);
        }

        public int getColumnCount() {
            return columnNames.length;
        } // this is 4 always

        public int getRowCount() {
            return this.collaborationsMap.size(); // this changes depending on the number of projects in the csv file
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Class getColumnClass(int col) {
            return columnClasses[col];
        }

        // set the value of each cell accordingly
        public Object getValueAt(int row, int col) {
            Integer projectId = (Integer) collaborationsMap.keySet().toArray()[row];
            Collaboration collaboration = collaborationsMap.get(projectId);
            return switch (col) {
                case 0 -> collaboration.getFirstEmployeeId();
                case 1 -> collaboration.getSecondEmployeeId();
                case 2 -> projectId;
                case 3 -> collaboration.getDaysTogether();
                default -> null;
            };
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }
    }
}