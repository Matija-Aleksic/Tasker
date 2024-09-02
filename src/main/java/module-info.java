module com.javaprojektni.tasker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires java.mail;


    opens com.javaprojektni.tasker to javafx.fxml;
    exports com.javaprojektni.tasker;
    exports com.javaprojektni.tasker.controllers;
    opens com.javaprojektni.tasker.controllers to javafx.fxml;
    exports com.javaprojektni.tasker.Enums;
    opens com.javaprojektni.tasker.Enums to javafx.fxml;
}