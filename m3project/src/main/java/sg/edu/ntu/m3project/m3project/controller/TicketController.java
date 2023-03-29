package sg.edu.ntu.m3project.m3project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    // Get Tickets by All/User ID (to refactor to ticketService)
    @GetMapping
    public ResponseEntity<?> findAllById(@RequestHeader(value = "user_id", required = false) Integer userId) {
        try {
            return ticketService.find(userId);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Bad request."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    // Create Ticket (to update to array input for multiple tickets)
    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestHeader(value = "user_id") int userId,
            @RequestBody List<NewTicket> newTickets) {
        try {
            return ticketService.add(userId, newTickets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @PutMapping("/{ticket_id}") // change selectedSeatId to RequestParam?
    public ResponseEntity<?> changeSeat(
            @RequestHeader(value = "user_id") int userId,
            @PathVariable int ticket_id,
            @RequestBody String selectedSeatId) {
        try {
            return ticketService.changeSeat(userId, ticket_id, selectedSeatId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @PostMapping("/{ticket_id}/delete")
    public ResponseEntity<?> deleteTicket(
            @RequestHeader(value = "user_id") int userId,
            @PathVariable int ticket_id) {
        try {
            return ticketService.delete(userId, ticket_id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

}
