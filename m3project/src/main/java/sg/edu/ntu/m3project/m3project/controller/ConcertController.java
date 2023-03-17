package sg.edu.ntu.m3project.m3project.controller;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertRepository concertRepo;

    @RequestMapping(value = "/{concertId}", method = RequestMethod.GET)
    public ResponseEntity<ConcertEntity> findById(@PathVariable int concertId) {

        Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

        if (optionalConcert.isPresent()) {
            ConcertEntity selectedConcert = optionalConcert.get();
            return new ResponseEntity<ConcertEntity>(selectedConcert, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

}
