package sg.edu.ntu.m3project.m3project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seats")
public class SeatEntity {

    @Id
    String seatId;

    @Column(name = "seat_category")
    String seatCategory;

    @Column(name = "venue_hall")
    String venueHall;

    @Column(name = "ticket_price")
    float ticketPrice;

    @Column(name = "concert_type")
    String concertType;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatCategory() {
        return seatCategory;
    }

    public void setSeatCategory(String seatCategory) {
        this.seatCategory = seatCategory;
    }

    public String getVenueHall() {
        return venueHall;
    }

    public void setVenueHall(String venueHall) {
        this.venueHall = venueHall;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getConcertType() {
        return concertType;
    }

    public void setConcertType(String concertType) {
        this.concertType = concertType;
    }

}
