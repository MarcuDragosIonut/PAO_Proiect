package Course;

import User.Instructor;

import java.util.Vector;

public class Course implements Comparable<Course> {
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
    public Course(int course_id, String course_name, String category, int months_duration) {
        this.course_name = course_name;
        this.category = category;
        this.months_duration = months_duration;
        this.course_id = course_id;
    }

    public static void setNumber_of_courses(int nr){
        number_of_courses = nr;
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
