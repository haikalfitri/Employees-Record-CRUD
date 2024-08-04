import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Employer_Form extends JFrame {
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldEmployerNumber;
    private JTextField textFieldDepartment;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton saveButton;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    //to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Employer_Form());
    }

    public Employer_Form() {
        setTitle("Employees Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setup main panel
        JPanel employeeFormPanel = new JPanel(new GridBagLayout());

        //setup gbc in gbl
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth =2; //column span
        gbc.insets = new Insets(10,10,10,10); //padding

        //title
        JLabel titleLabel = new JLabel ("Employees Record");
        titleLabel.setFont(new Font("Times New Roman",Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        employeeFormPanel.add(titleLabel, gbc);
        
        //move to the next row
        gbc.gridy++;
        gbc.gridwidth = 1;

        Font labelFont = new Font("Verdana", Font.PLAIN, 20);

        //first name label
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        employeeFormPanel.add(firstNameLabel,gbc); 

        //text field for first name
        textFieldFirstName = new JTextField(20);
        textFieldFirstName.setPreferredSize(new Dimension(200, 30));
        gbc.gridx=1;
        employeeFormPanel.add(textFieldFirstName,gbc); //add text field to the form

        //move to the next row
        gbc.gridy++;
        gbc.gridx = 0;

        //last name label
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        employeeFormPanel.add(lastNameLabel,gbc);

        //text field for last name
        textFieldLastName = new JTextField(20);
        textFieldLastName.setPreferredSize(new Dimension(200,30));
        gbc.gridx=1;
        employeeFormPanel.add(textFieldLastName,gbc);
        
        //move to next row
        gbc.gridy++;
        gbc.gridx = 0;

        //employer number label
        JLabel employeesNumberLabel = new JLabel ("Employer Number:");
        employeesNumberLabel.setFont(labelFont);
        employeeFormPanel.add(employeesNumberLabel,gbc);

        //text field for employer number
        textFieldEmployerNumber = new JTextField(20);
        textFieldEmployerNumber.setPreferredSize(new Dimension(200,30));
        gbc.gridx=1;
        employeeFormPanel.add(textFieldEmployerNumber,gbc);

        //move to next row
        gbc.gridy++;
        gbc.gridwidth = 0;

        //department label
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(labelFont);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        employeeFormPanel.add(departmentLabel, gbc);

        //text field for department
        textFieldDepartment = new JTextField(20);
        textFieldDepartment.setPreferredSize(new Dimension(200,30));

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        employeeFormPanel.add(textFieldDepartment, gbc);

        gbc.gridy++;
        gbc.gridx = 0; 

        //create Employer_Form button
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        saveButton = new JButton("Save");

        //button size
        Dimension buttonSize = new Dimension(120,30);
        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        //add button to form
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        employeeFormPanel.add(addButton, gbc);

        gbc.gridx = 1;
        employeeFormPanel.add(updateButton, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        employeeFormPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        employeeFormPanel.add(searchButton, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        employeeFormPanel.add(saveButton, gbc);

        gbc.gridy++;
        gbc.gridx=0;
        gbc.gridwidth=2;

        //create table to display employees
        String[] columnNames = {"First Name","Last Name", "Employer Num", "Department"};
        tableModel = new DefaultTableModel(columnNames,0);
        dataTable = new JTable (tableModel);
        dataTable.setFont(new Font("Times New Roman",Font.PLAIN,20));
        dataTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,20));
        dataTable.setRowHeight(30);


        JScrollPane scrollPane = new JScrollPane (dataTable);
        scrollPane.setPreferredSize(new Dimension(600,300));
        employeeFormPanel.add(scrollPane,gbc);

        //action listener for button
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addEmployer();
            }
        });

        updateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updateEmployer();
            }
        });

        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                deleteEmployer();
            }
        });

        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                searchEmployer();
            }
        });

        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                saveData();
            }
        });

        loadData(); //load data saved into the table from file
        add(employeeFormPanel);

        pack(); //frame size
        setLocationRelativeTo(null); //to center the frame size
        setVisible(true);
    }

    //method to check if employer exist
    private boolean isEmployerExist(String employerNumber) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(employerNumber)) {
                return true;
            }
        }
        return false;
    }

    //method to add employer to the table
    private void addEmployer() {
        //retrieve text input from text field
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String employerNumber = textFieldEmployerNumber.getText();
        String department = textFieldDepartment.getText();

        //prompt message for action
        if (isEmployerExist(employerNumber)) {
            JOptionPane.showMessageDialog(null, "The employer you enter already exist. Please try again.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tableModel.addRow(new Object[] {firstName, lastName, employerNumber, department}); //add data into table model
        clearFields(); //clear text field for next row
    }

    private void clearFields() {
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldEmployerNumber.setText("");
        textFieldDepartment.setText("");
    }    

    //method to update employer to the table
    private void updateEmployer() {
        //retrieve the currently selected row in table
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) { //show error message if there is no row selected
            JOptionPane.showMessageDialog(this, "Please select the employer you wish to update the record.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //retrieved previous filled text from selected row
        JTextField firstNameField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JTextField lastNameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField employerNumberField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField departmentField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));

        //create new panel to update form
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Employer Number:"));
        panel.add(employerNumberField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Employer record.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) { //condition for update
            //update the new datato the table
            tableModel.setValueAt(firstNameField.getText(), selectedRow, 0);
            tableModel.setValueAt(lastNameField.getText(), selectedRow, 1);
            tableModel.setValueAt(employerNumberField.getText(), selectedRow, 2);
            tableModel.setValueAt(departmentField.getText(), selectedRow, 3);
        }
    }
    
    //method to delete employer from the table
    private void deleteEmployer() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select the employer you wish to delete.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure to delete this employer?", "Delete Employer", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    //method to search data from the table by employer number
    private void searchEmployer() {
        JTextField employerNumberField = new JTextField();
        
        //create panel for search input
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Employer Number:"));
        panel.add(employerNumberField);

        //confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "Search Employer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            //retrieve the input from the the text field
            String employerNumber = employerNumberField.getText();
            
            //search the data from the table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 2).equals(employerNumber)) { //check if the data match from the table
                    dataTable.setRowSelectionInterval(i, i); //select row with matching data
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Employer not found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //method to save the table data to csv 
    private void saveData() {
        // Automatically close the file after writing, even if an error occurs
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employer_data.csv"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    //write the data from the table model to the saved file
                    writer.write((String) tableModel.getValueAt(i, j));

                    //write a comma to separate value for the last column
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine(); //move to next line 
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Save Data", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            //show an error message if IOException occur
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //load data from csv into table model
    private void loadData() {
        //try-with-resources to automatically close the file
        try (BufferedReader reader = new BufferedReader(new FileReader("employer_data.csv"))) {
            String fileLine; //variable to hold each line read from file

            //read each line from the file until eof
            while ((fileLine = reader.readLine()) != null) { 
                String[] csvData = fileLine.split(",");  //split line by comma to separate data for the table for each cell in each row
                tableModel.addRow(csvData); //add the row of data into table
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading csv file: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
