package sg.edu.ntu.m3project.m3project.service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class UserValidation {

    // this could be combined with userservice

    @Autowired
    UserRepository userRepo;

    public void checkUser(int userId) throws AccessDeniedException {

        Optional<UserEntity> optionalUser = userRepo.findById(userId);

        /*
         * if requester has admin access, it will not throw exception & process
         * continues
         */
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            if (!user.isAdminStatus())
                // not an admin
                throw new AccessDeniedException("User is not an administrator");

        } else {
            // not a user
            throw new AccessDeniedException("Please login to continue.");
        }
    }
}
