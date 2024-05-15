package User;

import java.util.TreeSet;
import java.util.Vector;

public class Student extends User {
    private final TreeSet<Course.Course> enrolled_courses = new TreeSet<Course.Course>();

    public Student(String _name) {
        super(_name);
    }

    public Student(int _id, String _name){
        super(_id, _name);
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
