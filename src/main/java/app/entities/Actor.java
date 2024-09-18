package app.entities;

import app.dtos.ActorDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actors")
@Getter
@NoArgsConstructor
public class Actor {
    @Id
    private Integer id;
    private String name;
    private String character;

    @ManyToMany (mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();

    public Actor(ActorDTO actorDTO) {
        this.id = actorDTO.getId();
        this.name = actorDTO.getName();
        this.character = actorDTO.getCharacter();
    }
}
