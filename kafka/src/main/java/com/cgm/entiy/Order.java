package com.cgm.entiy;

public class Order {


    private long id;

    private long name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
    public Order(long id,long name) {
        this.id = id;
        this.name = name;
    }

}
