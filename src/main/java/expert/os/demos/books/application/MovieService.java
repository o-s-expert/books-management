package expert.os.demos.books.application;

import expert.os.demos.books.api.BookRequest;
import expert.os.demos.books.api.BookResponse;
import expert.os.demos.books.domain.Book;
import expert.os.demos.books.domain.BookRepository;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {

    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());
    public static final Order<Book> ORDER = Order.by(Sort.asc("title"));
    private final BookRepository bookRepository;

    private final MovieMapper movieMapper;

    @Inject
    public MovieService(@Database(DatabaseType.DOCUMENT) BookRepository bookRepository, MovieMapper movieMapper) {
        this.bookRepository = bookRepository;
        this.movieMapper = movieMapper;
    }

    public MovieService() {
        this(null, null);
    }

    public List<BookResponse> getAllMovies(int page, int size) {
        LOGGER.log(Level.INFO, "Fetching all movies with page: {0} and size: {1}", new Object[]{page, size});
        PageRequest pageable = PageRequest.ofPage(page).size(size);

        Page<Book> moviePage = bookRepository.findAll(pageable, ORDER);
        List<BookResponse> movies = moviePage.stream()
                .map(movieMapper::toResponse)
                .collect(Collectors.toList());
        LOGGER.log(Level.INFO, "Retrieved {0} movies", movies.size());
        return movies;
    }

    public Optional<BookResponse> getMovieById(String id) {
        LOGGER.log(Level.INFO, "Fetching movie with ID: {0}", id);
        Optional<BookResponse> movieResponse = bookRepository.findById(id)
                .map(movieMapper::toResponse);
        if (movieResponse.isPresent()) {
            LOGGER.log(Level.INFO, "Movie found with ID: {0}", id);
        } else {
            LOGGER.log(Level.WARNING, "Movie not found with ID: {0}", id);
        }
        return movieResponse;
    }

    public BookResponse createMovie(BookRequest bookRequest) {
        LOGGER.log(Level.INFO, "Creating new movie with title: {0}", bookRequest.getTitle());
        Book book = movieMapper.toEntity(bookRequest, UUID.randomUUID().toString());
        Book savedBook = bookRepository.save(book);
        LOGGER.log(Level.INFO, "Movie created with ID: {0}", savedBook.getId());
        return movieMapper.toResponse(savedBook);
    }

    public Optional<BookResponse> updateMovie(String id, BookRequest bookRequest) {
        LOGGER.log(Level.INFO, "Updating movie with ID: {0}", id);
        return bookRepository.findById(id)
                .map(existingMovie -> {
                    Book updatedBook = movieMapper.toEntity(bookRequest);
                    existingMovie.update(updatedBook);
                    Book savedBook = bookRepository.save(updatedBook);
                    LOGGER.log(Level.INFO, "Movie updated with ID: {0}", id);
                    return movieMapper.toResponse(savedBook);
                });
    }

    public void deleteMovie(String id) {
        LOGGER.log(Level.INFO, "Deleting movie with ID: {0}", id);
        bookRepository.deleteById(id);
    }
}
