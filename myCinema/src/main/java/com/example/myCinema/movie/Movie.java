package com.example.myCinema.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.myCinema.ticket.Discount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Contains all information the website has to know about a movie playing in cinema.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Movie {
    
    @Id
    // @GeneratedValue(generator = "_movie_id_sequence", strategy = GenerationType.SEQUENCE)
    // @SequenceGenerator(name = "_movie_id_sequence", allocationSize = 1) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false)
    private String title;

    /** Duration in minutes. */
    @Column(nullable = false)
    private Integer duration; 

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate localReleaseDate;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate localFinishingDate;

    @Column(nullable = false)
    private String synopsis;   

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FSK fsk;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "version")
    @EqualsAndHashCode.Exclude
    private Set<MovieVersion> versions;

    /** Represents a basic price which is adjusted in certain cases. */
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false) 
    private String director;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "cast_member")
    @EqualsAndHashCode.Exclude
    private List<String> cast;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    @EqualsAndHashCode.Exclude
    private Set<Genre> genres; 

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private String trailerLink;


    public Movie(String title, 
                 Integer duration, 
                 LocalDate localReleaseDate, 
                 LocalDate localFinishingDate,
                 String synopsis, 
                 FSK fsk,
                 Set<MovieVersion> versions, 
                 Double price, 
                 String director,
                 List<String> cast,
                 Set<Genre> genres,
                 String trailerLink) {

        this.title = title;
        this.duration = duration;
        this.localReleaseDate = localReleaseDate;
        this.localFinishingDate = localFinishingDate;
        this.synopsis = synopsis;
        this.fsk = fsk;
        this.versions = versions;
        this.price = price;
        this.director = director;
        this.cast = cast;
        this.genres = genres;
        this.trailerLink = trailerLink;
    }


    @Override
    public String toString() {

        return this.title;
    }
}


/**
 * Wrapper class for html pages that need lists of the enums related to the movie entity. 
 * Also serves as container for cast and genres because thymeleaf works better with arrays but the api doesn't.
 */
@Getter
@Setter
class MovieWrapper {
    
    private FSK[] fsk = FSK.values();

    private MovieVersion[] versions = MovieVersion.values();

    private Discount[] discounts = Discount.values();

    /** A true value stands for the toggled version at the same index in the versions array. */
    private boolean[] toggledVersions = new boolean[versions.length];

    private Genre[] genres = Genre.values();

    /** A true value stands for the toggled genre at the same index in the genres array. */
    private boolean[] toggledGenres = new boolean[genres.length];

    private String[] movieCast = new String[3];

    private List<Movie> movies;
}