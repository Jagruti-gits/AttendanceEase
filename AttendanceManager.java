
import java.sql.*;

public class AttendanceManager {

    public void markAttendance(String rollNumber, String date, int periodNumber, String status) {
        if (periodNumber < 1 || periodNumber > 6) {
            System.out.println("Invalid period number.");
            return;
        }

        if (!status.equals("Present") && !status.equals("Absent")) {
            System.out.println("Invalid status.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT student_id FROM students WHERE roll_number = ?"
            );
            ps.setString(1, rollNumber);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found.");
                rs.close();
                ps.close();
                con.close();
                return;
            }

            int id = rs.getInt("student_id");
            rs.close();
            ps.close();

            ps = con.prepareStatement(
                "SELECT attendance_id FROM attendance WHERE student_id = ? AND attendance_date = ? AND period_number = ?"
            );
            ps.setInt(1, id);
            ps.setDate(2, Date.valueOf(date));
            ps.setInt(3, periodNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Attendance already marked.");
                rs.close();
                ps.close();
                con.close();
                return;
            }

            rs.close();
            ps.close();

            ps = con.prepareStatement(
                "INSERT INTO attendance (student_id, attendance_date, period_number, status) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, id);
            ps.setDate(2, Date.valueOf(date));
            ps.setInt(3, periodNumber);
            ps.setString(4, status);
            ps.executeUpdate();

            System.out.println("Attendance marked successfully.");

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAttendance() {
        String sql = "SELECT s.roll_number, s.student_name, a.attendance_date, a.period_number, a.status " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.student_id " +
                     "ORDER BY a.attendance_date DESC, a.period_number";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String rollNumber = rs.getString("roll_number");
                String studentName = rs.getString("student_name");
                String attendanceDate = rs.getString("attendance_date");
                int periodNumber = rs.getInt("period_number");
                String status = rs.getString("status");
                System.out.println("Roll: " + rollNumber + " | Name: " + studentName + " | Date: " + attendanceDate + " | Period: " + periodNumber + " | Status: " + status);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve attendance.");
            System.out.println(e.getMessage());
        }
    }

    public String getAttendanceAsString() {
        String sql = "SELECT s.roll_number, s.student_name, a.attendance_date, a.period_number, a.status " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.student_id " +
                     "ORDER BY a.attendance_date DESC, a.period_number";
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
                String attendanceDate = rs.getString("attendance_date");
                int periodNumber = rs.getInt("period_number");
                String status = rs.getString("status");
                result = result + "Roll: " + rollNumber + " | Name: " + studentName + " | Date: " + attendanceDate + " | Period: " + periodNumber + " | Status: " + status + "\n";
            }
            if (!found) {
                result = "No attendance records found.";
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            result = "Failed to retrieve attendance.\n" + e.getMessage();
        }
        return result;
    }

    public String saveAttendanceForDate(int studentId, String date, boolean[] periodPresent) {
        String sql = "INSERT INTO attendance (student_id, attendance_date, period_number, status) " +
                     "VALUES (?, ?, ?, ?) " +
                     "ON CONFLICT (student_id, attendance_date, period_number) " +
                     "DO UPDATE SET status = EXCLUDED.status";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < 6; i++) {
                String status = periodPresent[i] ? "Present" : "Absent";
                ps.setInt(1, studentId);
                ps.setDate(2, Date.valueOf(date));
                ps.setInt(3, i + 1);
                ps.setString(4, status);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            con.close();
            return "ok";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public boolean[] getAttendanceForDate(int studentId, String date) {
        boolean[] result = new boolean[6];
        String sql = "SELECT period_number, status FROM attendance WHERE student_id = ? AND attendance_date = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int period = rs.getInt("period_number");
                String status = rs.getString("status");
                if (period >= 1 && period <= 6) {
                    result[period - 1] = status.equals("Present");
                }
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int getAttendancePercentage(int studentId, String upToDate) {
        String sql = "SELECT COUNT(*) AS total, SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present_count " +
                     "FROM attendance WHERE student_id = ? AND attendance_date <= ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(upToDate));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int present = rs.getInt("present_count");
                rs.close();
                ps.close();
                con.close();
                if (total == 0) {
                    return -1;
                }
                return (int) Math.round((present * 100.0) / total);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
