package com.peliculas.peliculasapp.application.services;
import com.peliculas.peliculasapp.application.ports.in.GetAndSaveMovieInfoUseCase;
import com.peliculas.peliculasapp.dto.MovieDTO;
import org.springframework.stereotype.Service;


@Service
public class MovieService {
    private final GetAndSaveMovieInfoUseCase getAndSaveInfoUseCase;

    public MovieService(GetAndSaveMovieInfoUseCase getAndSaveInfoUseCase) {
        this.getAndSaveInfoUseCase = getAndSaveInfoUseCase;
    }

    public MovieDTO saveMovieInfo(long movieId) {
        return getAndSaveInfoUseCase.getAndSaveMovieInfo(movieId);
    }
}
