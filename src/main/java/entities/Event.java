package entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by damo k on 01/02/2017.
 */
public class Event implements Serializable{
    private int eventID;
    private String actName;
    private String actType;
    private float price;
    private float startingTime;
    private float endTime;
    private Date date;
    private String area;

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(float startingTime) {
        this.startingTime = startingTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
