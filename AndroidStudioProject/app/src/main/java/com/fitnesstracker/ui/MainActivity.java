package com.fitnesstracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.fitnesstracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

	public static final String EXTRA_MESSAGE = "Hello message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar topAppBar = (Toolbar) findViewById(R.id.top_toolbar);
		setSupportActionBar(topAppBar);
		topAppBar.showOverflowMenu();

		NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
				.findFragmentById(R.id.nav_host_fragment);
		assert navHostFragment != null;
		NavController navController = navHostFragment.getNavController();
		BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
		NavigationUI.setupWithNavController(bottomNav, navController);



		//AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.diary_page, R.id.food_page, R.id.exercise_page).build();
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		return onAppBarItemSelected(item)
				|| NavigationUI.onNavDestinationSelected(item, navController)
				|| super.onOptionsItemSelected(item);
	}

	public boolean onAppBarItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_open_settings:
				Toast.makeText(this, "Opening the settings menu", Toast.LENGTH_SHORT).show();
				openSettings();
				return true;
			default:
				return false;
		}
	}

	@Override public boolean onPrepareOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_toolbar, menu);

		return super.onPrepareOptionsMenu(menu);
	}

	public void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	/**
     * Switch to a fragment using the FragmentManager.
     *
     * @param fragment the fragment to switch to
     */
    public void openFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}