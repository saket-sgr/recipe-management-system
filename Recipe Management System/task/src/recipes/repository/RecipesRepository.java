package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {

    public List<Recipe> findRecipesByCategoryIgnoreCase(String category);
    public List<Recipe> findRecipesByNameContainingIgnoreCase(String name);

}
