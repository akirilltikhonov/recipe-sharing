package net.azeti.challenge.application.infra.jpa.repository;

import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long> {

    List<RecipeEntity> findByUsername(String username);
}
