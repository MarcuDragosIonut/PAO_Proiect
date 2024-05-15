import Services.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Services service = new Services();
    public static void main(String[] args) throws SQLException, IOException {

        LoadDB.load_DB();

        int console_input = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!\n");
        while (console_input != 0) {
            System.out.println("\nEnter 0 to quit\nEnter 1 to manage users\nEnter 2 to manage courses\nEnter 3 to manage grades\nEnter 4 to manage assessments");
            console_input = scanner.nextInt();
            switch (console_input) {
                case 0:
                    break;
                case 1:
                    int console_input_1 = -1;
                    while (console_input_1 != 0) {
                        System.out.println("\nEnter 0 to return\nEnter 1 to add a student\nEnter 2 to display students\n" +
                                "Enter 3 to add an instructor\nEnter 4 to display instructors\nEnter 5 to display a student's info\n");
                        console_input_1 = scanner.nextInt();
                        switch (console_input_1) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("Enter the student's name:");
                                String student_name = scanner.nextLine();
                                student_name = scanner.nextLine();
                                service.CreateNewStudent(student_name);
                                break;
                            case 2:
                                service.DisplayStudents();
                                break;
                            case 3:
                                System.out.println("Enter the instructor's name:");
                                String instructor_name = scanner.nextLine();
                                instructor_name = scanner.nextLine();
                                System.out.println("Enter the instructor's profession:");
                                String profession = scanner.nextLine();
                                service.CreateNewInstructor(instructor_name, profession);
                                break;
                            case 4:
                                service.DisplayInstructors();
                                break;
                            case 5:
                                System.out.println("Enter the student's id:");
                                int student_id = scanner.nextInt();
                                service.ShowStudentDetails(student_id);
                        }
                    }
                    break;
                case 2:
                    int console_input_2 = -1;
                    while (console_input_2 != 0) {
                        System.out.println("Enter 0 to return\nEnter 1 to add a course\nEnter 2 to display all courses\nEnter 3 to display a course's information" +
                                "\nEnter 4 to assign a user to a course\n");
                        console_input_2 = scanner.nextInt();
                        int course_id;
                        switch (console_input_2) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("Enter the course's name:");
                                String course_name = scanner.nextLine();
                                course_name = scanner.nextLine();
                                System.out.println("Enter the course's category:");
                                String course_categ = scanner.nextLine();
                                System.out.println("Enter the amount of months the course spans:");
                                int course_months = scanner.nextInt();
                                service.CreateNewCourse(course_name, course_categ, course_months);
                                break;
                            case 2:
                                service.DisplayCourses();
                                break;
                            case 3:
                                System.out.println("Enter the course id:");
                                course_id = scanner.nextInt();
                                service.ShowCourseDetails(course_id);
                                break;
                            case 4:
                                int console_input_2_3 = -1;
                                System.out.println("\nEnter 1 to associate a student | 2 to associate an instructor\n");
                                console_input_2_3 = scanner.nextInt();
                                switch (console_input_2_3) {
                                    case 1:
                                        System.out.println("Enter the student's id and the course's id:");
                                        int student_id = scanner.nextInt();
                                        course_id = scanner.nextInt();
                                        service.AddStudentToCourse(student_id, course_id);
                                        break;
                                    case 2:
                                        System.out.println("Enter the instructor's id and the course's id:");
                                        int instructor_id = scanner.nextInt();
                                        course_id = scanner.nextInt();
                                        service.AddInstructorToCourse(instructor_id, course_id);
                                        break;
                                    default:
                                        break;
                                }
                        }
                    }
                    break;
                case 3:
                    int console_input_3 = -1;
                    while (console_input_3 != 0) {
                        System.out.println("\nEnter 0 to return\nEnter 1 to add a grade to a student\n");
                        console_input_3 = scanner.nextInt();
                        switch (console_input_3) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("Enter the grade:");
                                int grade = scanner.nextInt();
                                System.out.println("Enter the maximum grade:");
                                int max_grade = scanner.nextInt();
                                System.out.println("Enter the associated course id:");
                                int course_id = scanner.nextInt();
                                System.out.println("Enter the student's id:");
                                int student_id = scanner.nextInt();
                                System.out.println("Enter details about the grade:");
                                String note = scanner.nextLine();
                                note = scanner.nextLine();
                                service.AddGrade(grade, max_grade, course_id, student_id, note);
                                break;
                        }
                    }
                    break;
                case 4:
                    int console_input_4 = -1;
                    while (console_input_4 != 0) {
                        System.out.println("\nEnter 0 to return\nEnter 1 to add a quiz\nEnter 2 to add a project\n");
                        console_input_4 = scanner.nextInt();
                        String name="", description="";
                        int course_id = -1;
                        if(console_input_4 == 1 || console_input_4 == 2){
                            System.out.println("Name:");
                            name = scanner.nextLine();
                            name = scanner.nextLine();
                        }
                        switch (console_input_4) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("Number of questions:");
                                int nr_q = scanner.nextInt();
                                System.out.println("Quiz time limit (in minutes):");
                                int time_lim = scanner.nextInt();
                                System.out.println("Quiz's course id:");
                                course_id = scanner.nextInt();
                                service.AddQuiz(name, nr_q, time_lim, course_id);
                                break;
                            case 2:
                                System.out.println("Deadline date (format: yyyy-mm-dd):");
                                String date = scanner.nextLine();
                                System.out.println("Deadline hour (format: hh:mm):");
                                String hour = scanner.nextLine();
                                System.out.println("Quiz's course id:");
                                course_id = scanner.nextInt();
                                service.AddProject(name, date, hour, course_id);
                                break;
                        }
                    }
                    break;
            }
        }
        service.close_connection();
        service.close_file();
    }
}