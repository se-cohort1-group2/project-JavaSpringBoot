package sg.edu.ntu.m3project.m3project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.entity.TicketEntity;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.TicketRepository;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TicketService ticketService;

    // Get Tickets by All/User ID (to refactor to ticketService)
    @GetMapping
    public ResponseEntity<?> findAllById(@RequestHeader(value = "user_id", required = false) Integer userId) {
        try {
            if (userId == null) {
                List<TicketEntity> tickets = (List<TicketEntity>) ticketRepo.findAll();
                if (tickets.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseMessage("No ticket history."));
                } else
                    return ResponseEntity.ok().body(tickets);
            } else {
                Optional<UserEntity> user = (Optional<UserEntity>) userRepo.findById(userId);
                if (user.isPresent()) {
                    List<TicketEntity> tickets = (List<TicketEntity>) ticketRepo.findByUserId(userId);
                    if (tickets.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseMessage("No ticket history for user: " + userId));
                    } else
                        return ResponseEntity.ok().body(tickets);
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseMessage("User not found."));
            }
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

    // Create Tickets (to update to array input)
    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestHeader(value = "user_id") int userId,
            @RequestBody NewTicket newTicket) {
        try {
            return ticketService.add(userId, newTicket);
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

}
