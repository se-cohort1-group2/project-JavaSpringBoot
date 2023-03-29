package sg.edu.ntu.m3project.m3project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import sg.edu.ntu.m3project.m3project.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/tickets")
public class TicketController {

        @Autowired
        TicketService ticketService;

        // Get Tickets by All/User ID
        @GetMapping
        public ResponseEntity<?> findAllById(@RequestHeader(value = "user-id", required = false) Integer userId) {
                return ticketService.find(userId);
        }

        // Create Ticket (to update to array input for multiple tickets)
        @PostMapping
        public ResponseEntity<?> createTicket(
                        @RequestHeader(value = "user-id") int userId,
                        @RequestBody List<NewTicket> newTickets) {
                return ticketService.add(userId, newTickets);
        }

        @PutMapping("/{ticket_id}") // change selectedSeatId to RequestParam?
        public ResponseEntity<?> changeSeat(
                        @RequestHeader(value = "user-id") int userId,
                        @PathVariable int ticket_id,
                        @RequestBody String selectedSeatId) {
                return ticketService.changeSeat(userId, ticket_id, selectedSeatId);
        }

        @PostMapping("/{ticket_id}/delete")
        public ResponseEntity<?> deleteTicket(
                        @RequestHeader(value = "user-id") int userId,
                        @PathVariable int ticket_id) {
                return ticketService.delete(userId, ticket_id);
        }

}
