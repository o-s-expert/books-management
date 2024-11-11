package expert.os.demos.movies;

import jakarta.json.bind.annotation.JsonbVisibility;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@JsonbVisibility(FieldAccessStrategy.class)
@Schema(description = "Movie request payload for creating or updating a movie")
public class MovieRequest {


    @Schema(description = "Title of the movie", example = "Inception")
    private String title;

    @Schema(description = "Genre of the movie", example = "SCIENCE_FICTION")
    private String genre;

    @Schema(description = "Year the movie was released", example = "2010")
    private int releaseYear;

    @Schema(description = "Director of the movie", example = "Christopher Nolan")
    private String director;

    @Schema(description = "List of actors in the movie", example = "[\"Leonardo DiCaprio\", \"Joseph Gordon-Levitt\"]")
    private List<String> actors;

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }
}
