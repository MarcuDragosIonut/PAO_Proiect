package Course;

public abstract class Assessment {
    protected String name;

    public Assessment(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
