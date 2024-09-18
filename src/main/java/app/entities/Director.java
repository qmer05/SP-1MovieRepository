package app.entities;

import app.dtos.DirectorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "directors")
@NoArgsConstructor
@Getter
public class Director {
    @Id
    private Integer id;
    private String name;

    @OneToMany (mappedBy = "director")
    private List<Movie> movies;

    public Director(DirectorDTO directorDTO) {
        this.id = directorDTO.getId();
        this.name = directorDTO.getName();
    }
}
