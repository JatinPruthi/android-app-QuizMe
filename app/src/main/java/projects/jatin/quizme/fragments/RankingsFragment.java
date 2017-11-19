package projects.jatin.quizme.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import projects.jatin.quizme.R;
import projects.jatin.quizme.common.Common;
import projects.jatin.quizme.interfaces.RankingCallBack;
import projects.jatin.quizme.model.QuestionScore;
import projects.jatin.quizme.model.Ranking;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {


    View myFragment;

    FirebaseDatabase database;
    DatabaseReference questionScore,rankingTable;

    int sum=0;

    public static RankingsFragment newInstance() {
        // Required empty public constructor
        RankingsFragment rankingsFragment=new RankingsFragment();
        return rankingsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database=FirebaseDatabase.getInstance();
        questionScore=database.getReference("Question_Score");
        rankingTable=database.getReference("Ranking");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment=inflater.inflate(R.layout.fragment_rankings,container,false);

        updateScore(Common.currentUser.getUsername(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {

                //updating the rankings table
                rankingTable.child(ranking.getUserName())
                        .setValue(ranking);
//                showRanking();
            }
        });

        return myFragment;
    }

    private void showRanking() {
        rankingTable.orderByChild("score")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren())
                        {
                            Ranking local=data.getValue(Ranking.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void updateScore(final String userName, final RankingCallBack<Ranking> callBack) {

        questionScore.orderByChild("user").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            QuestionScore questionScore=data.getValue(QuestionScore.class);
                            sum+=Integer.parseInt(questionScore.getScore());
                        }

                        Ranking ranking= new Ranking(userName,sum);
                        callBack.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
