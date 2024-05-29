package recipes.service;

import org.springframework.stereotype.Service;
import recipes.repository.Recipe;

import java.time.LocalDateTime;

@Service
public class Mapper {
    Recipe toEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        return getRecipe(recipeDTO, recipe);
    }
    RecipeDTO toDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDate(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections(),
                recipe.getAuthor()
        );
    }

    Recipe toEntity(RecipeDTO recipeDTO, Recipe recipe) {
        return getRecipe(recipeDTO, recipe);
    }

    private Recipe getRecipe(RecipeDTO recipeDTO, Recipe recipe) {
        recipe.setName(recipeDTO.name());
        recipe.setCategory(recipeDTO.category());
        recipe.setDate(LocalDateTime.now());
        recipe.setDescription(recipeDTO.description());
        recipe.setIngredients(recipeDTO.ingredients());
        recipe.setDirections(recipeDTO.directions());
        return recipe;
    }

}
