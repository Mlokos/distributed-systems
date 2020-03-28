package com.mlokos.online_nutritionist.presenter;

import java.util.List;

import com.mlokos.online_nutritionist.model.Meal;
import com.mlokos.online_nutritionist.view.HtmlBackbone;
import com.mlokos.online_nutritionist.view.HtmlRecipeRow;
import com.mlokos.online_nutritionist.view.HtmlRecipeRowNoSave;
import com.mlokos.online_nutritionist.view.HtmlSearchRow;

public class MealsHtmlConnector {	
	public String generateSearch() {
		String htmlPage = HtmlBackbone.getTemplate();
		htmlPage = htmlPage.replace("$search_row", HtmlSearchRow.getTemplate());
		htmlPage = htmlPage.replace("$recipe_rows", "");
		
		return htmlPage;
	}
	
	public String generateRecepies(List<Meal> savedMeals) {
		String recipeRows = createListOfRecipesHtmlBlock(savedMeals);
		
		String htmlPage = HtmlBackbone.getTemplate();
		htmlPage = htmlPage.replace("$search_row", "");
		htmlPage = htmlPage.replace("$recipe_rows", recipeRows);
		
		return htmlPage;
	}
	
	public String generateRecepiesNoSaveButton(List<Meal> savedMeals) {
		String recipeRows = createListOfRecipesHtmlBlockNoSaveButton(savedMeals);
		
		String htmlPage = HtmlBackbone.getTemplate();
		htmlPage = htmlPage.replace("$search_row", "");
		htmlPage = htmlPage.replace("$recipe_rows", recipeRows);
		
		return htmlPage;
	}
	
	public String generateSearchRecepies(List<Meal> savedMeals) {
		String recipeRows = createListOfRecipesHtmlBlock(savedMeals);
		
		String htmlPage = HtmlBackbone.getTemplate();
		htmlPage = htmlPage.replace("$search_row", HtmlSearchRow.getTemplate());
		htmlPage = htmlPage.replace("$recipe_rows", recipeRows);
		
		return htmlPage;
	}

	private String createListOfRecipesHtmlBlock(List<Meal> savedMeals) {
		String recipeRows = "";
		String recipeTemplate;
		int id = 0;
		for(Meal meal : savedMeals) {
			recipeTemplate = HtmlRecipeRow.getTemplate();
			
			recipeTemplate = recipeTemplate.replace("$name", meal.getName());
			recipeTemplate = recipeTemplate.replace("$imageURL", meal.getImageURL());
			recipeTemplate = recipeTemplate.replace("$linkURL", meal.getLinkURL());
			recipeTemplate = recipeTemplate.replace("$ingredients", meal.getIngredients());
			
			recipeTemplate = recipeTemplate.replace("$row_id", new Integer(id++).toString());
			
			recipeRows += recipeTemplate;
		}
		
		return recipeRows;
	}
	
	private String createListOfRecipesHtmlBlockNoSaveButton(List<Meal> savedMeals) {
		String recipeRows = "";
		String recipeTemplate;
		int id = 0;
		for(Meal meal : savedMeals) {
			recipeTemplate = HtmlRecipeRowNoSave.getTemplate();
			
			recipeTemplate = recipeTemplate.replace("$name", meal.getName());
			recipeTemplate = recipeTemplate.replace("$imageURL", meal.getImageURL());
			recipeTemplate = recipeTemplate.replace("$linkURL", meal.getLinkURL());
			recipeTemplate = recipeTemplate.replace("$ingredients", meal.getIngredients());
			
			recipeTemplate = recipeTemplate.replace("$row_id", new Integer(id++).toString());
			
			recipeRows += recipeTemplate;
		}
		
		return recipeRows;
	}
}
