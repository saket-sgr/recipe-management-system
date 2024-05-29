package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.repository.AppUser;
import recipes.repository.Recipe;
import recipes.repository.RecipesRepository;
import java.util.List;

@Service
public class RecipesService {
    private final RecipesRepository repository;
    private final Mapper mapper;

    @Autowired
    public RecipesService(RecipesRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Long save(RecipeDTO recipeDTO, AppUser user) {
        Recipe recipe = mapper.toEntity(recipeDTO);
        recipe.setAuthor(user);
        Recipe savedRecipe = repository.save(recipe);
        return savedRecipe.getId();
    }

    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = repository.findById(id).orElseThrow();
        return mapper.toDTO(recipe);
    }

    public void deleteRecipeById(Long id) {
        repository.deleteById(id);
    }

    public void updateRecipeById(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = repository.findById(id).orElseThrow();
        repository.save(mapper.toEntity(recipeDTO, recipe));
    }

    public List<RecipeDTO> searchByCategory(String category) {
        return repository
                .findRecipesByCategoryIgnoreCase(category)
                .stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .map(mapper::toDTO)
                .toList();
    }

    public List<RecipeDTO> searchByName(String name) {
        return repository
                .findRecipesByNameContainingIgnoreCase(name)
                .stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .map(mapper::toDTO)
                .toList();
    }
}
