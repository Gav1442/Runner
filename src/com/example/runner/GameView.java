package com.example.runner;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
implements SurfaceHolder.Callback{

	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private boolean onTitle = true;
	private GameThread thread;
	private static final String TAG = "GameView";
	private Actor actor; 
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		
		thread = new GameThread(getHolder(), this);
		setFocusable(true);
	}
	
	public GameThread getThread(){
		return thread;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		SCREEN_HEIGHT = height;
		SCREEN_WIDTH = width;
		Log.d(TAG, "Width: " + SCREEN_WIDTH +", Height: " + SCREEN_HEIGHT);
		actor = new Actor(BitmapFactory.decodeResource(getResources(), R.drawable.red_blob));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			}catch (InterruptedException e){
				//Loop again to try and shut down the thread
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			if (event.getY() > getHeight()/2){
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ", y=" + event.getY());
			}
		}
		return super.onTouchEvent(event);
	}
	
	protected void update(){
		actor.update();
	}
	
	protected void render(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		actor.render(canvas);
	}
}
