package User;

import java.util.TreeSet;
import java.util.Vector;

public class Student extends User {
    private TreeSet<Course.Course> enrolled_courses = new TreeSet<Course.Course>();
    private Vector<Course.Grade> grades = new Vector<Course.Grade>();

    public Student(String _name) {
        super(_name);
    }

    public void add_to_course(Course.Course course) {
        enrolled_courses.add(course);
    }

    public void quit_course(Course.Course course) {
        enrolled_courses.remove(course);
    }

    public TreeSet<Course.Course> get_courses() {
        return enrolled_courses;
    }

    @Override
    public String toString() {
        return user_id + " " + name;
    }
}
