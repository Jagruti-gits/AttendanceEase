import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttendanceGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JButton viewStudentsButton;
    private JButton viewAttendanceButton;
    private JButton addStudentButton;
    private JButton deleteStudentButton;
    private JButton takeAttendanceButton;
    private StudentManager studentManager;

    public AttendanceGUI() {
        Color darkBlue = new Color(25, 55, 109);
        Color lavender = new Color(230, 230, 250);
        Color buttonBlue = new Color(63, 114, 175);
        Color buttonLavender = new Color(150, 130, 200);
        Color buttonGreen = new Color(60, 140, 90);
        Color buttonRed = new Color(180, 60, 60);
        Color white = Color.WHITE;

        studentManager = new StudentManager();

        frame = new JFrame("AttendanceEase - Student Attendance Management");
        frame.setSize(760, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(darkBlue);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel titleLabel = new JLabel("AttendanceEase");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(white);

        JLabel subtitleLabel = new JLabel("Student Attendance Management System");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 210, 240));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(lavender);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 16));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        viewStudentsButton = new JButton("View Students");
        viewStudentsButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        viewStudentsButton.setBackground(buttonBlue);
        viewStudentsButton.setForeground(white);
        viewStudentsButton.setFocusPainted(false);
        viewStudentsButton.setPreferredSize(new Dimension(155, 40));

        viewAttendanceButton = new JButton("View Attendance");
        viewAttendanceButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        viewAttendanceButton.setBackground(buttonLavender);
        viewAttendanceButton.setForeground(white);
        viewAttendanceButton.setFocusPainted(false);
        viewAttendanceButton.setPreferredSize(new Dimension(155, 40));

        addStudentButton = new JButton("Add Student");
        addStudentButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        addStudentButton.setBackground(buttonGreen);
        addStudentButton.setForeground(white);
        addStudentButton.setFocusPainted(false);
        addStudentButton.setPreferredSize(new Dimension(155, 40));

        deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        deleteStudentButton.setBackground(buttonRed);
        deleteStudentButton.setForeground(white);
        deleteStudentButton.setFocusPainted(false);
        deleteStudentButton.setPreferredSize(new Dimension(155, 40));

        takeAttendanceButton = new JButton("Take Attendance");
        takeAttendanceButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        takeAttendanceButton.setBackground(new Color(25, 55, 109));
        takeAttendanceButton.setForeground(white);
        takeAttendanceButton.setFocusPainted(false);
        takeAttendanceButton.setPreferredSize(new Dimension(155, 40));

        viewStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String output = studentManager.getStudentsAsString();
                textArea.setText(output);
            }
        });

        viewAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceManager attendanceManager = new AttendanceManager();
                String output = attendanceManager.getAttendanceAsString();
                textArea.setText(output);
            }
        });

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField rollField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField sectionField = new JTextField();

                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridLayout(3, 2, 8, 8));
                formPanel.add(new JLabel("Roll Number:"));
                formPanel.add(rollField);
                formPanel.add(new JLabel("Student Name:"));
                formPanel.add(nameField);
                formPanel.add(new JLabel("Section:"));
                formPanel.add(sectionField);

                int option = JOptionPane.showConfirmDialog(frame, formPanel, "Add Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    String roll = rollField.getText().trim();
                    String name = nameField.getText().trim();
                    String section = sectionField.getText().trim();

                    if (roll.isEmpty() || name.isEmpty() || section.isEmpty()) {
                        textArea.setText("Please fill in all fields before adding a student.");
                        return;
                    }

                    String result = studentManager.insertStudent(roll, name, section);
                    textArea.setText(result);

                    if (result.equals("Student added successfully.")) {
                        textArea.setText(result + "\n\n" + studentManager.getStudentsAsString());
                    }
                }
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roll = JOptionPane.showInputDialog(frame, "Enter Roll Number of student to delete:", "Delete Student", JOptionPane.PLAIN_MESSAGE);

                if (roll == null) {
                    return;
                }

                roll = roll.trim();

                if (roll.isEmpty()) {
                    textArea.setText("Please enter a roll number.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete student with roll number: " + roll + "?\nThis will also delete all their attendance records.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    String result = studentManager.deleteStudent(roll);
                    textArea.setText(result);

                    if (result.contains("deleted successfully")) {
                        textArea.setText(result + "\n\n" + studentManager.getStudentsAsString());
                    }
                }
            }
        });

        takeAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TakeAttendanceScreen();
            }
        });

        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(viewAttendanceButton);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(deleteStudentButton);
        buttonPanel.add(takeAttendanceButton);

        JPanel outputPanel = new JPanel();
        outputPanel.setBackground(lavender);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setBackground(white);
        textArea.setForeground(new Color(30, 30, 60));
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220)));

        outputPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(lavender);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(outputPanel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AttendanceGUI();
    }
}
