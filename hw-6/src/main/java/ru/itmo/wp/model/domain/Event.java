package ru.itmo.wp.model.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public void setType(String typeName) {
        this.type = Type.strToType.get(typeName);
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
        private static final Map<String, Type> strToType = new HashMap<>();

        static {
            strToType.put(ENTER.name, ENTER);
            strToType.put(LOGOUT.name, LOGOUT);
        }

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type getType(String name) {
            return strToType.get(name);
        }
    }
}
