package com.example.runner;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Actor {
	private int x, y, width, height, xSpeed, ySpeed;
	private Bitmap bitmap;

	public Actor(Bitmap btmp) {
		bitmap = btmp;
		height = bitmap.getHeight();
		width = bitmap.getWidth();
		x = 50;
		y = 50;
		xSpeed = 1;
		ySpeed = 0;
	}
	
	public void update(){
		x = x + xSpeed;
		y = y + ySpeed;
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
