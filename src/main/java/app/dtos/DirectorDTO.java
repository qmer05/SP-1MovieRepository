package app.dtos;

import app.entities.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {
    private Integer id;
    private String name;
    private String job;

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
    }

    @Override
    public String toString() {
        return "DirectorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}