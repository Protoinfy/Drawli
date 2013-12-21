package com.tunjos.drawli;

import com.tunjos.drawli.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MainActivity extends Activity {

	private Button signinBtn, signupBtn;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		signupBtn = (Button)findViewById(R.id.signup_button);
		signinBtn = (Button)findViewById(R.id.signin_button);
		
		OnClickListener btnListener = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch (view.getId()) {
				case R.id.signup_button:
					Intent signupIntent = new Intent(getApplicationContext(), SignUpActivity.class);
					startActivity(signupIntent);
					break;
				case R.id.signin_button:
					Intent signinpIntent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(signinpIntent);
					break;
				default:
					break;
				}
			}
		};
		
		signupBtn.setOnClickListener(btnListener);
		signinBtn.setOnClickListener(btnListener);
		
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drawing, menu);
		return true;
	}
	
	
    
    

}
