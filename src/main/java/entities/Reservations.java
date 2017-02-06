package entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by damo k on 01/02/2017.
 */
public class Reservations implements Serializable {
    private int reservationID;
    private float startingTime;
    private float endingTime;
    private Date date;
    private String name;
    private int number;
    private String email;
    private int numberOfPeople;
    private String description;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public float getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(float startingTime) {
        this.startingTime = startingTime;
    }

    public float getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(float endingTime) {
        this.endingTime = endingTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
