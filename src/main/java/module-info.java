module dp.pa {
    requires javafx.controls;
    requires javafx.fxml;


    opens dp.pa to javafx.fxml;
    exports dp.pa;
    exports dp.pa.controller;
    opens dp.pa.controller to javafx.fxml;
    opens dp.pa.model to javafx.base;
}