public class Attendance {

    private String rollNumber;
    private String date;
    private int periodNumber;
    private String status;

    public Attendance(String rollNumber, String date, int periodNumber, String status) {
        this.rollNumber = rollNumber;
        this.date = date;
        this.periodNumber = periodNumber;
        this.status = status;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDate() {
        return date;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public String getStatus() {
        return status;
    }
}
