package recipes.repository;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;

    private LocalDateTime date;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;
    @ManyToOne
    @JoinColumn(name = "author_email", nullable = false)
    private AppUser author;
}
