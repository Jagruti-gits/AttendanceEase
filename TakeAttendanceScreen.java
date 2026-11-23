import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TakeAttendanceScreen {

    private JFrame frame;
    private JTextField dateField;
    private JPanel tablePanel;
    private JLabel statusLabel;
    private ArrayList<String[]> studentData;
    private ArrayList<JCheckBox[]> allCheckboxes;
    private ArrayList<JLabel> percentageLabels;
    private AttendanceManager attendanceManager;
    private StudentManager studentManager;

    private Color darkBlue = new Color(25, 55, 109);
    private Color lavender = new Color(230, 230, 250);
    private Color white = Color.WHITE;
    private Color headerColor = new Color(63, 114, 175);
    private Color rowAlt = new Color(240, 240, 255);

    public TakeAttendanceScreen() {
        attendanceManager = new AttendanceManager();
        studentManager = new StudentManager();
        allCheckboxes = new ArrayList<JCheckBox[]>();
        percentageLabels = new ArrayList<JLabel>();

        frame = new JFrame("Take Attendance");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(darkBlue);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JLabel titleLabel = new JLabel("Take Attendance");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(white);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel topBar = new JPanel();
        topBar.setBackground(lavender);
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        dateField = new JTextField(12);
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton loadButton = new JButton("Load");
        loadButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        loadButton.setBackground(headerColor);
        loadButton.setForeground(white);
        loadButton.setFocusPainted(false);
        loadButton.setPreferredSize(new Dimension(80, 32));

        JButton saveButton = new JButton("Save Attendance");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        saveButton.setBackground(new Color(60, 140, 90));
        saveButton.setForeground(white);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(160, 32));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(30, 30, 60));

        topBar.add(dateLabel);
        topBar.add(dateField);
        topBar.add(loadButton);
        topBar.add(saveButton);
        topBar.add(statusLabel);

        tablePanel = new JPanel();
        tablePanel.setBackground(white);
        tablePanel.setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220)));

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(lavender);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
        contentPanel.add(topBar, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        loadStudents();
        buildTable("");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText().trim();
                if (date.isEmpty()) {
                    statusLabel.setText("Please enter a date.");
                    return;
                }
                loadSavedAttendance(date);
                refreshPercentages(date);
                statusLabel.setText("Loaded attendance for " + date);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText().trim();
                if (date.isEmpty()) {
                    statusLabel.setText("Please enter a date before saving.");
                    return;
                }
                int saved = 0;
                int failed = 0;
                for (int i = 0; i < studentData.size(); i++) {
                    int studentId = Integer.parseInt(studentData.get(i)[0]);
                    JCheckBox[] boxes = allCheckboxes.get(i);
                    boolean[] periodPresent = new boolean[6];
                    for (int p = 0; p < 6; p++) {
                        periodPresent[p] = boxes[p].isSelected();
                    }
                    String result = attendanceManager.saveAttendanceForDate(studentId, date, periodPresent);
                    if (result.equals("ok")) {
                        saved++;
                    } else {
                        failed++;
                    }
                }
                refreshPercentages(date);
                if (failed == 0) {
                    statusLabel.setText("Attendance saved for " + saved + " students.");
                } else {
                    statusLabel.setText("Saved: " + saved + "  Failed: " + failed);
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadStudents() {
        studentData = studentManager.getStudentDataList();
    }

    private void buildTable(String date) {
        tablePanel.removeAll();
        allCheckboxes.clear();
        percentageLabels.clear();

        JPanel headerRow = new JPanel();
        headerRow.setBackground(headerColor);
        headerRow.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 6));
        headerRow.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        headerRow.add(makeHeaderCell("Roll No", 90));
        headerRow.add(makeHeaderCell("Student Name", 170));
        headerRow.add(makeHeaderCell("P1", 50));
        headerRow.add(makeHeaderCell("P2", 50));
        headerRow.add(makeHeaderCell("P3", 50));
        headerRow.add(makeHeaderCell("P4", 50));
        headerRow.add(makeHeaderCell("P5", 50));
        headerRow.add(makeHeaderCell("P6", 50));
        headerRow.add(makeHeaderCell("Att %", 70));

        tablePanel.add(headerRow);

        for (int i = 0; i < studentData.size(); i++) {
            String[] row = studentData.get(i);
            Color rowBg = (i % 2 == 0) ? white : rowAlt;

            JPanel studentRow = new JPanel();
            studentRow.setBackground(rowBg);
            studentRow.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 8));
            studentRow.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

            JLabel rollLabel = makeDataCell(row[1], 90, rowBg);
            JLabel nameLabel = makeDataCell(row[2], 170, rowBg);

            studentRow.add(rollLabel);
            studentRow.add(nameLabel);

            JCheckBox[] boxes = new JCheckBox[6];
            for (int p = 0; p < 6; p++) {
                JCheckBox cb = new JCheckBox();
                cb.setBackground(rowBg);
                cb.setPreferredSize(new Dimension(50, 28));
                boxes[p] = cb;
                studentRow.add(cb);
            }
            allCheckboxes.add(boxes);

            JLabel pctLabel = new JLabel("--");
            pctLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            pctLabel.setPreferredSize(new Dimension(70, 28));
            pctLabel.setOpaque(true);
            pctLabel.setBackground(rowBg);
            pctLabel.setHorizontalAlignment(JLabel.CENTER);
            percentageLabels.add(pctLabel);
            studentRow.add(pctLabel);

            tablePanel.add(studentRow);
        }

        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private void loadSavedAttendance(String date) {
        for (int i = 0; i < studentData.size(); i++) {
            int studentId = Integer.parseInt(studentData.get(i)[0]);
            boolean[] saved = attendanceManager.getAttendanceForDate(studentId, date);
            JCheckBox[] boxes = allCheckboxes.get(i);
            for (int p = 0; p < 6; p++) {
                boxes[p].setSelected(saved[p]);
            }
        }
    }

    private void refreshPercentages(String date) {
        for (int i = 0; i < studentData.size(); i++) {
            int studentId = Integer.parseInt(studentData.get(i)[0]);
            int pct = attendanceManager.getAttendancePercentage(studentId, date);
            JLabel pctLabel = percentageLabels.get(i);
            if (pct == -1) {
                pctLabel.setText("--");
                pctLabel.setForeground(Color.GRAY);
            } else {
                pctLabel.setText(pct + "%");
                if (pct >= 75) {
                    pctLabel.setForeground(new Color(30, 130, 50));
                } else if (pct >= 50) {
                    pctLabel.setForeground(new Color(200, 130, 0));
                } else {
                    pctLabel.setForeground(new Color(180, 40, 40));
                }
            }
        }
    }

    private JLabel makeHeaderCell(String text, int width) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(white);
        label.setPreferredSize(new Dimension(width, 28));
        label.setOpaque(true);
        label.setBackground(headerColor);
        return label;
    }

    private JLabel makeDataCell(String text, int width, Color bg) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(new Color(30, 30, 60));
        label.setPreferredSize(new Dimension(width, 28));
        label.setOpaque(true);
        label.setBackground(bg);
        return label;
    }
}
