package com.example.blog;

import java.util.ArrayList;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class PostsView extends VerticalLayout implements View {

	public final static String POSTS_VIEW = "POSTS";

	private Button logoutButton;
	private Button loginButton;
	private Button registerButton;
	private Button newPostButton;
	private String name = "";

	private Navigator navigator;

	private ArrayList<User> users;

	public PostsView(ArrayList<User> users, Navigator navigator) {
		this.users = users;
		this.navigator = navigator;

		for (User user : users) {
			addPostsFromUser(user);
		}

		this.setSizeFull();
	}

	public void addPostsFromUser(User user) {
		for (Post post : user.getPosts()) {
			RichTextArea h = new RichTextArea(post.getTitle(), post.getText()
					.concat("\n\n" + user.getName() + ""));
			h.addStyleName("no-toolbar-top");
			h.addStyleName("no-toolbar-bottom");
			h.setEnabled(false);
			this.addComponent(h);
		}
	}

	public void addPost(Post post, User owner) {
		RichTextArea h = new RichTextArea(post.getTitle(), post.getText()
				.concat("\n\n" + owner.getName() + ""));
		h.addStyleName("no-toolbar-top");
		h.addStyleName("no-toolbar-bottom");
		h.setEnabled(false);
		this.addComponent(h);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		this.removeAllComponents();

		for (User user : users) {
			addPostsFromUser(user);
		}

		if (getSession().getCurrent() != null
				&& getSession().getCurrent().getAttribute("userName") != null) {
			this.name = (String) getSession().getCurrent().getAttribute(
					"userName");
			this.logoutButton = new Button("Logout " + name,
					new Button.ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							getSession().getCurrent().setAttribute("userName",
									null);
							navigator.navigateTo(POSTS_VIEW);
						}
					});
			this.newPostButton = new Button("New Post",
					new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							navigator.navigateTo(AddPostView.ADD_POST_VIEW);
						}
					});

			this.addComponent(logoutButton);
			this.addComponent(newPostButton);
		} else {
			this.loginButton = new Button("Login", new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(LoginView.LOGIN_VIEW);
				}
			});

			this.registerButton = new Button("Register",
					new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							navigator.navigateTo(RegisterView.REGISTER_VIEW);
						}
					});

			this.addComponent(loginButton);
			this.addComponent(registerButton);
		}
	}
}
