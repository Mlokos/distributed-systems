package com.mlokos.online_nutritionist.presenter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExternalResource {
	private Client client;
	private WebTarget target;
	
	public ExternalResource() {
		client = ClientBuilder.newClient();
		target = client.target("http://www.recipepuppy.com/api");
	}

	public Response getJSON(String meal_name, String ingredients) {
		WebTarget tergetRequest = target
				.queryParam("q", meal_name)
				.queryParam("i", ingredients);
		
		Invocation.Builder invBuilder =
				tergetRequest.request(MediaType.APPLICATION_JSON);
		
		return invBuilder.get();
	}
}
