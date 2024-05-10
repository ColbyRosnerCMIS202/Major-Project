package com.example.demodemo.schedulebuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ScheduleBuilderUI extends Application {
        private List<Worker> workers; // List to store Worker objects
        private ListView<String> scheduleListView; // ListView to display schedules
        private CheckBox[] dayCheckboxes; // Array of CheckBoxes for days of the week
        private ChoiceBox<String> occupationChoiceBox; // ChoiceBox for selecting occupation
        private TextField nameField; // TextField for entering name

        // LinkedList to store Worker objects
        private LinkedList<Worker> workersList;

        @Override
        public void start(Stage primaryStage) {
            // Initialize workersList
            workersList = new LinkedList<>();

            // Initialize workers list
            workers = new ArrayList<>();

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setHgap(10);
            grid.setVgap(10);

            Label nameLabel = new Label("Name:");
            nameField = new TextField(); // Initialize here
            grid.add(nameLabel, 0, 0);
            grid.add(nameField, 1, 0);

            Label occupationLabel = new Label("Occupation:");
            occupationChoiceBox = new ChoiceBox<>();
            occupationChoiceBox.getItems().addAll("Cashier", "Driver");
            grid.add(occupationLabel, 0, 1);
            grid.add(occupationChoiceBox, 1, 1);

            Label availabilityLabel = new Label("Availability:");
            grid.add(availabilityLabel, 0, 2);

            String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            dayCheckboxes = new CheckBox[7];
            for (int i = 0; i < 7; i++) {
                dayCheckboxes[i] = new CheckBox(dayNames[i]);
                grid.add(dayCheckboxes[i], 0, i + 3);
            }

            HBox buttonBox = new HBox(10);
            // Add Worker button
            Button addButton = new Button("Add Worker");
            addButton.setOnAction(e -> {
                addWorker();
            });
            buttonBox.getChildren().addAll(addButton);

            // Build Schedule button
            Button buildButton = new Button("Build Schedule");
            buildButton.setOnAction(e -> {
                updateScheduleList();
            });
            buttonBox.getChildren().addAll(buildButton);

            // Sort and Write button
            Button sortAndWriteButton = new Button("Sort and Write");
            sortAndWriteButton.setOnAction(e -> {
                sortAndWriteWorkers();
            });
            buttonBox.getChildren().addAll(sortAndWriteButton);

            grid.add(buttonBox, 0, 11, 2, 1);

            scheduleListView = new ListView<>();
            VBox.setVgrow(scheduleListView, javafx.scene.layout.Priority.ALWAYS);
            VBox vbox = new VBox(scheduleListView);
            grid.add(vbox, 0, 12, 2, 1);

            Scene scene = new Scene(grid, 400, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Schedule Builder");
            primaryStage.show();
        }

        private String getDaysString(boolean[] availability) {
            StringBuilder days = new StringBuilder();
            String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
            for (int i = 0; i < availability.length; i++) {
                if (availability[i]) {
                    days.append(dayNames[i]).append(",");
                }
            }
            if (days.length() > 0) {
                days.setLength(days.length() - 1); // Remove the trailing comma
            }
            return days.toString();
        }

        private void addWorker() {
            String name = nameField.getText();
            String occupation = occupationChoiceBox.getValue();
            boolean[] availability = new boolean[7];
            for (int i = 0; i < 7; i++) {
                availability[i] = dayCheckboxes[i].isSelected();
            }

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/workerlog", "root",
                        "password");
                Statement statement = connection.createStatement();
                ResultSet resultset = statement.executeQuery("SELECT FROM * USERS");
                while (resultset.next()) {
                    System.out.println(resultset.getString("username"));
                    System.out.println(resultset.getString("password"));

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Worker worker = new Worker(name, occupation, availability);
            workersList.add(worker); // Add the worker to the workersList
            workers.add(worker); // Add the worker to the workers list

            try (FileWriter writer = new FileWriter("workers.txt", true)) {
                String days = getDaysString(worker.getAvailability());
                writer.write(worker.getName() + " - " + worker.getOccupation() + " (" + days + ")\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            nameField.clear();
            occupationChoiceBox.getSelectionModel().clearSelection();
            for (CheckBox checkbox : dayCheckboxes) {
                checkbox.setSelected(false);
            }
        }

        private void updateScheduleList() {
            ScheduleBuilder scheduleBuilder = new ScheduleBuilder();
            for (Worker worker : workers) {
                scheduleBuilder.addWorker(worker);
            }
            List<String> schedules = scheduleBuilder.buildSchedule(); // Get the schedules
            scheduleListView.getItems().setAll(schedules); // Display the schedules in the list view
        }

        private void sortAndWriteWorkers() {
            // Using a Deque to store workers
            Deque<Worker> workerDeque = new ArrayDeque<>();

            // Add all workers to the Deque
            for (Worker worker : workers) {
                workerDeque.offer(worker);
            }

            List<Worker> sortedWorkers = new ArrayList<>();

            // Separate cashiers and drivers
            for (Worker worker : workerDeque) {
                if (worker.getOccupation().equals("Cashier")) {
                    sortedWorkers.add(worker);
                }
            }

            for (Worker worker : workerDeque) {
                if (worker.getOccupation().equals("Driver")) {
                    sortedWorkers.add(worker);
                }
            }

            // Write the sorted workers to a file
            try (FileWriter writer = new FileWriter("sorted_workers.txt", true)) {
                for (Worker worker : sortedWorkers) {
                    String days = getDaysString(worker.getAvailability());
                    writer.write(worker.getName() + " - " + worker.getOccupation() + " (" + days + ")\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
