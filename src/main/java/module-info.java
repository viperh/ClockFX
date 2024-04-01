/**
 * hello documentation
 */
module com.example.clockfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;



    opens com.example.clockfx to javafx.fxml;
    opens com.example.clockfx.Controllers to javafx.fxml;
    exports com.example.clockfx;
}