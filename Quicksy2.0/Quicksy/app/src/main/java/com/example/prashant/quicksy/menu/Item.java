package com.example.prashant.quicksy.menu;

import android.graphics.Bitmap;

public class Item {
	Bitmap image;
	String title, id;

	public Item(Bitmap image, String title) {
		super();
		this.image = image;
		this.title = title;
		this.id = id;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
}
