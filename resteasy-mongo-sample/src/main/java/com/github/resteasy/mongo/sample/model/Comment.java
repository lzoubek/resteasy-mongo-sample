package com.github.resteasy.mongo.sample.model;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Comment {

	private String nick;
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return message;
	}

	public String getNick() {
		return nick;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}

}
