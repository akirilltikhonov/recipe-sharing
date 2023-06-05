package net.azeti.challenge.application.infra.jpa.repository.custom;

import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;

import java.util.List;

public interface RecipeRepositoryCustom {

    List<RecipeEntity> findByFilter(RecipeFilter filter);
}
