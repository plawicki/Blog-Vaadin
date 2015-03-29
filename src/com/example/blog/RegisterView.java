package com.example.blog;

import com.example.blog.model.User;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RegisterView extends VerticalLayout implements View {

	public final static String REGISTER_VIEW = "REGISTER";

	private final FormLayout registerForm;
	private final User user;
	private final BeanItem<User> item;

	private final TextField nameField;
	private final PasswordField passwordField;
	private final Button submit;

	private final Navigator navigator;

	public RegisterView(BlogUI root) {
		this.navigator = root.getNavigator();

		this.registerForm = new FormLayout();
		this.user = new User("", "");
		this.item = new BeanItem<User>(user);
		this.nameField = new TextField("Name:", item.getItemProperty("name"));
		this.nameField.setRequired(true);
		this.passwordField = new PasswordField("Password:",
				item.getItemProperty("password"));
		this.passwordField.addValidator(new PasswordValidator());
		this.passwordField.setRequired(true);
		this.passwordField.setNullRepresentation("");

		this.submit = new Button("Register", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				if (!passwordField.isValid() || !nameField.isValid()) {
					return;
				}

				getSession().getCurrent().setAttribute("userName",
						nameField.getValue());
				Broadcaster.registerUser(user);
				navigator.navigateTo(PostsView.POSTS_VIEW);
			}
		});

		this.registerForm.addComponent(nameField);
		this.registerForm.addComponent(passwordField);
		this.registerForm.addComponent(submit);

		this.addComponent(registerForm);

		this.setSizeFull();

		this.setComponentAlignment(registerForm, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getCurrent().getAttribute("user") == null)
			nameField.focus();
		else
			navigator.navigateTo(PostsView.POSTS_VIEW);
	}

	private static final class PasswordValidator extends
			AbstractValidator<String> {

		public PasswordValidator() {
			super("Password is not valid!");
		}

		@Override
		protected boolean isValidValue(String value) {
			if (value != null
					&& (value.length() < 4 || !value.matches("[A-z]*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

}
