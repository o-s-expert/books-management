package expert.os.demos.movies.application;

import expert.os.demos.movies.api.MovieRequest;
import expert.os.demos.movies.api.MovieResponse;
import expert.os.demos.movies.domain.Movie;
import expert.os.demos.movies.domain.MovieRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {

    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());
    public static final Order<Movie> ORDER = Order.by(Sort.asc("title"));
    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Inject
    public MovieService(@Database(DatabaseType.DOCUMENT) MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieService() {
        this(null, null);
    }

    public List<MovieResponse> getAllMovies(int page, int size) {
        LOGGER.log(Level.INFO, "Fetching all movies with page: {0} and size: {1}", new Object[]{page, size});
        PageRequest pageable = PageRequest.ofPage(page).size(size);

        Page<Movie> moviePage = movieRepository.findAll(pageable, ORDER);
        List<MovieResponse> movies = moviePage.stream()
                .map(movieMapper::toResponse)
                .collect(Collectors.toList());
        LOGGER.log(Level.INFO, "Retrieved {0} movies", movies.size());
        return movies;
    }

    public Optional<MovieResponse> getMovieById(String id) {
        LOGGER.log(Level.INFO, "Fetching movie with ID: {0}", id);
        Optional<MovieResponse> movieResponse = movieRepository.findById(id)
                .map(movieMapper::toResponse);
        if (movieResponse.isPresent()) {
            LOGGER.log(Level.INFO, "Movie found with ID: {0}", id);
        } else {
            LOGGER.log(Level.WARNING, "Movie not found with ID: {0}", id);
        }
        return movieResponse;
    }

    public MovieResponse createMovie(MovieRequest movieRequest) {
        LOGGER.log(Level.INFO, "Creating new movie with title: {0}", movieRequest.getTitle());
        Movie movie = movieMapper.toEntity(movieRequest);
        Movie savedMovie = movieRepository.save(movie);
        LOGGER.log(Level.INFO, "Movie created with ID: {0}", savedMovie.getId());
        return movieMapper.toResponse(savedMovie);
    }

    public Optional<MovieResponse> updateMovie(String id, MovieRequest movieRequest) {
        LOGGER.log(Level.INFO, "Updating movie with ID: {0}", id);
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    Movie updatedMovie = movieMapper.toEntity(movieRequest);
                    existingMovie.update(updatedMovie);
                    Movie savedMovie = movieRepository.save(updatedMovie);
                    LOGGER.log(Level.INFO, "Movie updated with ID: {0}", id);
                    return movieMapper.toResponse(savedMovie);
                });
    }

    public void deleteMovie(String id) {
        LOGGER.log(Level.INFO, "Deleting movie with ID: {0}", id);
        movieRepository.deleteById(id);
    }
}
