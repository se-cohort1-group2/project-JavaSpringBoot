package sg.edu.ntu.m3project.m3project.service;

import java.nio.file.AccessDeniedException;
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
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class ConcertService {

    @Autowired
    ConcertRepository concertRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserService userService;

    public ResponseEntity<?> find(String findBy, String searchParam) {
        try {

            List<ConcertEntity> currentConcertList;

            switch (findBy) {
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

    public ResponseEntity<?> create(String token, ConcertEntity concert) {
        try {
            // userValidation.checkUser(userId);
            userService.checkToken(token);
            ConcertEntity newConcert = concertRepo.save(concert);
            return new ResponseEntity(concertRepo.findById(newConcert.getId()), HttpStatus.CREATED);
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage(ade.getMessage()));

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

    public ResponseEntity<?> update(int userId, ConcertEntity concert, int concertId) {
        try {
            userValidation.checkUser(userId);
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

        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage(ade.getMessage()));

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

    // public void userValidation(int userId) throws AccessDeniedException {

    // Optional<UserEntity> optionalUser = userRepo.findById(userId);
    // if (optionalUser.isPresent()) {
    // UserEntity user = optionalUser.get();
    // if (!user.isAdminStatus())
    // throw new AccessDeniedException("User is not an administrator");
    // } else {
    // throw new AccessDeniedException("Please login to continue.");
    // }
    // }

}
