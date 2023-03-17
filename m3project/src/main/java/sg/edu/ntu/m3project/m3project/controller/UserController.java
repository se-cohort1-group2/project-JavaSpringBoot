package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;

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
    public ResponseEntity<UserEntity> findById(@PathVariable int id) {
        Optional<UserEntity> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            UserEntity product = optional.get();
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.notFound().build();
    }
}