package expert.os.demos.movies.domain;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.Year;
import java.util.List;

@Entity
public class Movie {

    @Id
    private String id;

    @Column
    private String title;

    @Column
    private Genre genre;

    @Column
    private Year releaseYear;

    @Column
    private String director;

    @Column
    private List<String> actors;


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

    public void update(Movie movie) {
        this.title = movie.title;
        this.genre = movie.genre;
        this.releaseYear = movie.releaseYear;
        this.director = movie.director;
        this.actors = movie.actors;
    }
}
