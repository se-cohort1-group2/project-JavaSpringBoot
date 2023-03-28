package sg.edu.ntu.m3project.m3project.service;

import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import sg.edu.ntu.m3project.m3project.repository.SeatRepository;
import sg.edu.ntu.m3project.m3project.entity.SeatEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@Service
class SeatService(val seatRepo:SeatRepository){

    fun findAll(): List<SeatEntity?> = seatRepo.findAll().toList()

    fun findById(seatId:String): SeatEntity = seatRepo.findById(seatId).get()

    fun create(seatEntity:SeatEntity): ResponseEntity<Any> {
        if(!seatRepo.findById(seatEntity.seatId.toString()).isPresent()) {
        seatRepo.save(seatEntity)
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(seatEntity)
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseMessage("Seat already exists."))
    }

    fun update(seatEntity:SeatEntity): SeatEntity {
        val existingSeat:SeatEntity = seatRepo.findById(seatEntity.seatId.toString()).get()
        existingSeat.seatCategory = seatEntity.seatCategory
        existingSeat.venueHall = seatEntity.venueHall
        existingSeat.ticketPrice = seatEntity.ticketPrice
        existingSeat.concertType = seatEntity.concertType
        seatRepo.save(existingSeat)
        return seatEntity
    }

    fun delete(seatId:String) {seatRepo.deleteById(seatId)}

}