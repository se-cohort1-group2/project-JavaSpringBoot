package sg.edu.ntu.m3project.m3project.helper;

public class NewTicket {
    String seat_id;
    int concert_id;

    public NewTicket(String seat_id, int concert_id) {
        this.seat_id = seat_id;
        this.concert_id = concert_id;
    }

    public String getSeatId() {
        return seat_id;
    }

    public void setseatId(String seat_id) {
        this.seat_id = seat_id;
    }

    public int getConcertId() {
        return concert_id;
    }

    public void setConcertId(int concert_id) {
        this.concert_id = concert_id;
    }
}
