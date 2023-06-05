package net.azeti.challenge.application.infra.jpa.repository;

import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import net.azeti.challenge.application.infra.jpa.repository.custom.RecipeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long>, RecipeRepositoryCustom {

    List<RecipeEntity> findByUsername(String username);
}
