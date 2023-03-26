package sg.edu.ntu.m3project.m3project.service;

import java.nio.file.AccessDeniedException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Value("${jwt.secret}")
    String secret;

    SecureRandom bCryptSR = new SecureRandom();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, bCryptSR);

    public Map<String, String> generateToken(UserEntity user) {

        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        return jwtTokenGen;
    }

    public UserEntity getUserForAuth(String email, String password) throws AccessDeniedException {
        Optional<UserEntity> optionalUser = userRepo.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new AccessDeniedException("Invalid email.");
        }

        UserEntity foundUser = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new AccessDeniedException("Invalid password.");

        }

        return foundUser;

    }

    public String checkToken(String token) throws AccessDeniedException {

        try {
            String email = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return email;
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid token");
        }
    }

    public UserEntity hashPassword(UserEntity user) {
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return user;
    }

    public ResponseEntity<?> login(UserEntity user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Email or password is empty."));
            }
            UserEntity selectedUser = this.getUserForAuth(user.getEmail(), user.getPassword());
            return new ResponseEntity<>(this.generateToken(selectedUser), HttpStatus.OK);
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ade.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    public ResponseEntity<?> register(UserEntity user) {
        UserEntity newUser = userRepo.save(hashPassword(user));
        return new ResponseEntity<>(userRepo.findById(newUser.getId()), HttpStatus.CREATED);
    }

}
