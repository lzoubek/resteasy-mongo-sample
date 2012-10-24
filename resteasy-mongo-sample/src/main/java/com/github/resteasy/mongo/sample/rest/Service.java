package com.github.resteasy.mongo.sample.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import com.github.resteasy.mongo.sample.EntityManager;
import com.github.resteasy.mongo.sample.model.Comment;
import com.github.resteasy.mongo.sample.model.Link;
import com.github.resteasy.mongo.sample.model.StatusResponse;

@Path("/links")
@RequestScoped
public class Service {
	@Inject
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Link> listAll() {
		return  em.getDs().createQuery(Link.class).asList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response linkById(@PathParam("id") ObjectId id) {
		Link s = em.getDs().find(Link.class).field("id").equal(id).get();
		if (s!=null) {
			return Response.ok(s).status(200).build();
		}
		return Response.ok().status(404).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLink(Link s) {
		em.getDs().save(s);
		return Response.ok(StatusResponse.success(s.getId())).status(201).build();
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response deleteLink(@PathParam("id") ObjectId id) {
		Link s = em.getDs().find(Link.class).field("id").equal(id).get();
		if (s == null) {
			return Response.ok(StatusResponse.error("link id is invalid")).status(404).build();
		} 
		em.getDs().delete(s);
		return Response.ok(StatusResponse.success()).status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAll() {
		em.getDs().delete(em.getDs().createQuery(Link.class));
		return Response.ok(StatusResponse.success()).status(200).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response addComment(@PathParam("id") ObjectId id, Comment e) {
		Link s = em.getDs().find(Link.class).field("id").equal(id).get();
		if (s==null) {
			return Response.ok(StatusResponse.error("link id is invalid")).status(404).build();
		}
		s.getComments().add(e);
		em.getDs().save(s);
		return Response.ok(StatusResponse.success()).status(201).build();
	}
}
