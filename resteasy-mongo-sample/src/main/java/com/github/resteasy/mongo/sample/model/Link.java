package com.github.resteasy.mongo.sample.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;

@Entity
public class Link implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id private ObjectId id;
	private String url;
	private String description;
	@Embedded
	private Set<Comment> comments = new LinkedHashSet<Comment>();

	public String getDescription() {
		return description;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public String getId() {
		return id.toString();
	}
	public String getUrl() {
		return url;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
