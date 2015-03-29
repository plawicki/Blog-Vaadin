package com.example.blog.model;

public class Post {
	private String title;
	private String text;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Post(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}
}
