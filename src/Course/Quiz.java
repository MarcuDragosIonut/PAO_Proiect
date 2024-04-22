package Course;

import java.util.HashMap;
import java.util.Vector;

class Quiz extends Assessment {
    private boolean Timed;
    private HashMap<Integer, String> question = new HashMap<Integer, String>();
    private HashMap<Integer, Vector<String>> answers = new HashMap<Integer, Vector<String>>();
    private HashMap<Integer, String> correct_answer = new HashMap<Integer, String>();
    private HashMap<Integer, Double> points = new HashMap<Integer, Double>();

    public Quiz(String type, double max_grade, boolean timed) {
        super(type, max_grade);
        Timed = timed;
    }

    public Quiz(String type, double max_grade, int number_of_questions) {
        super(type, max_grade);
    }
}
