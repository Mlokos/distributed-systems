/**
 * Grtitude to: https://www.youtube.com/watch?v=BZi44GOD8kY
 */

package com.mlokos.online_nutritionist.model;

import java.util.ArrayList;
import java.util.List;

public class MealListContainers {
	private static List<Meal> savedMeals = new ArrayList<>();
	private static List<Meal> searchedMeals = new ArrayList<>();
	
	public List<Meal> getSavedMeals() {
		return savedMeals;
	}
	
	public void saveMeal(Meal ml) {
		savedMeals.add(ml);
	}
	
	public List<Meal> getSearchedMeals() {
		return searchedMeals;
	}
	
	public Meal getSearchedMeals(int id) {
		return searchedMeals.get(id);
	}
	
	public void setSearchedMeals(List<Meal> searchedMeals) {
		MealListContainers.searchedMeals = searchedMeals;
	}
}
