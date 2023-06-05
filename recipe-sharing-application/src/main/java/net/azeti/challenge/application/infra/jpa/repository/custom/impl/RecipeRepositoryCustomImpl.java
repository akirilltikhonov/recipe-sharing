package net.azeti.challenge.application.infra.jpa.repository.custom.impl;

import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import net.azeti.challenge.application.infra.jpa.repository.custom.RecipeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RecipeEntity> findByFilter(RecipeFilter filter) {
        final String sql = "SELECT r FROM RecipeEntity r " +
                "WHERE 1 = 1 " +
                (filter.titleLike() != null
                        ? "AND lower(r.title) LIKE lower(:titleLike) " : "") +
                (filter.usernameLike() != null
                        ? "AND lower(r.username) LIKE lower(:usernameLike) " : "");

        final var query = em.createQuery(sql, RecipeEntity.class);

        if (filter.titleLike() != null) {
            query.setParameter("titleLike", "%" + filter.titleLike() + "%");
        }
        if (filter.usernameLike() != null) {
            query.setParameter("usernameLike", "%" + filter.usernameLike() + "%");
        }
        return query.getResultList();
    }
}
