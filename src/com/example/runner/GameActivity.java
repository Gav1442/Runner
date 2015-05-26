package com.example.runner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;



public class GameActivity extends Activity {

	private static final String TAG = "GameActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set our gameView as the View
     
        setContentView(new GameView(this));
        Log.d(TAG,"View added");
    }

    protected void onDestroy(){
    	Log.d(TAG,"Destroying");
    	super.onDestroy();
    }
    
    protected void onStop(){
    	Log.d(TAG, "Stopping");
    	super.onStop();
    }
}
