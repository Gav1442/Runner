package com.example.runner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Obstacle {

	private int x, y, width, height, xSpeed, ySpeed;
	private Bitmap bitmap;
	private Rect body;

	public Obstacle(Bitmap btmp, int xPos, int yPos) {
		bitmap = btmp;
		height = bitmap.getHeight();
		width = bitmap.getWidth();
		this.x = xPos;
		this.y = yPos-height;
		xSpeed = -5;
		ySpeed = 0;
		body = new Rect(x,y,x+width,y+height);
	}
	
	public boolean update(){
		boolean onScreen = true;
		x = x + xSpeed;
		y = y + ySpeed;
		body.set(x, y, x+height, y+height);
		if(x+width<0){
			return false;
		}else return true;
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
	
	public Rect getBody(){
		return this.body;
	}

}