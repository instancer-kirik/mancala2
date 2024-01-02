module com.instance.mancala2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires jogl.all;

    opens com.instance.mancala2 to javafx.fxml;
    exports com.instance.mancala2;
}