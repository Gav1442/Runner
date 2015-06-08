package com.example.runner;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Actor {
	private int x, y, width, height, xSpeed, ySpeed, jumpHeight, jumpMax, jumpSpeed;
	private boolean inAir, alive;
	private Bitmap bitmap;
	private static final String TAG = "Actor";
	private Rect body;

	public Actor(Bitmap btmp, int xPos, int yPos) {
		bitmap = btmp;
		height = bitmap.getHeight();
		width = bitmap.getWidth();
		x = xPos;
		y = yPos-height;
		jumpHeight = 0;
		jumpMax =  (int)(height*2.2);
		jumpSpeed = (int)jumpMax/10;
		Log.d(TAG,"Jump Max: " + jumpMax + ", Jump Speed: " + jumpSpeed + ".");
		inAir = false;
		alive = true;
		xSpeed = 0;
		ySpeed = 0;
		body = new Rect(x,y,x+width,y+height);
		Log.d(TAG, "Done Actor");
	}

	public boolean update(int floor, ArrayList<Obstacle> obstacles){
		alive = true;
		if(inAir && jumpHeight >= jumpMax){
			ySpeed *= -1;
		}
		if(inAir && y+height+ySpeed >= floor){
			y = y +(floor-(y+height));
			Log.d(TAG,"Floor: " + floor + ", Feet: " + (y+height) + ".");
			inAir = false;
			ySpeed = 0;
			jumpHeight = 0;
		}
	
		x = x + xSpeed;
		y = y + ySpeed;
		jumpHeight = jumpHeight - ySpeed;
		body.set(x,y,x+width,y+height);
		
		for (int spot = 0; spot < obstacles.size(); spot++){
			if(checkCollision(obstacles.get(spot))){
				alive = false;
			}
		}
		return alive;
	}
	
	public boolean checkCollision(Obstacle obstacle){
		boolean hit = false;
		if (body.intersect(obstacle.getBody())){
			hit = true;
		}
		return hit;
	}
	
	public void jump(){
		if(!inAir){
			inAir = true;
			ySpeed = -jumpSpeed;
		}
	}
	
	public void render(Canvas canvas){
		canvas.drawBitmap(bitmap, x, y, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
