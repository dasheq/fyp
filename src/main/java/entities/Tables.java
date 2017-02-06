package entities;

import java.io.Serializable;

/**
 * Created by damo k on 01/02/2017.
 */
public class Tables implements Serializable{
    private int tableID;
    private int noOfSeats;
    private int reservationID;

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
}
