package com.example.runner;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static int SCREEN_WIDTH, SCREEN_HEIGHT, FLOOR, CEILING;
	private boolean onTitle = true;
	private GameThread thread;
	private static final String TAG = "GameView";
	private Actor actor;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private long lastUpperObs, lastLowerObs;

	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);

		thread = new GameThread(getHolder(), this);
		setFocusable(true);
	}

	public GameThread getThread() {
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
		FLOOR = SCREEN_HEIGHT * 4 / 5;
		CEILING = SCREEN_HEIGHT *2/5;
		Log.d(TAG, "Width: " + SCREEN_WIDTH + ", Height: " + SCREEN_HEIGHT);
		actor = new Actor(BitmapFactory.decodeResource(getResources(),
				R.drawable.red_blob), SCREEN_WIDTH / 2, FLOOR);
		lastUpperObs = System.currentTimeMillis();
		lastUpperObs = lastLowerObs + 1400;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// Loop again to try and shut down the thread
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			actor.jump();
			Log.d(TAG, "Coords: x=" + event.getX() + ", y=" + event.getY());
		}
		return super.onTouchEvent(event);
	}

	protected void update() {
		//---------- Create new obstacle logic ---------
		if (System.currentTimeMillis() - lastUpperObs > 3000) {
			double rand = Math.random();
			if(rand <= 0.58 && rand >= 0.48){
				obstacles.add(new Obstacle(BitmapFactory.decodeResource(
					getResources(), R.drawable.red_blob), SCREEN_WIDTH, CEILING));
				Log.d(TAG, "Current: " + System.currentTimeMillis() + ", Last Upper: " + lastUpperObs + ".");
				lastUpperObs = System.currentTimeMillis();
			}
		}
		if (System.currentTimeMillis() - lastLowerObs > 3000) {
			double rand = Math.random();
			if(rand <= 0.65 && rand >= 0.55){
				obstacles.add(new Obstacle(BitmapFactory.decodeResource(
						getResources(), R.drawable.red_blob), SCREEN_WIDTH, FLOOR));
				Log.d(TAG, "Current: " + System.currentTimeMillis() + ", Last Upper: " + lastLowerObs + ".");
				lastLowerObs = System.currentTimeMillis();
			}
		}
		//--------- Update Obstacles ----------------
		if (!obstacles.isEmpty()) {
			// Should arguably find a better method to do this...
			for (int spot = obstacles.size() - 1; spot >= 0; spot--) {
				if (!obstacles.get(spot).update()) {
					obstacles.remove(spot);
				}
			}
		}
		//---------- Update Actor -------------------
		if (!actor.update(FLOOR, obstacles)) {
			Log.d(TAG, "Collision!");
			//thread.setRunning(false);
			//((Activity) getContext()).finish();
		}
	}
 
	protected void render(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
		canvas.drawColor(Color.BLACK);
		canvas.drawLine(0, FLOOR, SCREEN_WIDTH, FLOOR, paint);
		actor.render(canvas);
		Log.d(TAG, "Size of obstacles: " + obstacles.size());
		for (int spot = 0; spot < obstacles.size(); spot++) {
			obstacles.get(spot).render(canvas);
		}
	}
}
