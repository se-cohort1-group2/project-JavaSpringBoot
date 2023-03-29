package sg.edu.ntu.m3project.m3project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.entity.SeatEntity;
import sg.edu.ntu.m3project.m3project.entity.TicketEntity;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.exceptions.UserNotFoundException;
import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;
import sg.edu.ntu.m3project.m3project.repository.SeatRepository;
import sg.edu.ntu.m3project.m3project.repository.TicketRepository;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
class TicketService {

    @Autowired
    var ticketRepo : TicketRepository?  = null;

    @Autowired
    var userRepo : UserRepository? = null;

    @Autowired
    var concertRepo : ConcertRepository? = null;

    @Autowired
    var seatRepo : SeatRepository? = null;


    fun checkUserId(userId : Int) {
        var user = userRepo?.findById(userId) as Optional<UserEntity> // change to UserEntity? when userRepo converted to kt
        // change to user !== null when userRepo converted to kt
        if (!user.isPresent()) throw UserNotFoundException()
    }
    
    fun find(userId : Int?): ResponseEntity<*> {
        if (userId == null) {
            var tickets = ticketRepo?.findAll() as List<TicketEntity?>
            if (tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseMessage("No ticket history."));
            } else
                return ResponseEntity.ok().body(tickets);
        } else {
                checkUserId(userId)
                var tickets = ticketRepo?.findByUserEntityId(userId) as List<TicketEntity>
                if (tickets.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ResponseMessage("No ticket history for user: " + userId));
                } else
                    return ResponseEntity.ok().body(tickets);
        }
    }

    fun add(userId : Int, newTickets : List<NewTicket>): ResponseEntity<*> {
        checkUserId(userId)
        var validSeats = true
        for (newTicket in newTickets) {
            var selectedSeat = seatRepo?.findById(newTicket.seatId)
            var selectedConcert = concertRepo?.findById(newTicket.concertId)
            var selectedConcertSeat = ticketRepo?.findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
                newTicket.seatId, 
                newTicket.concertId, 
                true)
            if (selectedConcert!!.isPresent() && selectedSeat !== null && selectedConcertSeat == null) {
                    continue;
                } else {
                    validSeats = false;
                    break;
                }
        }

        if (validSeats){
            var createdTickets = ArrayList<TicketEntity>()
            for (newTicket in newTickets) {
                var newTicketEntity = TicketEntity()
                newTicketEntity.concertEntity = concertRepo!!.findById(newTicket.concertId).get()
                newTicketEntity.userEntity = userRepo!!.findById(userId).get()
                newTicketEntity.seatEntity = seatRepo!!.findById(newTicket.seatId).get()
                newTicketEntity.submissionStatus = true
                ticketRepo?.save(newTicketEntity)
                createdTickets.add(ticketRepo!!.findById(newTicketEntity.ticketId!!).get())
            }
                return ResponseEntity.status(HttpStatus.CREATED).body(createdTickets);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ResponseMessage("Selected concert/seat(s) not available. Please try again."));
    }

    // public ResponseEntity<?> changeSeat(int userId, int ticketId, String selectedSeatId) {

    //     Optional<UserEntity> user = (Optional<UserEntity>) userRepo.findById(userId);
    //     if (user.isPresent()) {

    //         Optional<TicketEntity> ticket = (Optional<TicketEntity>) ticketRepo.findByTicketIdAndUserEntityId(ticketId,
    //                 userId);
    //         if (ticket.isPresent()) {
    //             int concertId = ticket.get().getConcertEntity().getId();

    //             Optional<SeatEntity> selectedSeat = (Optional<SeatEntity>) seatRepo
    //                     .findById(selectedSeatId);
    //             Optional<TicketEntity> selectedConcertSeat = (Optional<TicketEntity>) ticketRepo
    //                     .findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
    //                             selectedSeatId,
    //                             concertId,
    //                             true);

    //             if (selectedSeat.isPresent() && !selectedConcertSeat.isPresent()) {
    //                 ticket.get().setSeatEntity(seatRepo.findById(selectedSeatId).get());
    //                 ticketRepo.save(ticket.get());
    //                 return ResponseEntity.ok().body(ticket.get());
    //             } else
    //                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                         .body(new ResponseMessage("Seat is unavailable"));
    //         } else
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Ticket ID not found."));
    //     } else throw new UserNotFoundException();
    // }

    // public ResponseEntity<?> delete(int userId, int ticketId) {
    //     Optional<UserEntity> user = (Optional<UserEntity>) userRepo.findById(userId);
    //     if (user.isPresent()) {
    //         Optional<TicketEntity> ticket = (Optional<TicketEntity>) ticketRepo.findByTicketIdAndUserEntityId(ticketId,
    //                 userId);
    //         if (ticket.isPresent()) {
    //             if (ticket.get().isSubmissionStatus()) {
    //                 ticket.get().setSubmissionStatus(false);
    //                 ticketRepo.save(ticket.get());
    //                 return ResponseEntity.ok().body(new ResponseMessage("Ticket deleted."));
    //             } else
    //                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                         .body(new ResponseMessage("Ticket is already deleted."));
    //         } else
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Ticket ID not found."));
    //     } else throw new UserNotFoundException();
    // }
}
