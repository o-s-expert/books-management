package expert.os.demos.movies.api;

import expert.os.demos.movies.infrastructure.FieldAccessStrategy;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
