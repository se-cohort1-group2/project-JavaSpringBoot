package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> findAll() {
        List<UserEntity> users = (List<UserEntity>)userRepo.findAll();
        return ResponseEntity.ok().body(users);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable int id) {
        Optional<UserEntity> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            UserEntity user = optional.get();
            return ResponseEntity.ok().body(user);
        }
        //return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Error 404 - User ID Not Found"));
    }
}