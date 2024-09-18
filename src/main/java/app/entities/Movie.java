package app.entities;

import app.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Setter
@Getter
@NoArgsConstructor
public class Movie {
    @Id
    private Integer id;
    private String title;
    private String language;
    private LocalDate releaseDate;
    private Double popularity;
    private Double rating;

    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany
    private List<Actor> actors = new ArrayList<>();

    @ManyToOne
    private Director director;

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.language = movieDTO.getLanguage();
        this.releaseDate = movieDTO.getReleaseDate();
        this.popularity = movieDTO.getPopularity();
        this.rating = movieDTO.getRating();
    }

    public void addGenre(Genre genre) {
        if (genre != null) {
            genres.add(genre);
        }
    }

    public void addActor(Actor actor) {
        if (actor != null) {
            actors.add(actor);
        }
    }

}
