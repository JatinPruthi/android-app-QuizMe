package projects.jatin.quizme.model;

/**
 * Created by Jateen on 19-11-2017.
 */

public class QuestionScore {

    String Question_Score;
    String User;
    String Score;

    public QuestionScore() {
    }

    public QuestionScore(String question_Score, String user, String score) {
        Question_Score = question_Score;
        User = user;
        Score = score;
    }


}
