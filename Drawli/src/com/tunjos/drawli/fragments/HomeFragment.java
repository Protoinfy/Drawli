package com.tunjos.drawli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tunjos.drawli.DrawingActivity;
import com.tunjos.drawli.R;

public class HomeFragment extends Fragment {
	private Button drawBtn;
	public HomeFragment(){}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		drawBtn = (Button)getActivity().findViewById(R.id.new_button);
		drawBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent drawIntent = new Intent(getActivity().getApplicationContext(), DrawingActivity.class);
				startActivity(drawIntent);
				
			}
		});
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
          
        return rootView;
    }
    
}
