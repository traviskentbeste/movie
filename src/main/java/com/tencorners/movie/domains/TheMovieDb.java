package com.tencorners.movie.domains;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
@Data
public class TheMovieDb {

    @Id
    private Long id;
    @Column(name="overview", columnDefinition="CLOB NOT NULL")
    @Lob
    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private Boolean video;
    private String title;
    //private List<Genre> genreIds;
    private String posterPath;
    private String backdropPath;
    private Date releaseDate;
    private Float popularity;
    private Float voteAverage;
    private Boolean adult;
    private Float voteCount;

    @Column(name = "updated_datetimestamp")
    @UpdateTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedDatetimestamp;

    @Column(name = "created_datetimestamp")
    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDatetimestamp;

}
