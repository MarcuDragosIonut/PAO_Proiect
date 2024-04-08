import java.util.Map;

class Course{
    private String course_name;
    private String category;
    private int months_duration;
    private static int number_of_students = 0;
    public Course(String course_name, String category, int months_duration) {
        this.course_name = course_name;
        this.category = category;
        this.months_duration = months_duration;
    }
}

class Grade{
    private Course course;
    private double grade;

    public Grade(Course course, double grade) {
        this.course = course;
        this.grade = grade;
    }
}

class User{
    protected String username;
    protected static int user_count = 0;
    public User(String _username){
        this.username = _username;
        user_count++;
    }
}

class Student extends User{
    private Course[] enrolled_courses;
    private Grade[] grades;

    public Student(String _username) {
        super(_username);
    }
}

class Instructor extends User{
    private Course[] teaching_courses;
    private String Profession;

    public Instructor(String _username, Course[] teaching_courses, String profession) {
        super(_username);
        this.teaching_courses = teaching_courses;
        Profession = profession;
    }
}
class Assessment {
    protected String name;
    protected double max_grade;

    public Assessment(String type, double max_grade) {
        this.name = type;
        this.max_grade = max_grade;
    }

}

class Quiz extends Assessment {
    private boolean Timed;
    private Map<Integer, String> question;
    private Map<Integer, String[]> answers;
    private Map<Integer, String> correct_answer;
    private Map<Integer, Double> points;
    private Map<Integer, Integer> question_time; // secunde
    public Quiz(String type, double max_grade, boolean timed) {
        super(type, max_grade);
        Timed = timed;
    }

    public Quiz(String type, double max_grade, int number_of_questions) {
        super(type, max_grade);
    }
}

class Test extends Assessment{
    private boolean Timed;
    private Map<Integer, String> question;
    private Map<Integer, Integer> question_time; // secunde
    public Test(String type, double max_grade, boolean timed) {
        super(type, max_grade);
        Timed = timed;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("yes");
    }
}