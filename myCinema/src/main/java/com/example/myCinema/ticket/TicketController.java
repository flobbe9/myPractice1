package com.example.myCinema.ticket;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


/**
 * Contains enpoint mappings for the ticket entity. For testing purposes.
 * Can only be used by a User with role 'ADMIN'.
 */
@RestController
@RequestMapping("/test/ticket")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;


    @PostMapping("/addNew")
    public Ticket addNew(@RequestBody Ticket ticket) {

        return ticketService.addNew(ticket);
    }


    @GetMapping("/getBySeat")
    public Ticket getBySeat(@RequestParam("theatreNumber") int theatreNumber, 
                            @RequestParam("rowLetter") char rowLetter,
                            @RequestParam("seatNumber") int seatNumber) {

        return ticketService.getBySeat(theatreNumber, rowLetter, seatNumber);
    }


    @GetMapping("/getByUserName")
    public List<Ticket> getByEmail(@RequestParam("userName") String email) {

        return ticketService.getByEmail(email);   
    }


    @DeleteMapping("/delete")
    public void delete(@RequestParam("theatreNumber") int theatreNumber, 
                       @RequestParam("rowLetter") char rowLetter, 
                       @RequestParam("seatNumber") int seatNumber) {
        
        ticketService.delete(theatreNumber, rowLetter, seatNumber);
    }
}