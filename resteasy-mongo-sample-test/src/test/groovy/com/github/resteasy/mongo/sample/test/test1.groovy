package com.github.resteasy.mongo.sample.test;

import java.util.logging.Logger;

import javax.swing.text.html.HTMLEditorKit.HTMLFactory.BodyBlockView;

import org.testng.Assert;
import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON
/**
 * @author Libor Zoubek
 */

class Test1 extends RestClientTest {


	@BeforeClass
	void deleteAll() {
		client.delete(path:"links")
	}		
	
	@Test 
	void putLink() {
		def response = client.put( path : "links", body : [description:"This is a great movie",url:"http://www.csfd.cz/film/8852-pulp-fiction-historky-z-podsveti/"] , requestContentType: JSON)
		println response.data
	}

	
	
	@Test(dependsOnMethods=["putLink"],priority=1)
	void postComment() {
		def id = client.put( path : "links", body : [description:"Another great one",url:"http://www.csfd.cz/film/241997-avengers/"] , requestContentType: JSON).data.message
		client.post(path : "links/$id", body : [nick:"master",message:"Yes, this is awesome!"], requestContentType: JSON) { resp, json ->
			Assert.assertEquals resp.status , 201 , "Response status"
			println json
		}
	}
	
	@Test(dependsOnMethods=["putLink"],priority=2)
	void getLinks() {
		client.get(path : "links") { resp, json ->
			Assert.assertEquals resp.status , 200 , "Response status"
			println json
		}
	}
	
	@Test(dependsOnMethods=["getLinks"])
	void deleteSome() {		
		def id = null;
		client.get(path : "links") { resp, json ->
			Assert.assertEquals resp.status , 200 , "Response status"
			id = json[0].id 
		}
		client.delete(path:"links/$id")
		client.handler."404" = { resp ->
			Assert.assertEquals resp.status , 404, "Response status"
		}
		client.get(path : "links/$id")
		client.handler.remove(404)
		println "Link deleted"	
	}

}
