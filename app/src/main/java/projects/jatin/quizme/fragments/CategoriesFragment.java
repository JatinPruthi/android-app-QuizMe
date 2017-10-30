package projects.jatin.quizme.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import projects.jatin.quizme.R;
import projects.jatin.quizme.interfaces.ItemClickListener;
import projects.jatin.quizme.model.Categories;
import projects.jatin.quizme.viewholder.CategoriesViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Categories,CategoriesViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;

    public static CategoriesFragment newInstance() {
        // Required empty public constructor
        CategoriesFragment categoriesFragment=new CategoriesFragment();
        return categoriesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment=inflater.inflate(R.layout.fragment_categories,container,false);

        listCategory=myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();

        return myFragment;
    }

    private void loadCategories() {

        adapter=new FirebaseRecyclerAdapter<Categories, CategoriesViewHolder>(
                Categories.class,R.layout.categories_layout,
                CategoriesViewHolder.class, categories
        ) {

            @Override
            protected void populateViewHolder(CategoriesViewHolder viewHolder, final Categories model, int position) {

                viewHolder.categoryName.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.categoryImage);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(), String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }

}
