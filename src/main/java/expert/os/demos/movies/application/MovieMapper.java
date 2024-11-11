package expert.os.demos.movies.application;

import expert.os.demos.movies.api.MovieRequest;
import expert.os.demos.movies.api.MovieResponse;
import expert.os.demos.movies.domain.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MovieMapper {

    MovieResponse toResponse(Movie movie);

    Movie toEntity(MovieRequest movieRequest);

}
