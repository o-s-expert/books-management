package expert.os.demos.movies.domain;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.List;

@Entity
public class Movie {

    @Id
    private String id = java.util.UUID.randomUUID().toString();

    @Column
    private String title;

    @Column
    private Genre genre;

    @Column
    private int releaseYear;

    @Column
    private String director;

    @Column
    private List<String> actors;

    public Movie() {
    }

    private Movie(Builder builder) {
        this.id = builder.id != null ? builder.id : java.util.UUID.randomUUID().toString();
        this.title = builder.title;
        this.genre = builder.genre;
        this.releaseYear = builder.releaseYear;
        this.director = builder.director;
        this.actors = builder.actors;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
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

    public void update(Movie movie) {
        this.title = movie.title;
        this.genre = movie.genre;
        this.releaseYear = movie.releaseYear;
        this.director = movie.director;
        this.actors = movie.actors;
    }

    public static class Builder {
        private String id;
        private String title;
        private Genre genre;
        private int releaseYear;
        private String director;
        private List<String> actors;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder genre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder director(String director) {
            this.director = director;
            return this;
        }

        public Builder actors(List<String> actors) {
            this.actors = actors;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    // Factory method to initiate the builder
    public static Builder builder() {
        return new Builder();
    }
}
