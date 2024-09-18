package app.dtos;

import app.entities.Actor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {
    private Integer id;
    private String name;
    private String character;

    public ActorDTO(Actor actor){
        this.id = actor.getId();
        this.name = actor.getName();
        this.character = actor.getCharacter();
    }
}