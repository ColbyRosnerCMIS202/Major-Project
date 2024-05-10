package com.example.demodemo.schedulebuilder;
import java.util.*;

// Worker class to store information about each worker
public class Worker {
    private String name;
    private String occupation;
    private boolean[] availability; // Using a boolean array to represent availability for each day (true for available, false for not available)

    public Worker(String name, String occupation, boolean[] availability) {
        this.name = name;
        this.occupation = occupation;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public boolean[] getAvailability() {
        return availability;
    }


    public String toString() {
        return "Name: " + name + ", Occupation: " + occupation + ", Availability: " + Arrays.toString(availability);
    }
}

