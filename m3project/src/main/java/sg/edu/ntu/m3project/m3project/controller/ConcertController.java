package sg.edu.ntu.m3project.m3project.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertRepository concertRepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllAvailable() {

        try {

            Timestamp currentDatenTime = new Timestamp(new Date().getTime());
            List<ConcertEntity> currentConcertList = (List<ConcertEntity>) concertRepo
                    .findByConcertDateAfter(currentDatenTime);
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

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity findAll() {

        try {

            List<ConcertEntity> currentConcertList = (List<ConcertEntity>) concertRepo.findAll();

            if (currentConcertList.size() > 0) {

                return ResponseEntity.ok().body(currentConcertList);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("No concert history"));

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }

    }

    @RequestMapping(value = "/{concertId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable int concertId) {

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody ConcertEntity concert) {
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

    @RequestMapping(value = "/{concertId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody ConcertEntity concert, @PathVariable int concertId) {

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
