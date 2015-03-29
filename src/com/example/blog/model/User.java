package com.example.blog.model;

import java.util.ArrayList;

public class User implements Comparable<User> {

	private String name;
	private String password;
	private ArrayList<Post> posts = new ArrayList<>();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public User(String name, ArrayList<Post> posts) {
		super();
		this.name = name;
		this.posts = posts;
	}

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	@Override
	public int compareTo(User o) {
		if (this.name.equals(o.getName())
				&& this.password.equals(o.getPassword())) {
			return 0;
		}
		return -1;
	}
}
