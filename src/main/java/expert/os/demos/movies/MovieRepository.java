package expert.os.demos.movies;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends BasicRepository<Movie, String> {
    List<Movie> findByTitle(String title);
    List<Movie> findByGenre(Genre genre);
}
