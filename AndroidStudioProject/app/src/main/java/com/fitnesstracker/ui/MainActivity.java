package com.fitnesstracker.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fitnesstracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.diary_page:
                        DiaryFragment diaryFragment = DiaryFragment.newInstance();
                        openFragment(diaryFragment);
                        return true;
                    case R.id.food_page:
                        FoodFragment foodFragment = FoodFragment.newInstance();
                        openFragment(foodFragment);
                        return true;
                    case R.id.exercise_page:
                        ExerciseFragment exerciseFragment = ExerciseFragment.newInstance();
                        openFragment(exerciseFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * Switch to a fragment using the FragmentManager.
     *
     * @param fragment the fragment to switch to
     */
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}