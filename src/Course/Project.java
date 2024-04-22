package Course;

import java.time.LocalTime;
import java.util.Map;

public class Project extends Assessment {
    private LocalTime deadline;

    public Project(String type, double max_grade, String _deadline) {
        super(type, max_grade);
        deadline = LocalTime.parse(_deadline);
    }
}
