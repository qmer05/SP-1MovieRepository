package app.entities;

import app.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@NoArgsConstructor
public class Genre {
    @Id
    private Integer id;
    private String name;

    @ManyToMany (mappedBy = "genres")
    private List<Movie> movies = new ArrayList<>();

    public Genre(GenreDTO genreDTO) {
        this.id = genreDTO.getId();
        this.name = genreDTO.getName();
    }
}
