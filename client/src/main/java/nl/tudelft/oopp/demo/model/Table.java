package nl.tudelft.oopp.demo.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Table {

    private final SimpleStringProperty date;

    private final SimpleIntegerProperty user;

    private final SimpleStringProperty timeslot;

    private final SimpleStringProperty building;

    private final SimpleStringProperty room;

    public Table(String inDate, int inUser, String inTimeslot, String inBuilding, String inRoom){
        this.date = new SimpleStringProperty(inDate);
        this.user = new SimpleIntegerProperty(inUser);
        this.timeslot  = new SimpleStringProperty(inTimeslot);
        this.building = new SimpleStringProperty(inBuilding);
        this.room = new SimpleStringProperty(inRoom);

    }

    public String getDate(){
        return date.get();
    }

    public void setDate(String v){
        date.set(v);
    }

    public Integer getUser(){
        return user.get();
    }

    public void setUser(Integer v){
        user.set(v);
    }

    public String getTimeslot(){
        return timeslot.get();
    }

    public void setTimeslot(String v){
        timeslot.set(v);
    }

    public String getBuilding(){
        return building.get();
    }

    public void setBuilding(String v){
        building.set(v);
    }

    public String getRoom(){
        return room.get();
    }

    public void setRoom(String v) {
        room.set(v);
    }



}
