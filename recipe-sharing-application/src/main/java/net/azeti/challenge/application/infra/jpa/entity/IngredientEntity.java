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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@TypeDef(name = "pg_enum", typeClass = PostgreSQLEnumType.class)
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ingredientId;

    @Column(name = "value")
    private Float value;

    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

    @Column(name = "type")
    private String type;
}
