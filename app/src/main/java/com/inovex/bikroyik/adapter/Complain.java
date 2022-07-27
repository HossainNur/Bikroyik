package com.inovex.bikroyik.adapter;

public class Complain {

    String id,name, title, note;

    public Complain(String id, String name, String title, String note) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
