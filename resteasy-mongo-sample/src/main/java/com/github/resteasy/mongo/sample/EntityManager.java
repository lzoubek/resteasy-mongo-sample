package com.github.resteasy.mongo.sample;

import java.net.UnknownHostException;

import javax.enterprise.context.ApplicationScoped;

import com.github.resteasy.mongo.sample.model.Comment;
import com.github.resteasy.mongo.sample.model.Link;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@ApplicationScoped
public class EntityManager {
	private Mongo mongo;
	private Datastore ds;
	private Morphia morphia;

	public EntityManager() {

		String host = System.getenv("OPENSHIFT_NOSQL_DB_HOST");
		String port = System.getenv("OPENSHIFT_NOSQL_DB_PORT");
		String username = System.getenv("OPENSHIFT_NOSQL_DB_USERNAME");
		String password = System.getenv("OPENSHIFT_NOSQL_DB_PASSWORD");
		String db = System.getenv("OPENSHIFT_APP_NAME");
		
		try {			
			if (host==null) {
				// we are not running on openshift
				mongo = new Mongo();
			}
			else {
				mongo = new Mongo(host, Integer.parseInt(port));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

		try {
			morphia = new Morphia();
			morphia.map(Comment.class);
			morphia.map(Link.class);
			if (host==null) {
				ds = morphia.createDatastore(mongo,"resteasy-mongo-example");
			}
			else {
				ds = morphia.createDatastore(mongo, db, username, password.toCharArray());
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		
		System.out.println("Mongo Datastore initialized");
	}

	public Datastore getDs() {
		return ds;
	}

}
