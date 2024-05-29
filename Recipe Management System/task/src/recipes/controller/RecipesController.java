package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import recipes.repository.AppUser;
import recipes.service.AppUserService;
import recipes.service.RecipeDTO;
import recipes.service.RecipesService;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class RecipesController {
    private final RecipesService recipesService;
    private final AppUserService userService;

    @Autowired
    public RecipesController(RecipesService recipesService, AppUserService userService) {
        this.recipesService = recipesService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AppUser user) {
        try {
            userService.loadUserByUsername(user.getUsername());
            return ResponseEntity.badRequest().build();
        } catch (UsernameNotFoundException e) {
            userService.registerUser(user);
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipesService.getRecipeById(id));
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Map<String, Long>> setRecipe(@Valid @RequestBody RecipeDTO recipeDTO, @AuthenticationPrincipal AppUser user) {
        Long id = recipesService.save(recipeDTO, user);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        if (recipesService.getRecipeById(id).author().equals(user)) {
            recipesService.deleteRecipeById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO, @AuthenticationPrincipal AppUser user) {
        if (recipesService.getRecipeById(id).author().equals(user)) {
            recipesService.updateRecipeById(id, recipeDTO);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/recipe/search/")
    public ResponseEntity<List<RecipeDTO>> searchRecipe(@RequestParam Map<String, String> params) {
        if (params.size() != 1) {
            return ResponseEntity.badRequest().build();
        }

        if (params.containsKey("category")) {
            return ResponseEntity.ok(
                    recipesService.searchByCategory(params.get("category"))
            );
        } else if (params.containsKey("name")) {
            return ResponseEntity.ok(
                    recipesService.searchByName(params.get("name"))
            );
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Void> exceptionHandlerForNotFound() {
        return ResponseEntity.notFound().build();
    }
}