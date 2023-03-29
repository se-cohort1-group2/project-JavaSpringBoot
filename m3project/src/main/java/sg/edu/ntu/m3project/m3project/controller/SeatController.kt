package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.dao.EmptyResultDataAccessException
import sg.edu.ntu.m3project.m3project.service.SeatService
import sg.edu.ntu.m3project.m3project.entity.SeatEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@RestController
@RequestMapping("seats")
class SeatController (val seatService: SeatService){
    
    @ExceptionHandler
    fun handleHttpMessageNotReadableException(e:HttpMessageNotReadableException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage("Bad request."))
    }

    @ExceptionHandler
    fun handleNoSuchElementException(e:NoSuchElementException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage("Seat not found."))
    }

    @ExceptionHandler
    fun handleEmptyResultDataAccessException(e:EmptyResultDataAccessException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage("Seat not found."))
    }

    @ExceptionHandler
    fun handleException(e:Exception): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
    }

    @GetMapping
    fun findAll(): List<SeatEntity?> = seatService.findAll()

    @GetMapping("/{seatId}")
    fun findById(@PathVariable seatId:String) = seatService.findById(seatId)

    @PostMapping
    fun createSeat(@RequestBody seatEntity : SeatEntity): ResponseEntity<Any> = seatService.create(seatEntity)

    @PutMapping
    fun updateSeat(@RequestBody seatEntity : SeatEntity) {
        seatService.update(seatEntity)
    }    

    @DeleteMapping("{seatId}")
    fun deleteSeat(@PathVariable seatId:String) {seatService.delete(seatId)}

}