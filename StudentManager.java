import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManager {

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (roll_number, student_name, section) VALUES (?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getStudentName());
            ps.setString(3, student.getSection());
            ps.executeUpdate();
            System.out.println("Student added successfully.");
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to add student.");
            System.out.println(e.getMessage());
        }
    }

    public void viewStudents() {
        String sql = "SELECT * FROM students ORDER BY roll_number";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rollNumber = rs.getString("roll_number");
                String studentName = rs.getString("student_name");
                String section = rs.getString("section");
                System.out.println("Roll: " + rollNumber + " | Name: " + studentName + " | Section: " + section);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve students.");
            System.out.println(e.getMessage());
        }
    }

    public String getStudentsAsString() {
        String sql = "SELECT * FROM students ORDER BY roll_number";
        String result = "";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                String rollNumber = rs.getString("roll_number");
                String studentName = rs.getString("student_name");
                String section = rs.getString("section");
                result = result + "Roll: " + rollNumber + " | Name: " + studentName + " | Section: " + section + "\n";
            }
            if (!found) {
                result = "No students found.";
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            result = "Failed to retrieve students.\n" + e.getMessage();
        }
        return result;
    }

    public java.util.ArrayList<int[]> getStudentIdList() {
        java.util.ArrayList<int[]> list = new java.util.ArrayList<int[]>();
        String sql = "SELECT student_id FROM students ORDER BY roll_number";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int[] row = new int[1];
                row[0] = rs.getInt("student_id");
                list.add(row);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public java.util.ArrayList<String[]> getStudentDataList() {
        java.util.ArrayList<String[]> list = new java.util.ArrayList<String[]>();
        String sql = "SELECT student_id, roll_number, student_name, section FROM students ORDER BY roll_number";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("student_id"));
                row[1] = rs.getString("roll_number");
                row[2] = rs.getString("student_name");
                row[3] = rs.getString("section");
                list.add(row);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public String insertStudent(String rollNumber, String studentName, String section) {
        String sql = "INSERT INTO students (roll_number, student_name, section) VALUES (?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollNumber);
            ps.setString(2, studentName);
            ps.setString(3, section);
            ps.executeUpdate();
            ps.close();
            con.close();
            return "Student added successfully.";
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate") || e.getMessage().toLowerCase().contains("unique")) {
                return "A student with roll number " + rollNumber + " already exists.";
            }
            return "Failed to add student.\n" + e.getMessage();
        }
    }

    public String deleteStudent(String rollNumber) {
        String checkSql = "SELECT student_id FROM students WHERE roll_number = ?";
        String deleteSql = "DELETE FROM students WHERE roll_number = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(checkSql);
            ps.setString(1, rollNumber);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                con.close();
                return "No student found with roll number " + rollNumber + ".";
            }
            rs.close();
            ps.close();
            PreparedStatement ps2 = con.prepareStatement(deleteSql);
            ps2.setString(1, rollNumber);
            ps2.executeUpdate();
            ps2.close();
            con.close();
            return "Student with roll number " + rollNumber + " deleted successfully.";
        } catch (SQLException e) {
            return "Failed to delete student.\n" + e.getMessage();
        }
    }
}
