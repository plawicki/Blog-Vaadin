package com.example.blog;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.blog.model.Post;
import com.example.blog.model.User;

public class Broadcaster {

	private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();
	private static BroadcastListener br;
	static ExecutorService executorService =
	        Executors.newSingleThreadExecutor();
	
	public static void register(final BroadcastListener listener) {
		listeners.add(listener);
		br = listener;
	}

	public static void unregister(final BroadcastListener listener) {
		listeners.remove(listener);
		br = null;
	}

	public static void registerUser(User user) {
		br.registerUser(user);
	}

	public static void addPost(Post post, User owner) {
		for (BroadcastListener listener : listeners) {
			executorService.execute(new Runnable() {
                @Override
                public void run() {
                	listener.addPost(post, owner);
                }
            });
		}
	}

	public interface BroadcastListener {
		public void registerUser(User user);

		public void addPost(Post post, User owner);
	}
}