package sg.edu.ntu.m3project.m3project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.m3project.m3project.entity.SeatEntity;

@Repository
interface SeatRepository : CrudRepository<SeatEntity?, String?>
