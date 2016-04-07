package fr.univtln.sgrassell418.restapp.utils;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by stephane on 02/04/16.
 */
public class Event {
    private int id;
    private String activity;
    private Timestamp date;

    public Event() {
    }

    public Event(int id, String activity, Timestamp date) {
        this.id = id;
        this.activity = activity;
        this.date = date;
    }

    public Event(String activity, Timestamp date) {
        this.activity = activity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String mapToJson() throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(this);
    }

    public static Event jsonToEvent(String json) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.readValue(json, Event.class);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", date=" + date +
                '}';
    }
}
