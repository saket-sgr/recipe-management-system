package recipes.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import recipes.repository.AppUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record RecipeDTO(
        @NotBlank String name,
        @NotBlank String category,
        LocalDateTime date,
        @NotBlank String description,
        @NotNull @Size(min = 1) List<String> ingredients,
        @NotNull @Size(min = 1) List<String> directions,
        @JsonIgnore AppUser author
) {
    @Override
    public String toString() {
        return "RecipeDTO{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", author=" + author +
                '}';
    }
}