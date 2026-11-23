public class Student {

    private String rollNumber;
    private String studentName;
    private String section;

    public Student(String rollNumber, String studentName, String section) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.section = section;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSection() {
        return section;
    }
}