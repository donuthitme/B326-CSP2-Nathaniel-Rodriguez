package com.joysistvi.recordingapp.model;

import java.sql.Date;

public class Playlist {
    private int id;
    private Date dateCreated;
    private int userId;

    public Playlist(Date dateCreated, int userId) {
        this.dateCreated = dateCreated;
        this.userId = userId;
    }

    public Playlist(int id, Date dateCreated, int userId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return String.format("Playlist{id=%d, dateCreated=%s, userId=%d}",
                id, dateCreated, userId);
    }
}