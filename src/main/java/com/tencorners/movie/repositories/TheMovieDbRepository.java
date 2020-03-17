package com.tencorners.movie.repositories;

import com.tencorners.movie.domains.TheMovieDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheMovieDbRepository extends JpaRepository<TheMovieDb, Long> {
}
