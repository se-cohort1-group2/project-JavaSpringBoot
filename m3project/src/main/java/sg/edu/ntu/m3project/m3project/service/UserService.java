package sg.edu.ntu.m3project.m3project.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.nio.file.AccessDeniedException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.sql.Timestamp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.exceptions.UserNotAdminException;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Value("${jwt.secret}")
    String secret;

    SecureRandom bCryptSR = new SecureRandom();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, bCryptSR);

    // yongxin
    public Map<String, String> generateToken(UserEntity user) {
        String jwtToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("userID", user.getId().toString());
        return jwtTokenGen;
    }

    // yongxin
    public String checkToken(String token, Integer userId) throws AccessDeniedException {
        String id = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (userId != Integer.parseInt(id)) {
            throw new AccessDeniedException("user-id and token do not match");
        }

        return id;

    }

    // yongxin
    public UserEntity hashPassword(UserEntity user) {
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return user;
    }

    public void checkAdmin(int userId) {
        UserEntity user = userRepo.findById(userId).get();
        if (!user.isAdminStatus()) {
            throw new UserNotAdminException("User does not have admin status.");
        }
    }

    // phoebe
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

    // phoebe
    public ResponseEntity<?> findById(int id) {
        try {
            Optional<UserEntity> userByID = userRepo.findById(id);
            if (userByID.isPresent()) {
                UserEntity user = userByID.get();
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

    // phoebe
    public ResponseEntity<?> update(int id, UserEntity user, int userID) {
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
                        editedUser.setPassword(user.getPassword() != null ? hashPassword(user).getPassword() : null);
                        /* editedUser.setAdminStatus(user.isAdminStatus()); */
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

    // phoebe
    public ResponseEntity<?> create(UserEntity user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Email and/or password cannot be empty."));
            }
            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Sorry, a user with this email already exists."));
            }
            UserEntity createNewUser = userRepo.save(hashPassword(user));
            return new ResponseEntity<>(userRepo.findById(createNewUser.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    // yongxin
    public UserEntity getUserForAuth(String email, String password) throws AccessDeniedException {
        Optional<UserEntity> optionalUser = userRepo.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new AccessDeniedException("User account not found, please try a different email.");
        }
        UserEntity foundUser = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new AccessDeniedException("Incorrect password for '" + email + "'. Please try again.");
        }
        return foundUser;
    }

    // yongxin
    public ResponseEntity<?> login(UserEntity user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage("Email and/or password cannot be empty."));
            }
            UserEntity selectedUser = this.getUserForAuth(user.getEmail(), user.getPassword());
            return new ResponseEntity<>(this.generateToken(selectedUser), HttpStatus.OK);
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(ade.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }
}
