package expert.os.demos.movies;

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
}
