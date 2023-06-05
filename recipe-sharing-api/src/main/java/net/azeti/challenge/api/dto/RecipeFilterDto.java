package net.azeti.challenge.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record RecipeFilterDto(
        String titleLike,
        String usernameLike
) {
}
