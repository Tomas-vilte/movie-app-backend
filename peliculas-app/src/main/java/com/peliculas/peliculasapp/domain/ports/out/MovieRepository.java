package com.peliculas.peliculasapp.domain.ports.out;
import com.peliculas.peliculasapp.domain.models.Movie;
import java.util.Optional;

public interface MovieRepository {
    Movie saveMovieInfo(Movie movie);
    Optional<Movie> getMovieId(long movieId);
}
