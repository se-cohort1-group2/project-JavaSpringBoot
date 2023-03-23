package sg.edu.ntu.m3project.m3project.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;

@Service
public class ConcertService {

    @Autowired
    ConcertRepository concertRepo;

    public ResponseEntity<?> find(String caseDesc, String searchParam) {
        try {

            List<ConcertEntity> currentConcertList;

            switch (caseDesc) {
                case "upcoming":
                    // find all upcoming

                    Timestamp currentDatenTime = new Timestamp(new Date().getTime());
                    currentConcertList = (List<ConcertEntity>) concertRepo
                            .findByConcertDateAfter(currentDatenTime);

                    break;

                case "artist":
                    // find by artist
                    currentConcertList = (List<ConcertEntity>) concertRepo.findByArtist(searchParam);
                    if (currentConcertList.size() == 0) {
                        currentConcertList = (List<ConcertEntity>) concertRepo.findByArtistContaining(searchParam);
                    }

                    break;

                case "history":
                    // find all past and upcoming
                default:
                    currentConcertList = (List<ConcertEntity>) concertRepo.findAll();
                    break;
            }

            if (currentConcertList.size() > 0) {

                return ResponseEntity.ok().body(currentConcertList);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("No upcoming concerts"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }

    }

    public ResponseEntity<?> findbyConcertId(int concertId) {
        try {
            Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

            if (optionalConcert.isPresent()) {
                ConcertEntity selectedConcert = optionalConcert.get();
                return new ResponseEntity<ConcertEntity>(selectedConcert, HttpStatus.OK);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Invalid concert id."));
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    public ResponseEntity<?> create(ConcertEntity concert) {
        try {
            ConcertEntity newConcert = concertRepo.save(concert);
            return new ResponseEntity(concertRepo.findById(newConcert.getId()), HttpStatus.CREATED);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid inputs received from user."));
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    public ResponseEntity<?> update(ConcertEntity concert, int concertId) {
        try {
            Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

            if (optionalConcert.isPresent()) {
                ConcertEntity selectedConcert = optionalConcert.get();

                Timestamp updatedAt = new Timestamp(new Date().getTime());

                selectedConcert.setArtist(concert.getArtist());
                selectedConcert.setConcertDate(concert.getConcertDate());
                selectedConcert.setTicketPrice(concert.getTicketPrice());
                selectedConcert.setTicketsAvailable(concert.getTicketsAvailable());
                selectedConcert.setUpdatedAt(updatedAt);

                concertRepo.save(selectedConcert);
                return ResponseEntity.ok().body(selectedConcert);
            }

            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Invalid inputs received from user."));
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }

    }
}
