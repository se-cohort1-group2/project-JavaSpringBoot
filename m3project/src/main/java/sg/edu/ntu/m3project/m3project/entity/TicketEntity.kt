package sg.edu.ntu.m3project.m3project.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ticketId : Int? = null

    @ManyToOne
    @JoinColumn(name = "seat_id")
    var seatEntity : SeatEntity? = null
    
    @ManyToOne
    @JoinColumn(name = "concert_id")
    var concertEntity : ConcertEntity? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userEntity : UserEntity? = null

    @Column(name = "submission_status")
    var submissionStatus = false;

    @Column(name = "created_at", updatable = false)
    var createdAt = Timestamp(Date().time)

}
