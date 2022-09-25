package com.example.myCinema.theatre.row;

import static com.example.myCinema.theatre.Theatre.NUM_SEATS_PER_ROW_NORMAL_THEATRE;
import static com.example.myCinema.theatre.row.RowRank.PARQUET;
import static com.example.myCinema.theatre.seat.SeatType.DISABLED;
import static com.example.myCinema.theatre.seat.SeatType.FOOTREST;
import static com.example.myCinema.theatre.seat.SeatType.LOVE_SEAT;
import static com.example.myCinema.theatre.seat.SeatType.NORMAL;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.myCinema.theatre.Theatre;
import com.example.myCinema.theatre.seat.Seat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Contains row data, a list of seats and the corresponding theatre.
 */
@Entity
@Table(name = "theatre_rows")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false)
    private Character rowLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RowRank rowRank;

    @Column(nullable = false)
    private Integer seatsPerRow;
    
    @Column(nullable = false)
    private Boolean frontRow;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id")
    @JsonBackReference
    private Theatre theatre;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "row_id")
    @JsonManagedReference
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private List<Seat> seats = new LinkedList<Seat>();


    public Row(Character rowLetter, 
               RowRank rowRank, 
               Integer seatsPerRow, 
               Boolean frontRow) {

        this.rowLetter = rowLetter;
        this.rowRank = rowRank;
        this.seatsPerRow = seatsPerRow;
        this.frontRow = frontRow;

        // generating seats
        this.seats = generateSeats();
    }


    private List<Seat> generateSeats() {

        // parquet row
        if (rowRank == PARQUET) {
            generateParquetRow(seats);

        // box row
        } else {
            generateBoxRow(seats);
        }
        
        return seats;
    }
    
    
    /**
     * Parquet rows have NORMAL seats only with the exception of the front row, where
     * DISABLED seats are placed (two or three depending on the theatre size).
     *  
     * @param seats list to be filled with seat objects.
     */
    private void generateParquetRow(List<Seat> seats) {
        
        for (int i = 0; i < seatsPerRow; i++) {

            // front row
            if (frontRow) {
                // first seat of row is disabled seat
                if (i == 0) {
                    seats.add(new Seat(rowLetter, i + 1, DISABLED));

                // last seat of row is also disabled seat
                } else if (i == seatsPerRow - 1) {
                    seats.add(new Seat(rowLetter, i + 1, DISABLED));

                // next-to-last seat of row in case of bigCinema is also disabled
                } else if (seatsPerRow > NUM_SEATS_PER_ROW_NORMAL_THEATRE && i == seatsPerRow - 2) {
                    seats.add(new Seat(rowLetter, i + 1, DISABLED));

                // any other seat is normal
                } else {
                    seats.add(new Seat(rowLetter, i + 1, NORMAL));
                }

            // normal row not in front
            } else {
                seats.add(new Seat(rowLetter, i + 1, NORMAL));
            }
        }
    }
    
    
    /**
     * Box rows all have FOOTREST seats and the two last seats in each row are LOVE_SEATS.
     * 
     * @param seats list to be filled with seat objects.
     */
    private void generateBoxRow(List<Seat> seats) {

        for (int i = 0; i < seatsPerRow; i++) {

            // two last seats are love seats
            if (seatsPerRow > NUM_SEATS_PER_ROW_NORMAL_THEATRE && (i == seatsPerRow - 1 || i == seatsPerRow - 2)) {
                seats.add(new Seat(rowLetter, i + 1, LOVE_SEAT));

            // any other seat with footrest
            } else {
                seats.add(new Seat(rowLetter, i + 1, FOOTREST));
            }
        }
    }
    
    
    @Override
    public String toString() {
        
        return "Row " + this.rowLetter;
    }
}