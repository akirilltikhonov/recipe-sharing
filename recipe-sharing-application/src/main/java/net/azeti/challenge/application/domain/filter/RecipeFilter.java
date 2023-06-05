package net.azeti.challenge.application.domain.filter;

import lombok.Builder;

@Builder(toBuilder = true)
public record RecipeFilter(
        String titleLike,
        String usernameLike
) {
}
