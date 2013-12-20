package com.tunjos.drawli.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tunjos.drawli.R;

public class DrawFragment extends Fragment {

	public DrawFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_draw, container, false);
          
        return rootView;
    }
    
    
}
