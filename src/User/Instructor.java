package User;
import java.util.Vector;


public class Instructor extends User {
    private Vector<Course.Course> teaching_courses = new Vector<Course.Course>();
    private String profession;

    public Instructor(String _name, String profession) {
        super(_name);
        this.profession = profession;
    }
    public Instructor(int _id, String _name, String profession) {
        super(_id, _name);
        this.profession = profession;
    }

    public void add_to_course(Course.Course course) {
        teaching_courses.add(course);
    }

    public void quit_course(Course.Course course) {
        teaching_courses.remove(course);
        if (course.get_instructors().contains(this)) course.remove_instructor(this);
    }

    public Vector<Course.Course> get_courses() {
        return teaching_courses;
    }

    @Override
    public String toString() {
        return user_id + " " + name + " | " + profession;
    }
}
