package ru.itmo.wp.model.domain;

import java.sql.Date;

public class Event {
    private long id;
    private long userId;
    private Type type;
    private Date creationTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Type getType() {
        return type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Event() {
    }

    public Event(long userId, Type type) {
        this.userId = userId;
        this.type = type;
    }

    public enum Type {
        ENTER("ENTER"), LOGOUT("LOGOUT");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
