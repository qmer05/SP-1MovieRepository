package app.dtos;

import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Integer id;
    @JsonProperty("original_title")
    private String title;
    @JsonProperty("original_language")
    private String language;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private Double popularity;
    @JsonProperty("vote_average")
    private Double rating;
    @JsonProperty("genres")
    private List<GenreDTO> genresDTOs;
    private List<ActorDTO> actorDTOs;
    private DirectorDTO directorDTO;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.language = movie.getLanguage();
        this.releaseDate = movie.getReleaseDate();
        this.popularity = movie.getPopularity();
        this.rating = movie.getRating();
        this.genresDTOs = movie.getGenres().stream()
                .map(genre -> new GenreDTO(genre))
                .collect(Collectors.toList());
        this.actorDTOs = movie.getActors().stream()
                .map(actor -> new ActorDTO(actor))
                .collect(Collectors.toList());
        this.directorDTO = movie.getDirector() != null ? new DirectorDTO(movie.getDirector()) : null;
    }

}

