package com.mlokos.online_nutritionist.presenter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("meals")
public class MealsResource {
	ExternalResource recipePuppyResource = new ExternalResource();
	MealsHtmlConnector htmlPage = new MealsHtmlConnector();
	MealsModelConnector modelConnector = new MealsModelConnector();
	
	@GET
	@Path("search")
	@Produces(MediaType.TEXT_HTML)
	public String viewSearch() {
		return htmlPage.generateSearch();
	}
	
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String applySearch(
			@FormParam("meal") String meal,
			@FormParam("ingredients") String ingredients) {
		Response resp = recipePuppyResource.getJSON(meal, ingredients);
		modelConnector.updateModelSetSearchedMeals(resp);
		
		return htmlPage.generateSearchRecepies(modelConnector.getSearchedMeals());
	}
	
	@POST
	@Path("search/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void saveMeal(
			@PathParam("id") String id,
			@Context HttpServletResponse servletResponse) {
		modelConnector.saveMealToModel(Integer.parseInt(id));
	}
	
	@GET
	@Path("recepies")
	@Produces(MediaType.TEXT_HTML)
	public String viewRecepies() {
		return htmlPage.generateRecepiesNoSaveButton(modelConnector.getSavedMeals());
	}
}
