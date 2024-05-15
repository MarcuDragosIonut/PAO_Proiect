package Services;

import Course.*;
import User.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class LoadDB {
    public static void load_DB() throws SQLException, IOException {
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##javaProiect", "c##parola");
        Services service = new Services();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP_USER " +
                "WHERE user_id NOT IN (SELECT user_id FROM INSTRUCTOR)");
        int max_id = 0;
        while(rs.next()){
            int rs_user_id = rs.getInt("user_id");
            service.AddStudentFromDB(rs_user_id, rs.getString("user_name"));
            if(rs_user_id > max_id) max_id = rs_user_id;
        }
        rs = stmt.executeQuery("SELECT user_id, user_name, profession" +
                " FROM APP_USER JOIN INSTRUCTOR USING(user_id)");
        while(rs.next()){
            int rs_user_id = rs.getInt("user_id");
            service.AddInstructorFromDB(rs_user_id, rs.getString("user_name"), rs.getString("profession"));
            if(rs_user_id > max_id) max_id = rs_user_id;
        }
        User.setUser_id_count(max_id);
        max_id = 0;
        rs = stmt.executeQuery("SELECT * FROM COURSE");
        while(rs.next()){
            int rs_course_id = rs.getInt("course_id");
            service.AddCourseFromDB(rs_course_id, rs.getString("course_name"),
                                    rs.getString("course_category"), rs.getInt("months_duration"));
            if(rs_course_id > max_id) max_id = rs_course_id;
        }
        Course.setNumber_of_courses(max_id);
        rs = stmt.executeQuery("SELECT * FROM USER_COURSE WHERE user_id NOT IN (SELECT user_id FROM INSTRUCTOR)");
        while(rs.next()){
            service.AddStudentToCourse(rs.getInt("user_id"), rs.getInt("course_id"));
        }
        rs = stmt.executeQuery("SELECT * FROM USER_COURSE WHERE user_id IN (SELECT user_id FROM INSTRUCTOR)");
        while(rs.next()){
            service.AddInstructorToCourse(rs.getInt("user_id"), rs.getInt("course_id"));
        }
        rs = stmt.executeQuery("SELECT name, course_id, quiz_time, nr_questions" +
                                    " FROM ASSESSMENT JOIN QUIZ USING(ASSESSMENT_ID)");
        while(rs.next()){
            Quiz quiz = new Quiz(rs.getString("name"),
                    rs.getInt("quiz_time"), rs.getInt("nr_questions"));
            service.AddAssessment(rs.getInt("course_id"), service.course_bin_lookup(rs.getInt("course_id")), quiz);
        }

        rs = stmt.executeQuery("SELECT name, course_id, deadline" +
                                    " FROM ASSESSMENT JOIN PROJECT USING(ASSESSMENT_ID)");
        while(rs.next()){
            String project_date = rs.getDate("deadline").toString();
            String project_hour = rs.getTime("deadline").toString();
            Project project = new Project(rs.getString("name") ,project_date, project_hour);
            service.AddAssessment(rs.getInt("course_id"), service.course_bin_lookup(rs.getInt("course_id")), project);
        }
        rs = stmt.executeQuery("SELECT * FROM GRADE");
        while(rs.next()){
            service.AddDBGrade(rs.getDouble("grade_score"), rs.getDouble("grade_max_score"),
                    rs.getString("note"), rs.getInt("course_id"), rs.getInt("user_id"));
        }
    }
}
