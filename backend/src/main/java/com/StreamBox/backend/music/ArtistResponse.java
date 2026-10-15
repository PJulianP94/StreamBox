package com.StreamBox.backend.music;

public record ArtistResponse(Long id, String name, String bio, String country) {

    public static ArtistResponse from(Artist artist) {
        return new ArtistResponse(
                artist.getId(),
                artist.getName(),
                artist.getBio(),
                artist.getCountry()
        );
    }
}