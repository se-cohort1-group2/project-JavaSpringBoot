package sg.edu.ntu.m3project.m3project.service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.authentication.JwtGeneratorImpl;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtGeneratorImpl jwtGenerator;

    public UserEntity getUserForAuth(String email, String password) throws AccessDeniedException {
        Optional<UserEntity> optionalUser = userRepo.findByEmailAndPassword(email, password);

        if (!optionalUser.isPresent()) {
            throw new AccessDeniedException("Invalid email or password.");
        }
        return optionalUser.get();
    }

    public ResponseEntity<?> login(UserEntity user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Email or password is empty."));
            }
            UserEntity selectedUser = this.getUserForAuth(user.getEmail(), user.getPassword());
            return new ResponseEntity<>(jwtGenerator.generateToken(selectedUser), HttpStatus.OK);
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ade.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

}
