package sg.edu.ntu.m3project.m3project.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;

public interface ConcertRepository extends CrudRepository<ConcertEntity, Integer> {
}
