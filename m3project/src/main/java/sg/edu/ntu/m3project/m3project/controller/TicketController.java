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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/tickets")
public class TicketController {

        @Autowired
        TicketService ticketService;

        // Get all Tickets
        @GetMapping("/all")
        public ResponseEntity<?> findAll(@RequestHeader(value = "user-id") Integer userId) {
                return ticketService.findAll(userId);
        }

        // Get Tickets by User ID
        @GetMapping
        public ResponseEntity<?> findAllById(@RequestHeader(value = "user-id") Integer userId) {
                return ticketService.findAllById(userId);
        }

        // Create Tickets
        @PostMapping
        public ResponseEntity<?> createTicket(
                        @RequestHeader(value = "user-id") int userId,
                        @RequestBody List<NewTicket> newTickets) {
                return ticketService.add(userId, newTickets);
        }

        // Change Seat
        @PutMapping("/{ticket-id}")
        public ResponseEntity<?> changeSeat(
                        @RequestHeader(value = "user-id") int userId,
                        @PathVariable(value = "ticket-id") int ticketId,
                        @RequestParam String seat) {
                return ticketService.changeSeat(userId, ticketId, seat);
        }

        // Delete ticket
        @PutMapping("/{ticket-id}/delete")
        public ResponseEntity<?> deleteTicket(
                        @RequestHeader(value = "user-id") int userId,
                        @PathVariable(value = "ticket-id") int ticketId) {
                return ticketService.delete(userId, ticketId);
        }

}
