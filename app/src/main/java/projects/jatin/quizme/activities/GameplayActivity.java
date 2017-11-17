package projects.jatin.quizme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import projects.jatin.quizme.R;
import projects.jatin.quizme.common.Common;

public class GameplayActivity extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 7000;
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    //Firebase

    FirebaseDatabase database;
    DatabaseReference questions;

    private ProgressBar progressBar;
    private ImageView ivQuestion;
    private Button btnAnswerA;
    private Button btnAnswerB;
    private Button btnAnswerC;
    private Button btnAnswerD;
    private TextView txtScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        initView();

    }

    private void initView() {
        progressBar=findViewById(R.id.progressBar);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        btnAnswerA = (Button) findViewById(R.id.btnAnswerA);
        btnAnswerB = (Button) findViewById(R.id.btnAnswerB);
        btnAnswerC = (Button) findViewById(R.id.btnAnswerC);
        btnAnswerD = (Button) findViewById(R.id.btnAnswerD);
        txtScore=findViewById(R.id.txtScore);

        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        mCountDown.cancel();

        if(index<totalQuestion)
        {
            Button clickedButton= (Button) v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                score+=10;
                correctAnswer++;
                showQuestion(++index);
            }
            else
            {
                 // wrong answer chosen
                Intent intent=new Intent(this,GameOverActivity.class);
                Bundle dataSend=new Bundle();
                dataSend.putInt("SCORE",score);
                dataSend.putInt("TOTAL",totalQuestion);
                dataSend.putInt("CORRECT",correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }

            txtScore.setText(String.format("%d",score));
        }

        switch (v.getId()) {
            case R.id.btnAnswerA:

                break;
            case R.id.btnAnswerB:

                break;
            case R.id.btnAnswerC:

                break;
            case R.id.btnAnswerD:

                break;
        }
    }

    private void showQuestion(int i) {

        if(index<totalQuestion)
        {
            thisQuestion++;

        }

    }
}
