package sg.edu.ntu.m3project.m3project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.entity.TicketEntity;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;
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

    public ResponseEntity<?> add(Integer userId, NewTicket newTicket) {

        Optional<UserEntity> user = (Optional<UserEntity>) userRepo.findById(userId);
        if (user.isPresent()) {
            Integer selectedConcertId = newTicket.getConcertId();
            Optional<ConcertEntity> selectedConcert = (Optional<ConcertEntity>) concertRepo
                    .findById(selectedConcertId);
            if (selectedConcert.isPresent()) {
                String selectedSeatId = newTicket.getSeatId();
                Optional<TicketEntity> selectedSeat = (Optional<TicketEntity>) ticketRepo
                        .findBySeatIdAndConcertEntityId(selectedSeatId, selectedConcertId);
                if (!selectedSeat.isPresent()) {
                    TicketEntity newTicketEntity = new TicketEntity();
                    newTicketEntity.setSubmissionStatus(true);
                    newTicketEntity.setConcertEntity(selectedConcert.get());
                    newTicketEntity.setUserId(userId);
                    newTicketEntity.setSeatId(selectedSeatId);
                    ticketRepo.save(newTicketEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseMessage("Seat is not available."));

            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Concert not found."));

        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("User not found."));
    }

}
