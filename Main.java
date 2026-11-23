public class Main {
    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        studentManager.viewStudents();

        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.markAttendance("CSE002", "2026-10-28", 1, "Present");
        attendanceManager.viewAttendance();
    }
}
