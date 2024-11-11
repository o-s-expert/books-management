package expert.os.demos.movies.application;

import expert.os.demos.movies.api.MovieRequest;
import expert.os.demos.movies.api.MovieResponse;
import expert.os.demos.movies.domain.Genre;
import expert.os.demos.movies.domain.Movie;
import org.assertj.core.api.SoftAssertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Year;
import java.util.Arrays;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = Mappers.getMapper(MovieMapper.class);
    }


    @Test
    void shouldMapMovieToMovieResponse() {
        // Given
        Movie movie = Instancio.of(Movie.class)
                .set(field("id"), UUID.randomUUID().toString())
                .set(field("title"), "Inception")
                .set(field("genre"), Genre.SCIENCE_FICTION)
                .set(field("releaseYear"), 2010)
                .set(field("director"), "Christopher Nolan")
                .set(field("actors"), Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt"))
                .create();

        // When
        MovieResponse movieResponse = movieMapper.toResponse(movie);

        // Then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(movieResponse).isNotNull();
            soft.assertThat(movieResponse.getId()).isEqualTo(movie.getId());
            soft.assertThat(movieResponse.getTitle()).isEqualTo(movie.getTitle());
            soft.assertThat(movieResponse.getGenre()).isEqualTo(movie.getGenre().toString());
            soft.assertThat(movieResponse.getReleaseYear()).isEqualTo(movie.getReleaseYear());
            soft.assertThat(movieResponse.getDirector()).isEqualTo(movie.getDirector());
            soft.assertThat(movieResponse.getActors()).isEqualTo(movie.getActors());
        });
    }

    @Test
    void shouldMapMovieRequestToMovieWithNewUUID() {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("Inception");
        movieRequest.setGenre("SCIENCE_FICTION");
        movieRequest.setReleaseYear(2010);
        movieRequest.setDirector("Christopher Nolan");
        movieRequest.setActors(Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt"));

        Movie movie = movieMapper.toEntity(movieRequest);

        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(movie).isNotNull();
            soft.assertThat(movie.getId()).isNotNull().isNotEmpty();
            soft.assertThat(movie.getTitle()).isEqualTo(movieRequest.getTitle());
            soft.assertThat(movie.getGenre()).isEqualTo(Genre.valueOf(movieRequest.getGenre()));
            soft.assertThat(movie.getReleaseYear()).isEqualTo(movieRequest.getReleaseYear());
            soft.assertThat(movie.getDirector()).isEqualTo(movieRequest.getDirector());
            soft.assertThat(movie.getActors()).isEqualTo(movieRequest.getActors());
        });
    }
}
