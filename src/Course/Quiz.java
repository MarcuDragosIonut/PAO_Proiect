package Course;

import java.util.HashMap;
import java.util.Vector;

public class Quiz extends Assessment {
    private int Time; // in minute
    private int Number_of_questions;

    public Quiz(String name, String description, int nr_questions, int time) {
        super(name, description);
        Time = time;
        Number_of_questions = nr_questions;
    }

    public Quiz(String name, String description, int nr_questions) {
        super(name, description);
        Time = 0;
        Number_of_questions = nr_questions;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getNumber_of_questions() {
        return Number_of_questions;
    }

    public void setNumber_of_questions(int number_of_questions) {
        Number_of_questions = number_of_questions;
    }
}
