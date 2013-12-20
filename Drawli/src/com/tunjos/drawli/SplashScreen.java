package com.tunjos.drawli;

import com.tunjos.drawli.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	
	//Splash screen timeout
	private static int SPLASH_TIME = 2800;
	Runnable splashrun;
	
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		 
		
		splashrun = new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
	            startActivity(i);
	 
	            // close this activity
	            finish();
			}
		};
		handler  = new Handler();
		handler.postDelayed(splashrun, SPLASH_TIME);
	}

	@Override
    public void onPause() {
        super.onPause();
        if (handler!= null) {
            handler.removeCallbacks(splashrun);
        }
    }

}