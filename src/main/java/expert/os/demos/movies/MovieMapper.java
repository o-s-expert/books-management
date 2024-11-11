package expert.os.demos.movies;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MovieMapper {

    MovieResponse toResponse(Movie movie);


}
