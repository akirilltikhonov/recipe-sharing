package net.azeti.challenge.application.infra.jpa.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import net.azeti.challenge.application.domain.enums.Unit;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@TypeDef(name = "pg_enum", typeClass = PostgreSQLEnumType.class)
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @Column(name = "value")
    private Float value;

    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

    @Column(name = "type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientEntity that = (IngredientEntity) o;
        return ingredientId != null && ingredientId.equals(that.getIngredientId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
