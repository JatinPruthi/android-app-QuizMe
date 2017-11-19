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
import com.squareup.picasso.Picasso;

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
    private TextView txtScore,txtQuestionNum,questionText;


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
        txtQuestionNum=findViewById(R.id.txtTotalQuestion);
        questionText=findViewById(R.id.txtQuestion);

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
//                Intent intent=new Intent(this,GameOverActivity.class);
//                Bundle dataSend=new Bundle();
//                dataSend.putInt("SCORE",score);
//                dataSend.putInt("TOTAL",totalQuestion);
//                dataSend.putInt("CORRECT",correctAnswer);
//                intent.putExtras(dataSend);
//                startActivity(intent);
//                finish();
                showQuestion(++index);
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
            txtQuestionNum.setText(String.format("%d/%d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;
            if(Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                //image question
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(ivQuestion);
                ivQuestion.setVisibility(View.VISIBLE);
                questionText.setVisibility(View.GONE);
            }
            else
            {
                questionText.setText(Common.questionList.get(index).getQuestion());

                ivQuestion.setVisibility(View.GONE);
                questionText.setVisibility(View.VISIBLE);
            }

            btnAnswerA.setText(Common.questionList.get(index).getAnswerA());
            btnAnswerB.setText(Common.questionList.get(index).getAnswerB());
            btnAnswerC.setText(Common.questionList.get(index).getAnswerC());
            btnAnswerD.setText(Common.questionList.get(index).getAnswerD());

            //start the timer
            mCountDown.start();


        }
        else
        {
            //for final question
            Intent intent=new Intent(this,GameOverActivity.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion=Common.questionList.size();

        mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long l) {

                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {

                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}
