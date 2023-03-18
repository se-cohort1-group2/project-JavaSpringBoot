package sg.edu.ntu.m3project.m3project.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertRepository concertRepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ConcertEntity>> findAllAvailable() {

        Timestamp currentDatenTime = new Timestamp(new Date().getTime());
        List<ConcertEntity> currentConcertList = (List<ConcertEntity>) concertRepo
                .findByConcertDateAfter(currentDatenTime);
        if (currentConcertList.size() > 0) {

            return ResponseEntity.ok().body(currentConcertList);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<List<ConcertEntity>> findAll() {

        List<ConcertEntity> currentConcertList = (List<ConcertEntity>) concertRepo.findAll();

        if (currentConcertList.size() > 0) {

            return ResponseEntity.ok().body(currentConcertList);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{concertId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable int concertId) {

        Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

        if (optionalConcert.isPresent()) {
            ConcertEntity selectedConcert = optionalConcert.get();
            return new ResponseEntity<ConcertEntity>(selectedConcert, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Invalid concert id"));
    }

}
