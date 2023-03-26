package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.sql.Timestamp;

import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.service.UserService;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        try {
            List<UserEntity> users = (List<UserEntity>) userRepo.findAll();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable int id) {
        try {
            Optional<UserEntity> optional = userRepo.findById(id);
            if (optional.isPresent()) {
                UserEntity user = optional.get();
                return ResponseEntity.ok().body(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("User " + id + " not found. Please try a different User ID."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        return userService.login(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UserEntity user) {
        try {
            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Sorry, a user with this email already exists."));
            }
            UserEntity createNewUser = userRepo.save(user);
            return new ResponseEntity(userRepo.findById(createNewUser.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestHeader(name = "user-id", required = true) int userID,
            @RequestBody UserEntity user, @PathVariable int id) {
        try {
            Optional<UserEntity> userToBeUpdated = userRepo.findById(id);
            if (userToBeUpdated.isPresent()) {
                UserEntity editedUser = userToBeUpdated.get();

                // check if userInHeader exists
                Optional<UserEntity> userInHeader = userRepo.findById(userID);
                if (userInHeader.isPresent()) {
                    UserEntity headerUser = userInHeader.get();

                    // check if match - editedUser.getId() == userID
                    // check if admin - headerUser.isAdminStatus()
                    if (userID == editedUser.getId() || headerUser.isAdminStatus()) {
                        Timestamp updatedAt = new Timestamp(new Date().getTime());
                        editedUser.setName(user.getName());
                        editedUser.setPhone(user.getPhone());
                        editedUser.setEmail(user.getEmail());
                        editedUser.setPassword(user.getPassword());
                        editedUser.setAdminStatus(user.isAdminStatus());
                        editedUser.setUpdatedAt(updatedAt);
                        editedUser = userRepo.save(editedUser);
                        return ResponseEntity.ok().body(editedUser);
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new ResponseMessage("Sorry, you are not allowed to update this user."));
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseMessage("Invalid credentials."));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("User " + id + " not found. Please try a different User ID."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }
}