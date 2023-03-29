package sg.edu.ntu.m3project.m3project.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response

import sg.edu.ntu.m3project.m3project.helper.NewTicket;
import sg.edu.ntu.m3project.m3project.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    var ticketService : TicketService? = null

    @GetMapping
    fun findAllById(
        @RequestHeader(value = "user_id", required = false) userId : Int?): ResponseEntity<*>? {
                return ticketService?.find(userId)
        }

    @PostMapping
    fun createTickets(
        @RequestHeader(value = "user_id") userId : Int,
        @RequestBody newTickets : List<NewTicket> ): ResponseEntity<*>? {
                return ticketService?.add(userId, newTickets)
        }


    @PutMapping("/{ticket_id}")
    fun changeSeat(
        @RequestHeader(value = "user_id") userId : Int,
        @PathVariable ticket_id : Int,
        @RequestBody selectedSeatId : String) : ResponseEntity<*>? {
                return ticketService?.changeSeat(userId, ticket_id, selectedSeatId)

        }
//     public ResponseEntity<?> changeSeat(
//             @RequestHeader(value = "user_id") int userId,
//             @PathVariable int ticket_id,
//             @RequestBody String selectedSeatId) {
//             return ticketService.changeSeat(userId, ticket_id, selectedSeatId);
//     }

//     @PostMapping("/{ticket_id}/delete")
//     public ResponseEntity<?> deleteTicket(
//             @RequestHeader(value = "user_id") int userId,
//             @PathVariable int ticket_id) {
//             return ticketService.delete(userId, ticket_id);
//     }

}
