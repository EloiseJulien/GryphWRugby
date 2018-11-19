package ayoolamakinde.eloisejulien.gryphswrugby.models;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{
    private String id;
    private String location;
    private String date;
    private String startHour;
    private String endHour;
    private String name;

    public Event(){}

    public Event(String id, String location, String date, String name, String startHour, String endHour){
        this.id = id;
        this.location = location;
        this.date = date;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Event(String location, String date, String name, String startHour, String endHour){
        this.location = location;
        this.date = date;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
