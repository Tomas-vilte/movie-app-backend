package com.peliculas.peliculasapp.infrastructure.repositories;
import com.peliculas.peliculasapp.infrastructure.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Transactional(readOnly = true)
    boolean existsByMovieId(final long id);
    @Transactional(readOnly = true)
    Optional<MovieEntity> findByMovieId(final long id);
}
