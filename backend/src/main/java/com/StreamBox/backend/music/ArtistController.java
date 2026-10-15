package com.StreamBox.backend.music;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;

    @PostMapping
    public ResponseEntity<ArtistResponse> create(@Valid @RequestBody CreateArtistRequest request) {
        if (artistRepository.existsByName(request.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Artist artist = new Artist();
        artist.setName(request.name());
        artist.setBio(request.bio());
        artist.setCountry(request.country());

        Artist saved = artistRepository.save(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(ArtistResponse.from(saved));
    }

    @GetMapping
    public List<ArtistResponse> findAll() {
        return artistRepository.findAll().stream()
                .map(ArtistResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable Long id) {
        return artistRepository.findById(id)
                .map(artist -> ResponseEntity.ok(ArtistResponse.from(artist)))
                .orElse(ResponseEntity.notFound().build());
    }
}