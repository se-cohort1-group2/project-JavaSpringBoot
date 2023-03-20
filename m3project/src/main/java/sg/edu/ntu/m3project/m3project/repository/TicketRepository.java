package sg.edu.ntu.m3project.m3project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.m3project.m3project.entity.TicketEntity;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, Integer> {
    List<TicketEntity> findByUserId(Integer userId);

    Optional<TicketEntity> findBySeatIdAndConcertEntityIdAndSubmissionStatus(String seatId, Integer concertId, boolean submissionStatus);
}
