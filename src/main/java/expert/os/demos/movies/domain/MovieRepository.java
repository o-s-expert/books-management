package expert.os.demos.movies.domain;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

@Repository
public interface MovieRepository extends BasicRepository<Movie, String> {

}
