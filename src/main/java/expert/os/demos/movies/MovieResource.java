package expert.os.demos.movies;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@RequestScoped
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Movies", description = "Operations related to movies")
public class MovieResource {


    private final MovieService movieService;

    @Inject
    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    public MovieResource() {
        this(null);
    }

    @GET
    @Operation(summary = "Retrieve all movies", description = "Returns a paginated list of all movies.")
    @APIResponse(responseCode = "200", description = "List of movies", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MovieResponse.class)))
    public Response getAllMovies(
            @Parameter(description = "Page number for pagination", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @QueryParam("size") @DefaultValue("10") int size) {
        List<MovieResponse> movies = movieService.getAllMovies(page, size);
        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a movie by ID", description = "Returns a movie for the specified ID.")
    @APIResponse(responseCode = "200", description = "Movie details", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MovieResponse.class)))
    @APIResponse(responseCode = "404", description = "Movie not found")
    public Response getMovieById(
            @Parameter(description = "ID of the movie to retrieve", required = true)
            @PathParam("id") String id) {
        Optional<MovieResponse> movieResponse = movieService.getMovieById(id);
        return movieResponse.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Operation(summary = "Create a new movie", description = "Creates a new movie in the system.")
    @APIResponse(responseCode = "201", description = "Movie created", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MovieResponse.class)))
    public Response createMovie(
            @Parameter(description = "Movie data to be created", required = true)
            MovieRequest movieRequest) {
        MovieResponse movieResponse = movieService.createMovie(movieRequest);
        return Response.status(Response.Status.CREATED).entity(movieResponse).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a movie", description = "Updates an existing movie by ID.")
    @APIResponse(responseCode = "200", description = "Movie updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponse.class)))
    @APIResponse(responseCode = "404", description = "Movie not found")
    public Response updateMovie(
            @Parameter(description = "ID of the movie to update", required = true)
            @PathParam("id") String id,
            @Parameter(description = "Updated movie data", required = true)
            MovieRequest movieRequest) {
        Optional<MovieResponse> updatedMovie = movieService.updateMovie(id, movieRequest);
        return updatedMovie.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a movie", description = "Deletes a movie by ID.")
    @APIResponse(responseCode = "204", description = "Movie deleted")
    @APIResponse(responseCode = "404", description = "Movie not found")
    public Response deleteMovie(
            @Parameter(description = "ID of the movie to delete", required = true)
            @PathParam("id") String id) {
        movieService.deleteMovie(id);
        return Response.noContent().build();
    }
}
