package app.dtos;

import app.entities.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {
    private Integer id;
    private String name;

    GenreDTO (Genre genre){
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
