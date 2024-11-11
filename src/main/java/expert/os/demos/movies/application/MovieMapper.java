package expert.os.demos.movies.application;

import expert.os.demos.movies.api.MovieRequest;
import expert.os.demos.movies.api.MovieResponse;
import expert.os.demos.movies.domain.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface MovieMapper {

    MovieResponse toResponse(Movie movie);

    Movie toEntity(MovieRequest movieRequest);

    @Mapping(target = "id", source = "id")
    Movie toEntity(MovieRequest movieRequest, String id);

}
