package sg.edu.ntu.m3project.m3project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.entity.SeatEntity;
import sg.edu.ntu.m3project.m3project.entity.TicketEntity;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.exceptions.TicketNotFoundException;
import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;
import sg.edu.ntu.m3project.m3project.repository.SeatRepository;
import sg.edu.ntu.m3project.m3project.repository.TicketRepository;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ConcertRepository concertRepo;

    @Autowired
    SeatRepository seatRepo;

    @Autowired
    UserService userService;

    // Find all if adminStatus = true
    public ResponseEntity<?> findAll(Integer userId) {
        userService.checkAdmin(userId);

        List<TicketEntity> tickets = (List<TicketEntity>) ticketRepo.findAll();
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("No ticket history."));
        } else
            return ResponseEntity.ok().body(tickets);
    }

    // Find by userId
    public ResponseEntity<?> findAllById(Integer userId) {
        List<TicketEntity> tickets = (List<TicketEntity>) ticketRepo.findByUserEntityId(userId);
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("No ticket history for user: " + userId));
        } else
            return ResponseEntity.ok().body(tickets);
    }

    // Add new tickets
    public ResponseEntity<?> add(int userId, List<NewTicket> newTickets) {
        // Check if Ticket array is valid
        boolean validSeats = true;
        for (NewTicket newTicket : newTickets) {
            Integer selectedConcertId = newTicket.getConcertId();
            String selectedSeatId = newTicket.getSeatId();

            Optional<ConcertEntity> selectedConcert = (Optional<ConcertEntity>) concertRepo
                    .findById(selectedConcertId);
            Optional<SeatEntity> selectedSeat = (Optional<SeatEntity>) seatRepo
                    .findById(selectedSeatId);
            Optional<TicketEntity> selectedConcertSeat = (Optional<TicketEntity>) ticketRepo
                    .findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
                            selectedSeatId,
                            selectedConcertId,
                            true);

            if (selectedConcert.isPresent() && selectedSeat.isPresent() && !selectedConcertSeat.isPresent()) {
                continue;
            } else {
                validSeats = false;
                break;
            }
        }
        // Add tickets to ticketRepo and return created tickets
        if (validSeats) {
            List<TicketEntity> createdTickets = new ArrayList<TicketEntity>();
            for (NewTicket newTicket : newTickets) {
                Integer selectedConcertId = newTicket.getConcertId();
                String selectedSeatId = newTicket.getSeatId();
                TicketEntity newTicketEntity = new TicketEntity();
                newTicketEntity.setSubmissionStatus(true);
                newTicketEntity.setConcertEntity(concertRepo.findById(selectedConcertId).get());
                newTicketEntity.setUserEntity(userRepo.findById(userId).get());
                newTicketEntity.setSeatEntity(seatRepo.findById(selectedSeatId).get());
                ticketRepo.save(newTicketEntity);
                createdTickets.add(ticketRepo.findById(newTicketEntity.getTicketId()).get());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTickets);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Selected concert/seat(s) not available. Please try again."));
    }

    // Check if ticket exists for user
    private TicketEntity checkTicket(int userId, int ticketId) {
        Optional<TicketEntity> ticket = (Optional<TicketEntity>) ticketRepo.findByTicketIdAndUserEntityId(ticketId,
                userId);
        if (ticket.isPresent()) {
            return ticket.get();
        } else
            throw new TicketNotFoundException();
    }

    // Change seat of specified ticket
    public ResponseEntity<?> changeSeat(int userId, int ticketId, String selectedSeatId) {
        TicketEntity ticket = checkTicket(userId, ticketId);
        int concertId = ticket.getConcertEntity().getId();
        Optional<SeatEntity> selectedSeat = (Optional<SeatEntity>) seatRepo
                .findById(selectedSeatId);
        Optional<TicketEntity> selectedConcertSeat = (Optional<TicketEntity>) ticketRepo
                .findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
                        selectedSeatId,
                        concertId,
                        true);

        if (selectedSeat.isPresent() && !selectedConcertSeat.isPresent()) {
            ticket.setSeatEntity(seatRepo.findById(selectedSeatId).get());
            ticketRepo.save(ticket);
            return ResponseEntity.ok().body(ticket);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Seat is unavailable"));
    }

    // Change submissionStatus to false
    public ResponseEntity<?> delete(int userId, int ticketId) {
        TicketEntity ticket = checkTicket(userId, ticketId);
        if (ticket.isSubmissionStatus()) {
            ticket.setSubmissionStatus(false);
            ticketRepo.save(ticket);
            return ResponseEntity.ok().body(new ResponseMessage("Ticket deleted."));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Ticket is already deleted."));

    }
}
