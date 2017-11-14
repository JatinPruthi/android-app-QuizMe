package projects.jatin.quizme.model;

/**
 * Created by Jateen on 15-11-2017.
 */

public class Question {

    private String Question, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer, categoryId, isImageQuestion;


    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryId, String isImageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CorrectAnswer = correctAnswer;
        this.categoryId = categoryId;
        this.isImageQuestion = isImageQuestion;
    }


}
