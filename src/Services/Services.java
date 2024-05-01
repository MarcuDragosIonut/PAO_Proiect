package Services;

import Course.*;
import User.Instructor;
import User.Student;

import javax.swing.*;
import java.util.*;

public class Services {


    private static final Vector<Student> students = new Vector<Student>();
    private static final Vector<Instructor> instructors = new Vector<Instructor>();
    private static final Vector<Course> courses = new Vector<Course>();
    private static final HashMap<Student, Vector<Grade>> student_grades = new HashMap<Student, Vector<Grade>>();
    private static final HashMap<Course, Vector<Assessment>> course_assessments = new HashMap<Course, Vector<Assessment>>();

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

    public void DisplayCourses() {
        System.out.println("\nList of courses:");
        for (var course : courses) {
            System.out.println(course);
        }
    }

    /* Daca o sa fie sterse vreodata elementele, id-urile nu o sa mai la locul lor in vector, deci
        e necesara o cautare
     */
    private int student_bin_lookup(int s_id) {
        int L = 0, R = students.size() - 1, m = (R + L) / 2;
        while (L <= R) {
            int c_id = students.elementAt(m).getId();
            System.out.println(c_id + " " + s_id);
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
            int c_id = instructors.elementAt(m).getId();
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
            } else {
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
            Instructor instructor = instructors.elementAt(vector_instructor_id);
            int vector_course_id = course_bin_lookup(course_id);
            if (vector_course_id == -1) {
                System.out.println("The course with the id " + course_id + " doesn't exist\n");
            } else {
                Course course = courses.elementAt(vector_course_id);
                instructor.add_to_course(course);
                course.add_instructor(instructor);
                System.out.println("Succesfully added!\n");
            }
        }
    }

    public void ShowCourseDetails(int course_id) {
        int vector_course_id = course_bin_lookup(course_id);
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
            System.out.println("\n");
        }
    }

    public void ShowStudentDetails(int student_id) {
        int vector_student_id = student_bin_lookup(student_id);
        if (vector_student_id == -1) {
            System.out.println("There is no student with the id " + student_id + "\n");
        } else {
            Student student = students.elementAt(vector_student_id);
            System.out.println(student_id + " " + student.getName() + " is enrolled in the following courses:\n");
            for (var course : student.get_courses()) {
                System.out.println(course + " | grades: ");
                for (var grade : student_grades.get(student)) {
                    if (grade.getCourse() == course) System.out.println(grade + " ");
                }
            }
            System.out.println();
        }
    }

    public void AddGrade(double grade, double max_grade, int course_id, int student_id, String note) {
        int vector_course_id = course_bin_lookup(course_id);
        if (vector_course_id == -1) {
            System.out.println("There is no course with the id " + course_id + "\n");
        } else {
            int vector_student_id = student_bin_lookup(student_id);
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
                        System.out.println("The grade was successfully added\n");
                    } else {
                        System.out.println("The student isn't enrolled in this course\n");
                    }
                }
            }
        }
    }

    public void AddQuiz(String name, String description, int number_of_questions, int time_limit, int course_id){
        Quiz quiz = new Quiz(name, description, number_of_questions, time_limit);
        int vector_course_id = course_bin_lookup(course_id);
        AddAssessment(course_id, vector_course_id, quiz);
    }

    public void AddProject(String name, String description, String date, String hour, int course_id){
        Project project = new Project(name, description,date, hour);
        int vector_course_id = course_bin_lookup(course_id);
        AddAssessment(course_id, vector_course_id, project);
    }

    private static void AddAssessment(int course_id, int vector_course_id, Assessment project) {
        if(vector_course_id == -1){
            System.out.println("There is no course with the id " + course_id + "\n");
        }
        else{
            Course course = courses.elementAt(vector_course_id);
            course_assessments.putIfAbsent(course, new Vector<Assessment>());
            Vector<Assessment> old_vector = course_assessments.get(course);
            old_vector.add(project);
            course_assessments.put(course, old_vector);
            System.out.println("The quiz has been successfully added\n");
        }
    }

}
