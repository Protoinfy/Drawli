package com.tunjos.drawli;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tunjos.drawli.R;


public class DrawingActivity extends Activity implements OnClickListener{
private DrawingView drawView;
private ImageButton currPaint,drawBtn,eraseBtn,newBtn,saveBtn,opacityBtn;
private float smallBrush, mediumBrush, largeBrush;

private static String FIREBASE_URL = "https://drawapp.firebaseio.com/";
private Firebase ref;
private ValueEventListener connectedListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		
		
		drawView = (DrawingView)findViewById(R.id.drawing);
		drawView.setBrushSize(mediumBrush);
		
		drawBtn = (ImageButton)findViewById(R.id.draw_btn);
		drawBtn.setOnClickListener(this);
		
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
		currPaint = (ImageButton)paintLayout.getChildAt(0);
		
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
		
		eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
		eraseBtn.setOnClickListener(this);
		
		newBtn = (ImageButton)findViewById(R.id.new_btn);
		newBtn.setOnClickListener(this);
		
		saveBtn = (ImageButton)findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(this);
		
		opacityBtn = (ImageButton)findViewById(R.id.opacity_btn);
		opacityBtn.setOnClickListener(this);
		
		ref = new Firebase(FIREBASE_URL);
	}
	

    @Override
    public void onStart() {
        super.onStart();
        // Set up a notification to let us know when we're connected or disconnected from the Firebase servers
        connectedListener = ref.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean)dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(DrawingActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DrawingActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
        });
    }
    @Override
    protected void onStop() {
    	super.onStop();
    	 // Clean up our listener so we don't have it attached twice.
    	 ref.getRoot().child(".info/connected").removeEventListener(connectedListener);
    };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void paintClicked(View view){
	    //use chosen color
		if(view!=currPaint){
			//update color
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			
			drawView.setColor(color);
			
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint=(ImageButton)view;
			}
		
		drawView.setErase(false);
		drawView.setBrushSize(drawView.getLastBrushSize());
	}

	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.draw_btn){
		    //draw button clicked
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Brush size:");
			brushDialog.setContentView(R.layout.brush_chooser);
			
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setBrushSize(smallBrush);
			        drawView.setLastBrushSize(smallBrush);
			        brushDialog.dismiss();
			        drawView.setErase(false);
			    }
			});
			
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setBrushSize(mediumBrush);
			        drawView.setLastBrushSize(mediumBrush);
			        brushDialog.dismiss();
			        drawView.setErase(false);
			    }
			});
			 
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setBrushSize(largeBrush);
			        drawView.setLastBrushSize(largeBrush);
			        brushDialog.dismiss();
			        drawView.setErase(false);
			    }
			});
			brushDialog.show();
		}
		else if(view.getId()==R.id.erase_btn){
		    //switch to erase - choose size
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Eraser size:");
			brushDialog.setContentView(R.layout.brush_chooser);
			
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setErase(true);
			        drawView.setBrushSize(smallBrush);
			        brushDialog.dismiss();
			    }
			});
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setErase(true);
			        drawView.setBrushSize(mediumBrush);
			        brushDialog.dismiss();
			    }
			});
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setErase(true);
			        drawView.setBrushSize(largeBrush);
			        brushDialog.dismiss();
			    }
			});
			
			brushDialog.show();
		}
		else if(view.getId()==R.id.new_btn){
		    //new button
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New drawing");
			newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        drawView.startNew();
			        dialog.dismiss();
			    }
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			newDialog.show();
		}
		else if(view.getId()==R.id.save_btn){
            //save drawing
			AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
			saveDialog.setTitle("Save drawing");
			saveDialog.setMessage("Save drawing to device Gallery?");
			saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        //save drawing
			    	drawView.setDrawingCacheEnabled(true);
			    	String imgSaved = MediaStore.Images.Media.insertImage(
			    		    getContentResolver(), drawView.getDrawingCache(),
			    		    UUID.randomUUID().toString()+".png", "drawing");
			    	if(imgSaved!=null){
			    	    Toast savedToast = Toast.makeText(getApplicationContext(), 
			    	        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
			    	    savedToast.show();
			    	}
			    	else{
			    	    Toast unsavedToast = Toast.makeText(getApplicationContext(), 
			    	        "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
			    	    unsavedToast.show();
			    	}
			    	
			    	drawView.destroyDrawingCache();
			    }
			});
			saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			saveDialog.show();
		}
		else if(view.getId()==R.id.opacity_btn){
		    //launch opacity chooser
			final Dialog seekDialog = new Dialog(this);
			seekDialog.setTitle("Opacity level:");
			seekDialog.setContentView(R.layout.opacity_chooser);
			
			final TextView seekTxt = (TextView)seekDialog.findViewById(R.id.opq_txt);
			final SeekBar seekOpq = (SeekBar)seekDialog.findViewById(R.id.opacity_seek);
			seekOpq.setMax(100);
			
			int currLevel = drawView.getPaintAlpha();
			seekTxt.setText(currLevel+"%");
			seekOpq.setProgress(currLevel);
			
			seekOpq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			    @Override
			    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			        seekTxt.setText(Integer.toString(progress)+"%");
			    }
			    @Override
			    public void onStartTrackingTouch(SeekBar seekBar) {}
			    @Override
			    public void onStopTrackingTouch(SeekBar seekBar) {}
			});
			
			Button opqBtn = (Button)seekDialog.findViewById(R.id.opq_ok);
			opqBtn.setOnClickListener(new OnClickListener(){
			    @Override
			    public void onClick(View v) {
			        drawView.setPaintAlpha(seekOpq.getProgress());
			        seekDialog.dismiss();
			    }
			});
			
			seekDialog.show();
		}
		
	}
	

}
