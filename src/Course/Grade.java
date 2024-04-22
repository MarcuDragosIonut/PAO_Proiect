package Course;

public class Grade {
    private Course course;
    private double grade, max_grade;
    private String note;

    public Grade(Course course, double grade, double max_grade, String note) {
        this.course = course;
        this.grade = grade;
        this.max_grade = max_grade;
        this.note = note;
    }

    public Course getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return grade + "/" + max_grade;
    }
}
