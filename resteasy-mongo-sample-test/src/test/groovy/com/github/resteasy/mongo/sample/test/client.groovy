package com.github.resteasy.mongo.sample.test;

import org.testng.Assert;
import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.JSON


/**
 * @author Libor Zoubek
 */
class RestClientTest {

	protected client

	@BeforeClass
	def baseSetUp() {
		
		def serverUrl = 'http://localhost:8080/resteasy-mongo-sample/'
		if (System.properties['url'])  {
			serverUrl = System.properties['url']
		}
		client = new RESTClient( serverUrl + 'api/' )
		try {
			client.get( path : '' )
		}
		catch (org.apache.http.conn.HttpHostConnectException ex) {
			Assert.fail(ex.message)
		}
		catch (Exception ex) {
			// that is OK, should fail on Unauthorized
		}
	}

}

