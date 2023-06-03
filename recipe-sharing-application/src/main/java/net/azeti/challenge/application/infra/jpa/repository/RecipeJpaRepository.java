package net.azeti.challenge.application.infra.jpa.repository;

import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long> {
}
