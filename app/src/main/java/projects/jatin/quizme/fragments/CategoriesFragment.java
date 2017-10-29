package projects.jatin.quizme.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.jatin.quizme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;

    public static CategoriesFragment newInstance() {
        // Required empty public constructor
        CategoriesFragment categoriesFragment=new CategoriesFragment();
        return categoriesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment=inflater.inflate(R.layout.fragment_categories,container,false);

        return myFragment;
    }

}
