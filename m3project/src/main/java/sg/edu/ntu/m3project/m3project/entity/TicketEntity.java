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
    Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seatEntity;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private ConcertEntity concertEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "submission_status")
    boolean submissionStatus;

    @Column(name = "created_at", updatable = false)
    Timestamp createdAt = new Timestamp(new Date().getTime());

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public SeatEntity getSeatEntity() {
        return seatEntity;
    }

    public void setSeatEntity(SeatEntity seatEntity) {
        this.seatEntity = seatEntity;
    }

    public ConcertEntity getConcertEntity() {
        return concertEntity;
    }

    public void setConcertEntity(ConcertEntity concertEntity) {
        this.concertEntity = concertEntity;
    }

    public boolean isSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(boolean submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
