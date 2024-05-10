module com.example.demodemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demodemo to javafx.fxml;
    exports com.example.demodemo.schedulebuilder; // Export the package containing ScheduleBuilderUI

}