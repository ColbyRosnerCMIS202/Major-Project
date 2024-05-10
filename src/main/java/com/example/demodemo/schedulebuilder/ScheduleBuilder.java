package com.example.demodemo.schedulebuilder;
import java.util.ArrayList;
import java.util.List;

// ScheduleBuilder class to build the weekly schedule
public class ScheduleBuilder {
    private List<Worker> workers;

    public ScheduleBuilder() {
        workers = new ArrayList<>();
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public List<String> buildSchedule() {
        List<String> schedules = new ArrayList<>();

        for (Worker worker : workers) {
            StringBuilder scheduleBuilder = new StringBuilder();
            scheduleBuilder.append(worker.getName()).append("'s Schedule:\n");

            boolean[] availability = worker.getAvailability();
            for (int i = 0; i < 7; i++) {
                String dayAvailability = availability[i] ? "Available" : "Not Available";
                scheduleBuilder.append("Day ").append(i + 1).append(": ").append(dayAvailability).append("\n");
            }

            schedules.add(scheduleBuilder.toString());
        }

        return schedules;
    }
}