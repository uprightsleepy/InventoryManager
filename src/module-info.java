module InventorySystem.HenryPhillips {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens model to javafx.graphics, javafx.controls, javafx.fxml, javafx.base;
    opens controller to javafx.graphics, javafx.controls, javafx.fxml;
}