package Course;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class Project extends Assessment {
    private String deadline;

    public Project(String name, String description, String date, String hour) {
        super(name, description);
        deadline = date.trim() + "T" + hour.trim();
        LocalDateTime.parse(deadline);
    }

    public void Change_deadline(String date, String hour){
        deadline = date.trim() + "T" + hour.trim();
    }
    public String Get_deadline(){
        return deadline;
    }
}
