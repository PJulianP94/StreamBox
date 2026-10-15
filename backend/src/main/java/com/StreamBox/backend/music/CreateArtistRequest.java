package com.StreamBox.backend.music;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArtistRequest(
        @NotBlank String name,
        @Size(max = 1000) String bio,
        String country
) {}