package entities;

import com.vaadin.ui.Button;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by damo k on 01/02/2017.
 */
public class Tables extends Button implements Serializable{
    private int tableID;
    private int noOfSeats;

    private String area;

    public Tables(String caption) {
        this.setCaption(caption);
    }

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
