package entities;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;

import java.awt.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by damo k on 01/02/2017.
 */
public class TablesCheckBox extends CheckBox implements Serializable{
    private int tableID;
    private int noOfSeats;
    private boolean isReserved;
    private String area;

    public TablesCheckBox(String caption) {
        this.setCaption(caption);
    }

    public TablesCheckBox() {
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
        //setStyleName();
    }

}
