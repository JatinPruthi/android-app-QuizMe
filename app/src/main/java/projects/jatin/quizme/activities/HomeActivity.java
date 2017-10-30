package projects.jatin.quizme.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import projects.jatin.quizme.R;
import projects.jatin.quizme.fragments.CategoriesFragment;
import projects.jatin.quizme.fragments.RankingsFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                switch(item.getItemId())
                {
                    case R.id.action_category:
                        selectedFragment= CategoriesFragment.newInstance();
                        break;
                    case R.id.action_ranking:
                        selectedFragment= RankingsFragment.newInstance();
                        break;
                }

                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,selectedFragment);
                transaction.commit();
                return true;
            }


        });

        selectDefaultFragment();
    }

    private void selectDefaultFragment() {


        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,CategoriesFragment.newInstance());
        transaction.commit();

    }

}
