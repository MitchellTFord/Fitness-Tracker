package com.fitnesstracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.fitnesstracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    private Button ViewProgress;

    public FoodFragment() {
        // Required empty public constructor
        Button ViewProgress;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
	 * @return A new instance of fragment FoodFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static FoodFragment newInstance() {
		FoodFragment fragment = new FoodFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_food, container, false);
	}

}