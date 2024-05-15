package Services;

import Course.*;
import User.*;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Services {
    private static final Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##javaProiect", "c##parola");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DateTimeFormatter dt_frmt = DateTimeFormatter.ofPattern("ss:mm:HH dd-MM-yyyy");
    private static final FileWriter fw;

    static {
        try {
            fw = new FileWriter("src/audit.csv", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static final Vector<Student> students = new Vector<Student>();
    private static final Vector<Instructor> instructors = new Vector<Instructor>();
    private static final Vector<Course> courses = new Vector<Course>();
    private static final HashMap<Student, Vector<Grade>> student_grades = new HashMap<Student, Vector<Grade>>();
    private static final HashMap<Course, Vector<Assessment>> course_assessments = new HashMap<Course, Vector<Assessment>>();


    public void CreateNewStudent(String name) throws SQLException, IOException {
        Student new_student = new Student(name);
        students.add(new_student);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO app_user VALUES(" + new_student.getId() + ", '" + name + "')");
        fw.append("INSERT INTO APP_USER, " + LocalDateTime.now().format(dt_frmt) + '\n');
        fw.flush();
    }

    public void AddStudentFromDB(int id, String name) {
        Student new_student = new Student(id, name);
        students.add(new_student);
    }

    public void CreateNewInstructor(String name, String profession) throws SQLException, IOException {
        Instructor new_instructor = new Instructor(name, profession);
        instructors.add(new_instructor);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO app_user VALUES(" + new_instructor.getId() + ", '" + name + "')");
        fw.append("INSERT INTO APP_USER, " + LocalDateTime.now().format(dt_frmt) + '\n');
        stmt.executeUpdate("INSERT INTO instructor VALUES(" + new_instructor.getId() + ", '" + profession + "')");
        fw.append("INSERT INTO INSTRUCTOR, " + LocalDateTime.now().format(dt_frmt) + '\n');

    }

    public void AddInstructorFromDB(int id, String name, String profession) {
        Instructor new_instructor = new Instructor(id, name, profession);
        instructors.add(new_instructor);
    }

    public void CreateNewCourse(String name, String category, int num_of_months) throws SQLException, IOException {
        category = category.trim().toLowerCase();
        Course new_course = new Course(name, category, num_of_months);
        courses.add(new_course);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO course VALUES(" + new_course.getCourse_id() +
                ", '" + category + "', " + num_of_months + ", '" + name + "')");
        fw.append("INSERT INTO COURSE, " + LocalDateTime.now().format(dt_frmt) + '\n');
    }

    public void AddCourseFromDB(int id, String name, String category, int num_of_months) throws SQLException {
        Course new_course = new Course(id, name, category, num_of_months);
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

    public void DisplayCourses() {
        System.out.println("\nList of courses:");
        for (var course : courses) {
            System.out.println(course);
        }
    }

    public int bin_lookup(int s_id, Vector<?> v) {
        var vfe = v.firstElement();
        int L = 0, R = (vfe instanceof Student) ? students.size() -1 : (vfe instanceof Instructor ? instructors.size() - 1 : courses.size() - 1);
        int m = (R + L) / 2;
        while (L <= R) {
            int c_id = 0;// students.elementAt(m).getId();
            if(vfe instanceof Student){
                c_id = ((Vector<Student>) v).elementAt(m).getId();
            }
            else if(vfe instanceof Instructor){
                c_id = ((Vector<Instructor>) v).elementAt(m).getId();
            }
            else if(vfe instanceof Course){
                c_id = ((Vector<Course>) v).elementAt(m).getCourse_id();
            }
            if (s_id == c_id) {
                return m;
            }
            if (s_id < c_id) {
                R = m - 1;
                m = (R + L) / 2;
            } else {
                L = m + 1;
                m = (R + L) / 2;
            }
        }
        return -1;
    }



    public void AddStudentToCourse(int student_id, int course_id) throws SQLException, IOException {
        int vector_student_id = bin_lookup(student_id, students);
        if (vector_student_id == -1) {
            System.out.println("The student with the id " + student_id + " doesn't exist\n");
        } else {
            Student student = students.elementAt(vector_student_id);
            int vector_course_id = bin_lookup(course_id, courses);
            if (vector_course_id == -1) {
                System.out.println("The course with the id " + course_id + " doesn't exist\n");
            } else {
                student.add_to_course(courses.elementAt(vector_course_id));
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USER_COURSE WHERE COURSE_ID = " + course_id + " AND USER_ID = " + student_id);
                fw.append("SELECT FROM USER_COURSE, " + LocalDateTime.now().format(dt_frmt) + '\n');
                rs.next();
                if (rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO USER_COURSE VALUES(" + course_id + ", " + student_id + ")");
                    fw.append("INSERT INTO USER_COURSE, " + LocalDateTime.now().format(dt_frmt) + '\n');
                }
            }
        }
    }

    public void AddInstructorToCourse(int instructor_id, int course_id) throws SQLException, IOException {
        int vector_instructor_id = bin_lookup(instructor_id, instructors);
        if (vector_instructor_id == -1) {
            System.out.println("The instructor with the id " + instructor_id + " doesn't exist\n");
        } else {
            Instructor instructor = instructors.elementAt(vector_instructor_id);
            int vector_course_id = bin_lookup(course_id, courses);
            if (vector_course_id == -1) {
                System.out.println("The course with the id " + course_id + " doesn't exist\n");
            } else {
                Course course = courses.elementAt(vector_course_id);
                instructor.add_to_course(course);
                course.add_instructor(instructor);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USER_COURSE WHERE COURSE_ID = " + course_id + " AND USER_ID = " + instructor_id);
                fw.append("SELECT FROM USER_COURSE, " + LocalDateTime.now().format(dt_frmt) + '\n');
                rs.next();
                if (rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO USER_COURSE VALUES(" + course_id + ", " + instructor_id + ")");
                    fw.append("INSERT INTO USER_COURSE, " + LocalDateTime.now().format(dt_frmt) + '\n');
                }
            }
        }
    }

    public void ShowCourseDetails(int course_id) {
        int vector_course_id = bin_lookup(course_id, courses);
        if (vector_course_id == -1) {
            System.out.println("There is no course with the id " + course_id + "\n");
        } else {
            Course course = courses.elementAt(vector_course_id);
            System.out.println(course_id + " " + course.getCourse_name() + ", Category: " + course.getCategory());
            System.out.println("Instructors: ");
            for (var instructor : course.get_instructors()) {
                System.out.println(instructor);
            }
            System.out.println("Students: ");
            for (var student : students) {
                if (student.get_courses().contains(course)) {
                    System.out.println(student);
                }
            }
            System.out.println("Assessments: ");
            if(course_assessments.containsKey(course)) {
                for (var assessment : course_assessments.get(course)) {
                    if (assessment instanceof Project) {
                        System.out.println(assessment.getName() + " " + ((Project) assessment).Get_deadline());
                    }
                    if (assessment instanceof Quiz) {
                        System.out.println(assessment.getName() + " minutes: " + ((Quiz) assessment).getTime()
                                + "\nnumber of questions: " + ((Quiz) assessment).getNumber_of_questions());
                    }
                }
            }
            System.out.println("\n");
        }
    }

    public void ShowStudentDetails(int student_id) {
        int vector_student_id = bin_lookup(student_id, students);
        if (vector_student_id == -1) {
            System.out.println("There is no student with the id " + student_id + "\n");
        } else {
            Student student = students.elementAt(vector_student_id);
            System.out.println(student_id + " " + student.getName() + " is enrolled in the following courses:\n");
            for (var course : student.get_courses()) {
                System.out.println(course + " | grades: ");
                student_grades.putIfAbsent(student, new Vector<Grade>());
                for (var grade : student_grades.get(student)) {
                    if (grade.getCourse() == course) System.out.println(grade + " ");
                }
            }
            System.out.println();
        }
    }

    public void AddGrade(double grade, double max_grade, int course_id, int student_id, String note) throws SQLException, IOException {
        int vector_course_id = bin_lookup(course_id, courses);
        if (vector_course_id == -1) {
            System.out.println("There is no course with the id " + course_id + "\n");
        } else {
            int vector_student_id = bin_lookup(student_id, students);
            if (vector_student_id == -1) {
                System.out.println("There is no student with the id " + student_id + "\n");
            } else {
                if (max_grade < grade) {
                    System.out.println("Invalid grade\n");
                } else {
                    Course course = courses.elementAt(vector_course_id);
                    Student student = students.elementAt(vector_student_id);
                    Grade newgrade = new Grade(courses.elementAt(vector_course_id), grade, max_grade, note);
                    if (student.get_courses().contains(course)) {
                        student_grades.putIfAbsent(student, new Vector<Grade>());
                        Vector<Grade> old_vector = student_grades.get(student);
                        old_vector.add(newgrade);
                        student_grades.put(student, old_vector);
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate("INSERT INTO GRADE (grade_score, grade_max_score, note, course_id, user_id) VALUES(%f, %f, '%s', %d, %d)".formatted(grade, max_grade, note, course_id, student_id));
                        fw.append("INSERT INTO GRADE, " + LocalDateTime.now().format(dt_frmt) + '\n');
                    } else {
                        System.out.println("The student isn't enrolled in this course\n");
                    }
                }
            }
        }
    }

    public void AddDBGrade(double grade_score, double max_grade_score, String note, int course_id, int student_id) {
        Course grade_course = courses.elementAt(bin_lookup(course_id, courses));
        Grade grade = new Grade(grade_course, grade_score, max_grade_score, note);
        int vector_student_id = bin_lookup(student_id, students);
        Student student = students.elementAt(vector_student_id);
        student_grades.putIfAbsent(student, new Vector<Grade>());
        Vector<Grade> old_vector = student_grades.get(student);
        old_vector.add(grade);
        student_grades.put(student, old_vector);
    }

    public void AddQuiz(String name, int number_of_questions, int time_limit, int course_id) throws SQLException, IOException {
        Quiz quiz = new Quiz(name, number_of_questions, time_limit);
        int vector_course_id = bin_lookup(course_id, courses);
        if(vector_course_id != -1) {
            AddAssessment(course_id, quiz);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO ASSESSMENT (name, course_id) VALUES('" + name + "', " + course_id + ")");
            fw.append("INSERT INTO ASSESSMENT, " + LocalDateTime.now().format(dt_frmt) + '\n');
            ResultSet rs = stmt.executeQuery("SELECT MAX(ASSESSMENT_ID) FROM ASSESSMENT");
            fw.append("SELECT FROM ASSESSMENT, " + LocalDateTime.now().format(dt_frmt) + '\n');
            rs.next();
            stmt.executeUpdate("INSERT INTO QUIZ VALUES (" + rs.getInt(1) + ", " + time_limit
                    + ", " + number_of_questions + ")");
            fw.append("INSERT INTO QUIZ, " + LocalDateTime.now().format(dt_frmt) + '\n');
        }
    }

    public void AddProject(String name, String date, String hour, int course_id) throws SQLException, IOException {
        Project project = new Project(name, date, hour);
        int vector_course_id = bin_lookup(course_id, courses);
        if(vector_course_id != -1) {
            AddAssessment(course_id, project);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO ASSESSMENT (name, course_id) VALUES('" + name + "', " + course_id + ")");
            fw.append("INSERT INTO ASSESSMENT, " + LocalDateTime.now().format(dt_frmt) + '\n');
            ResultSet rs = stmt.executeQuery("SELECT MAX(ASSESSMENT_ID) FROM ASSESSMENT");
            fw.append("SELECT FROM ASSESSMENT, " + LocalDateTime.now().format(dt_frmt) + '\n');
            rs.next();
            stmt.executeUpdate("INSERT INTO PROJECT VALUES (" + rs.getInt(1) +
                    ", TO_DATE('" + date + " " + hour + "','yyyy-mm-dd hh24:mi'))");
            fw.append("INSERT INTO PROJECT, " + LocalDateTime.now().format(dt_frmt) + '\n');
        }

    }

    public void AddAssessment(int course_id, Assessment assessment) {
        int vector_course_id = bin_lookup(course_id, courses);
        if (vector_course_id == -1) {
            System.out.println("There is no course with the id " + course_id + "\n");
        } else {
            Course course = courses.elementAt(vector_course_id);
            course_assessments.putIfAbsent(course, new Vector<Assessment>());
            Vector<Assessment> old_vector = course_assessments.get(course);
            old_vector.add(assessment);
            course_assessments.put(course, old_vector);
        }
    }

    public void close_connection() throws SQLException {
        con.close();
    }
    public void close_file() throws IOException {
        fw.close();
    }
}
