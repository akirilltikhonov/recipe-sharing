package net.azeti.challenge.application.infra.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class RecipeEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "title")
    private String title;

    @Column(name = "username")
    private String username;

    @Column(name = "description")
    private String description;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "servings")
    private Integer servings;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "recipe",
            orphanRemoval = true)
    private List<IngredientEntity> ingredients = new ArrayList<>();

    public void addIngredient(IngredientEntity ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void removeIngredient(IngredientEntity ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null);
    }
}
