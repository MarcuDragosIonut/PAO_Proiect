import java.util.*;

class Course implements Comparable<Course>{
    private String course_name;
    private String category;
    private int months_duration;
    private int course_id;
    private static int number_of_courses = 0;
    private Vector<Instructor> instructors = new Vector<Instructor>();

    public Course(String course_name, String category, int months_duration) {
        this.course_name = course_name;
        this.category = category;
        this.months_duration = months_duration;
        this.course_id = ++number_of_courses;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCategory() {
        return category;
    }

    public int getMonths_duration() {
        return months_duration;
    }

    public void setMonths_duration(int months_duration) {
        this.months_duration = months_duration;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void add_instructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void remove_instructor(Instructor instructor) {
        instructors.remove(instructor);
        if (instructor.get_courses().contains(this)) instructor.quit_course(this);
    }

    public Vector<Instructor> get_instructors() {
        return instructors;
    }

    @Override
    public String toString() {
        return course_id + " " + course_name + " (" + category + ")";
    }

    @Override
    public int compareTo(Course o) {
        return (this.course_id > o.course_id) ? 1 : 0;
    }
}

class Grade {
    private Course course;
    private double grade;

    public Grade(Course course, double grade) {
        this.course = course;
        this.grade = grade;
    }
}

abstract class User {
    protected String name;
    protected int user_id;
    protected static int user_id_count = 0;

    public User(String _username) {
        this.name = _username;
        this.user_id = ++user_id_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String new_username) {
        this.name = new_username;
    }
}

class Student extends User {
    private TreeSet<Course> enrolled_courses = new TreeSet<Course>();
    private Vector<Grade> grades = new Vector<Grade>();

    public Student(String _name) {
        super(_name);
    }

    public void add_to_course(Course course) {
        enrolled_courses.add(course);
    }

    public void quit_course(Course course) {
        enrolled_courses.remove(course);
    }

    public TreeSet<Course> get_courses() {
        return enrolled_courses;
    }

    @Override
    public String toString() {
        return user_id + " " + name;
    }
}

class Instructor extends User {
    private Vector<Course> teaching_courses = new Vector<Course>();
    private String profession;

    public Instructor(String _name, String profession) {
        super(_name);
        this.profession = profession;
    }

    public void add_to_course(Course course) {
        teaching_courses.add(course);
    }

    public void quit_course(Course course) {
        teaching_courses.remove(course);
        if (course.get_instructors().contains(this)) course.remove_instructor(this);
    }

    public Vector<Course> get_courses() {
        return teaching_courses;
    }

    @Override
    public String toString() {
        return user_id + " " + name + " | " + profession;
    }
}

abstract class Assessment {
    protected String name;
    protected double max_grade;

    public Assessment(String type, double max_grade) {
        this.name = type;
        this.max_grade = max_grade;
    }

}

class Quiz extends Assessment {
    private boolean Timed;
    private HashMap<Integer, String> question;
    private HashMap<Integer, String[]> answers;
    private HashMap<Integer, String> correct_answer;
    private HashMap<Integer, Double> points;

    public Quiz(String type, double max_grade, boolean timed) {
        super(type, max_grade);
        Timed = timed;
    }

    public Quiz(String type, double max_grade, int number_of_questions) {
        super(type, max_grade);
    }
}

class Test extends Assessment {
    private boolean Timed;
    private Map<Integer, String> question;

    public Test(String type, double max_grade, boolean timed) {
        super(type, max_grade);
        Timed = timed;
    }
}

public class Services {


    private static Vector<Student> students = new Vector<Student>();
    private static Vector<Instructor> instructors = new Vector<Instructor>();
    private static Vector<Course> courses = new Vector<Course>();
    private static HashMap<Student, Vector<Grade>> student_grades = new HashMap<Student, Vector<Grade>>();

    public void CreateNewStudent(String name) {
        Student new_student = new Student(name);
        students.add(new_student);
    }

    public void CreateNewInstructor(String name, String profession) {
        Instructor new_instructor = new Instructor(name, profession);
        instructors.add(new_instructor);
    }

    public void CreateNewCourse(String name, String category, int num_of_months) {
        category = category.trim().toLowerCase();
        Course new_course = new Course(name, category, num_of_months);
        courses.add(new_course);
    }

    public void DisplayStudents() {
        System.out.println("\nList of students:");
        for (var student : students) {
            System.out.println(student);
        }
    }

    public void DisplayInstructors() {
        System.out.println("\nList of instructors:");
        for (var instructor : instructors) {
            System.out.println(instructor);
        }
    }

    public void DisplayCourses(){
        System.out.println("\nList of courses:");
        for(var course : courses){
            System.out.println(course);
        }
    }

    /* Daca o sa fie sterse vreodata elementele, id-urile nu o sa mai la locul lor in vector, deci
        e necesara o cautare
     */
    private int student_bin_lookup(int s_id) {
        int L = 0, R = students.size() - 1, m = (R + L) / 2;
        while (L <= R) {
            int c_id = students.elementAt(m).user_id;
            System.out.println(c_id + " " + s_id );
            if (s_id == c_id) {
                return m;
            }
            if (s_id < c_id) {
                R = m - 1;
            } else {
                L = m + 1;
            }
        }
        return -1;
    }

    private int course_bin_lookup(int crs_id) {
        int L = 0, R = courses.size() - 1, m = (R + L) / 2;
        while (L <= R) {
            int c_id = courses.elementAt(m).getCourse_id();
            if (crs_id == c_id) {
                return m;
            }
            if (crs_id < c_id) {
                R = m - 1;
            } else {
                L = m + 1;
            }
        }
        return -1;
    }

    private int instructor_bin_lookup(int crs_id) {
        int L = 0, R = instructors.size() - 1, m = (R + L) / 2;
        while (L <= R) {
            int c_id = courses.elementAt(m).getCourse_id();
            if (crs_id == c_id) {
                return m;
            }
            if (crs_id < c_id) {
                R = m - 1;
            } else {
                L = m + 1;
            }
        }
        return -1;
    }

    public void AddStudentToCourse(int student_id, int course_id) {
        int vector_student_id = student_bin_lookup(student_id);
        if (vector_student_id == -1) {
            System.out.println("The student with the id " + student_id + " doesn't exist\n");
        } else {
            Student student = students.elementAt(vector_student_id);
            int vector_course_id = course_bin_lookup(course_id);
            if (vector_course_id == -1) {
                System.out.println("The course with the id " + course_id + " doesn't exist\n");
            }
            else {
                student.add_to_course(courses.elementAt(vector_course_id));
                System.out.println("Succesfully added!\n");
            }
        }
    }

    public void AddInstructorToCourse(int instructor_id, int course_id) {
        int vector_instructor_id = instructor_bin_lookup(instructor_id);
        if (vector_instructor_id == -1) {
            System.out.println("The sinstructor with the id " + instructor_id + " doesn't exist\n");
        } else {
            Student student = students.elementAt(vector_instructor_id);
            int vector_course_id = course_bin_lookup(course_id);
            if (vector_course_id == -1) {
                System.out.println("The course with the id " + course_id + " doesn't exist\n");
            }
            else {
                student.add_to_course(courses.elementAt(vector_course_id));
                System.out.println("Succesfully added!\n");
            }
        }
    }

    public void ShowCourseDetails(int course_id){
        int vector_course_id = course_bin_lookup(course_id);
        if(vector_course_id == -1){
            System.out.println("There is no course with the id " + course_id + "\n");
        }
        else{
            Course course = courses.elementAt(vector_course_id);
            System.out.println(course_id + " " + course.getCourse_name() + ", Category: " + course.getCategory());
            System.out.println("Instructors: ");
            for(var instructor : course.get_instructors()){
                System.out.println(instructor);
            }
            System.out.println("Students: ");
            for(var student : students){
                if(student.get_courses().contains(course)){
                    System.out.println(student);
                }
            }
            System.out.println("\n");
        }
    }

}
