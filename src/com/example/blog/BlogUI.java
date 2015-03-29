package com.example.blog;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.example.blog.Broadcaster.BroadcastListener;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("blog")
@Push
public class BlogUI extends UI implements BroadcastListener {

	private static final ArrayList<User> users = new ArrayList<>();
	private Navigator navigator;

	// views
	private PostsView posts;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BlogUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		Broadcaster.register(this);

		navigator = new Navigator(this, this);
		navigator.addView(RegisterView.REGISTER_VIEW, new RegisterView(this));
		posts = new PostsView(users, navigator);
		navigator.addView(LoginView.LOGIN_VIEW, new LoginView(this));
		navigator.addView(PostsView.POSTS_VIEW, posts);
		navigator.addView(AddPostView.ADD_POST_VIEW, new AddPostView(this));

		navigator.navigateTo(PostsView.POSTS_VIEW);
	}

	@Override
	public void registerUser(User user) {

		users.add(user);
		System.out.println("ADDED USER " + users.size());
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	@Override
	public void addPost(Post post, User owner) {
		access(new Runnable() {
			@Override
			public void run() {
				if (getSession().getCurrent().getAttribute("userName") == null) {
					posts.addPost(post, owner);
					return;
				}

				if (getSession().getCurrent().getAttribute("userName") != null
						&& !((String) (getSession().getCurrent().getAttribute("userName"))).equals(owner.getName())
						) {
					posts.addPost(post, owner);
				}
			}
		});
	}
}