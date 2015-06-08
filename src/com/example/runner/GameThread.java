package com.example.runner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

class GameThread extends Thread{
	private boolean running = false;
	private SurfaceHolder surfaceHolder;
	private GameView gameView;
	private static final String TAG = "GameThread";
	
	public GameThread(SurfaceHolder surfaceHolder, GameView gameView){
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameView = gameView;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run(){
		Canvas canvas;
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		while(running){
			canvas = null;
			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder){
					//update
					this.gameView.update();
					//render
					this.gameView.render(canvas);
				}
			}finally{
				//in case of an exception so that the surface is not left in an inconsistent state
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			tickCount++;
			
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times!");
	}

}
