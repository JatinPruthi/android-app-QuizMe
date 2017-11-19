package projects.jatin.quizme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import projects.jatin.quizme.R;
import projects.jatin.quizme.common.Common;
import projects.jatin.quizme.model.Question;

public class StartQuizActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay;
    FirebaseDatabase database;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        initView();

        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");

        loadQuestions(Common.categoryId);
    }

    private void loadQuestions(final String categoryId) {

        //clearing the list incase its not empty
        if(Common.questionList.size()>0)
            Common.questionList.clear();

        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren())
                        {
                            Question question=postSnapshot.getValue(Question.class);
                            Common.questionList.add(question);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //Randomise the list
        Collections.shuffle(Common.questionList);

    }

    private void initView() {
        btnPlay = (Button) findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:

                startActivity(new Intent(StartQuizActivity.this,GameplayActivity.class));
                finish();

                break;
        }
    }
}
