package projects.jatin.quizme.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.jatin.quizme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {


    View myFragment;

    public static RankingsFragment newInstance() {
        // Required empty public constructor
        RankingsFragment rankingsFragment=new RankingsFragment();
        return rankingsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment=inflater.inflate(R.layout.fragment_rankings,container,false);

        return myFragment;
    }
}
