package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.service.ConcertService;

@CrossOrigin
@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertService concertService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllAvailable() {
        return concertService.find("upcoming", "");
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return concertService.find("history", "");

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(@RequestParam String artist) {

        return concertService.find("artist", artist.toUpperCase());
    }

    @RequestMapping(value = "/{concertId}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable int concertId) {
        return concertService.findbyConcertId(concertId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestHeader("user-id") int userId, @RequestBody ConcertEntity concert) {
        return concertService.create(userId, concert);
    }

    @RequestMapping(value = "/{concertId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody ConcertEntity concert, @PathVariable int concertId) {
        return concertService.update(concert, concertId);
    }

}
