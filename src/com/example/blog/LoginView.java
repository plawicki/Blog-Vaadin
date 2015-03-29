package com.example.blog;

import com.example.blog.model.User;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class LoginView extends VerticalLayout implements View {

	public final static String LOGIN_VIEW = "LOGIN";

	private final FormLayout loginForm;

	private final TextField nameField;
	private final PasswordField passwordField;
	private final Button submit;

	public LoginView(BlogUI root) {
		this.setSizeFull();
		this.loginForm = new FormLayout();
		this.nameField = new TextField("Name:");
		this.nameField.setRequired(true);
		this.passwordField = new PasswordField("Password:");
		this.passwordField.setRequired(true);

		this.loginForm.addComponent(nameField);
		this.loginForm.addComponent(passwordField);

		this.submit = new Button("Login", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				for (User user : root.getUsers()) {
					if (user.getName().equals(nameField.getValue())
							&& user.getPassword().equals(passwordField.getValue())) {
						getSession().getCurrent().setAttribute("userName", user.getName());
						root.getNavigator().navigateTo(PostsView.POSTS_VIEW);
						return;
					}
				}
			}
		});

		this.loginForm.addComponent(nameField);
		this.loginForm.addComponent(passwordField);
		this.loginForm.addComponent(submit);
		this.addComponent(loginForm);

		this.setSizeFull();

		this.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		nameField.focus();
	}
}
