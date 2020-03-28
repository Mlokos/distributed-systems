package com.mlokos.online_nutritionist.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mlokos.online_nutritionist.model.Meal;
import com.mlokos.online_nutritionist.model.MealListContainers;

public class MealsModelConnector {
	private MealListContainers model = new MealListContainers();
	
	private JSONObject convertResponseToJSON(Response resp) {
		return new JSONObject(resp.readEntity(String.class));
	}
	
	private List<Meal> convertJSONToMealList(JSONArray recipes) {
		List<Meal> searchedMeals = new ArrayList<>();
		
		for(int i = 0; i < recipes.length(); i++) {
			Meal meal = new Meal();
			meal.setName(recipes.getJSONObject(i).getString("title"));
			meal.setImageURL(recipes.getJSONObject(i).getString("thumbnail"));
			meal.setLinkURL(recipes.getJSONObject(i).getString("href"));
			meal.setIngredients(recipes.getJSONObject(i).getString("ingredients"));
			
			searchedMeals.add(meal);
		}
		
		return searchedMeals;
	}
	
	public void updateModelSetSearchedMeals(Response resp) {
		JSONObject recipePuppyJson = convertResponseToJSON(resp);
		JSONArray recepies = recipePuppyJson.getJSONArray("results");
		
		model.setSearchedMeals(convertJSONToMealList(recepies));
	}
	
	public void saveMealToModel(int id) {
		model.saveMeal(model.getSearchedMeals(id));
	}
	
	public List<Meal> getSavedMeals() {
		return model.getSavedMeals();
	}
	
	public List<Meal> getSearchedMeals() {
		return model.getSearchedMeals();
	}
}
