package sg.edu.ntu.m3project.m3project.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "concerts")
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String artist;

    @Column(name = "concert_date")
    Timestamp concertDate;

    @Column(name = "concert_venue")
    String concertVenue;

    @Column(name = "tickets_available")
    Integer ticketsAvailable;

    @Column(name = "ticket_price")
    float ticketPrice;

    @Column(name = "updated_at")
    Timestamp updatedAt = new Timestamp(new Date().getTime());

    @Column(name = "created_at", updatable = false)
    Timestamp createdAt = new Timestamp(new Date().getTime());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Timestamp getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(Timestamp concertDate) {
        this.concertDate = concertDate;
    }

    public String getConcertVenue() {
        return concertVenue;
    }

    public void setConcertVenue(String concertVenue) {
        this.concertVenue = concertVenue;
    }

    public Integer getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(Integer ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}