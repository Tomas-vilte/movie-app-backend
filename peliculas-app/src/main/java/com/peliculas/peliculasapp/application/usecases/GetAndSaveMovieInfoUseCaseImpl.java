package com.peliculas.peliculasapp.application.usecases;
import com.peliculas.peliculasapp.domain.models.Movie;
import com.peliculas.peliculasapp.application.ports.in.GetAndSaveMovieInfoUseCase;
import com.peliculas.peliculasapp.application.ports.out.MovieRepositoryPort;
import com.peliculas.peliculasapp.dto.MovieDTO;
import com.peliculas.peliculasapp.dto.MovieInfoDTO;
import com.peliculas.peliculasapp.infrastructure.adapters.MovieDetailsAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Optional;


@Service
public class GetAndSaveMovieInfoUseCaseImpl implements GetAndSaveMovieInfoUseCase {
    private final MovieDetailsAdapter movieDetailsAdapter;
    private final MovieRepositoryPort movieRepository;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public GetAndSaveMovieInfoUseCaseImpl(
            MovieDetailsAdapter movieDetailsAdapter, MovieRepositoryPort movieRepository, ModelMapper modelMapper, RedisTemplate<String, Object> redisTemplate
            ) {
        this.movieDetailsAdapter = movieDetailsAdapter;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public MovieDTO getAndSaveMovieInfo(long movieId) {
        Movie movie = movieDetailsAdapter.getMovieInfoById(movieId);
        movieRepository.saveMovie(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    public MovieInfoDTO getMovieById(long movieId) {
        MovieInfoDTO movieInfoDTO = getMovieFromCache(movieId);
        if (movieInfoDTO == null) {
            System.out.println("Película no encontrada en caché. Buscando en la base de datos...");
            movieInfoDTO = getMovieFromDatabase(movieId);
            storeMovieInCache(movieId, movieInfoDTO);
        }
        System.out.println("Película encontrada en caché.");
        return movieInfoDTO;
    }

    private MovieInfoDTO getMovieFromCache(long movieId) {
        System.out.println("Intentando obtener película desde la caché...");
        return (MovieInfoDTO) redisTemplate.opsForValue().get("movie:" + movieId);
    }

    private void storeMovieInCache(long movieId, MovieInfoDTO movieInfoDTO) {
        System.out.println("Guardando película en la caché...");
        redisTemplate.opsForValue().set("movie:" + movieId, movieInfoDTO, Duration.ofMinutes(1));
    }

    private MovieInfoDTO getMovieFromDatabase(long movieId) {
        Optional<Movie> optionalMovie = movieRepository.getMovieById(movieId);
        if (optionalMovie.isPresent()) {
            System.out.println("Película encontrada en la base de datos.");
            return optionalMovie.map(movie -> modelMapper.map(optionalMovie, MovieInfoDTO.class)).orElse(null);
        } else {
            System.out.println("Película no encontrada en la base de datos.");
            return null;
        }
    }

}
