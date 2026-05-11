import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ProjectFormPanel extends JPanel {

    private JTextField projectNameField;
    private JTextField teamLeaderField;
    private JComboBox<String> teamSizeBox;
    private JComboBox<String> projectTypeBox;
    private JTextField startDateField;

    public ProjectFormPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        projectNameField = new JTextField();
        teamLeaderField = new JTextField();

        String[] teamSizes = {"Select Team Size", "1-3", "4-6", "7-10", "10+"};
        teamSizeBox = new JComboBox<>(teamSizes);

        String[] projectTypes = {"Select Project Type", "Web", "Mobile", "Desktop", "API"};
        projectTypeBox = new JComboBox<>(projectTypes);

        startDateField = new JTextField();

        addFormRow(gbc, 0, "Project Name:", projectNameField);
        addFormRow(gbc, 1, "Team Leader:", teamLeaderField);
        addFormRow(gbc, 2, "Team Size:", teamSizeBox);
        addFormRow(gbc, 3, "Project Type:", projectTypeBox);
        addFormRow(gbc, 4, "Start Date (DD/MM/YYYY):", startDateField);

        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        saveButton.addActionListener((ActionEvent e) -> saveProject());
        clearButton.addActionListener((ActionEvent e) -> clearForm());
    }

    private void addFormRow(GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        add(component, gbc);
    }

    private void saveProject() {
        String projectName = projectNameField.getText().trim();
        String teamLeader = teamLeaderField.getText().trim();
        String teamSize = (String) teamSizeBox.getSelectedItem();
        String projectType = (String) projectTypeBox.getSelectedItem();
        String startDate = startDateField.getText().trim();

        if (projectName.isEmpty() || teamLeader.isEmpty() || startDate.isEmpty()
                || teamSizeBox.getSelectedIndex() == 0
                || projectTypeBox.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String recordTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("projects.txt", true))) {
            writer.write("=== Project Entry ===");
            writer.newLine();
            writer.write("Project Name : " + projectName);
            writer.newLine();
            writer.write("Team Leader : " + teamLeader);
            writer.newLine();
            writer.write("Team Size : " + teamSize);
            writer.newLine();
            writer.write("Project Type : " + projectType);
            writer.newLine();
            writer.write("Start Date : " + startDate);
            writer.newLine();
            writer.write("Record Time : " + recordTime);
            writer.newLine();
            writer.write("====================");
            writer.newLine();
            writer.newLine();

            JOptionPane.showMessageDialog(
                    this,
                    "Project saved successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while saving the file.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        projectNameField.setText("");
        teamLeaderField.setText("");
        startDateField.setText("");
        teamSizeBox.setSelectedIndex(0);
        projectTypeBox.setSelectedIndex(0);
    }
}