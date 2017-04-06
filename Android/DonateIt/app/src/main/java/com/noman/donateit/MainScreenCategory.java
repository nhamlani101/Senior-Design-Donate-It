package com.noman.donateit;

/**
 * Created by noman on 3/28/17.
 */

public class MainScreenCategory {

    private String name;
    private int resource;

    public MainScreenCategory(String name, int resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
