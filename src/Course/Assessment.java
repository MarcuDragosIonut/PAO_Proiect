package Course;

abstract class Assessment {
    protected String name;
    protected double max_grade;

    public Assessment(String type, double max_grade) {
        this.name = type;
        this.max_grade = max_grade;
    }

}
