package com.example.blog;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddPostView extends VerticalLayout implements View {

	public final static String ADD_POST_VIEW = "ADD_POST";

	private final FormLayout postForm;
	private Post post = new Post("", "");
	private BeanItem<Post> item = new BeanItem<Post>(post);

	private final TextField titleField;
	private final RichTextArea textField;
	private final Button submit;

	private final Navigator navigator;

	public AddPostView(BlogUI root) {

		this.navigator = root.getNavigator();

		this.postForm = new FormLayout();
		this.titleField = new TextField("Title: ",
				item.getItemProperty("title"));
		this.textField = new RichTextArea("Text:", item.getItemProperty("text"));
		submit = new Button("Add post", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String userName = (String) getSession().getCurrent()
						.getAttribute("userName");

				for (User user : root.getUsers()) {
					if (user.getName().equals(userName)) {
						user.getPosts().add(new Post(post.getTitle(), post.getText()));
						Broadcaster.addPost(new Post(post.getTitle(), post.getText()), user);
					}
				}
				navigator.navigateTo(PostsView.POSTS_VIEW);
			}
		});

		this.postForm.addComponent(titleField);
		this.postForm.addComponent(textField);
		this.postForm.addComponent(submit);

		this.setSizeFull();

		this.addComponent(postForm);
		this.setComponentAlignment(postForm, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		post.setText("");
		post.setTitle("");
		// TODO Auto-generated method stub
		if (getSession().getCurrent().getAttribute("userName") == null)
			navigator.navigateTo(LoginView.LOGIN_VIEW);
	}

}
