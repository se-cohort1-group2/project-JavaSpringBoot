package sg.edu.ntu.m3project.m3project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import sg.edu.ntu.m3project.m3project.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    
}