module app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    exports com.example.source;
    opens com.example.source to javafx.fxml;
    exports com.example.source.claseTabele;
    opens com.example.source.claseTabele to javafx.fxml;
    exports com.example.source.controller.ResurseUmane;
    opens com.example.source.controller.ResurseUmane to javafx.fxml;
    exports com.example.source.controller.Receptioner;
    opens com.example.source.controller.Receptioner to javafx.fxml;
    exports com.example.source.controller.Admin;
    opens com.example.source.controller.Admin to javafx.fxml;
    exports com.example.source.controller.Asistent;
    opens com.example.source.controller.Asistent to javafx.fxml;
    exports com.example.source.controller.Economic;
    opens com.example.source.controller.Economic to javafx.fxml;
    exports com.example.source.controller.Altele;
    opens com.example.source.controller.Altele to javafx.fxml;
    exports com.example.source.controller.Medic;
    opens com.example.source.controller.Medic to javafx.fxml;
}