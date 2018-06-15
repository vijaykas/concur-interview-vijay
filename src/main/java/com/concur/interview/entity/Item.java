package com.concur.interview.entity;

public class Item {

    private long id;
    private String timestamp;

    public Item() {

    }

    public Item(long id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
