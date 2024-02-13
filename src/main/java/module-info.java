module com.instance.mancala2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires jogl.all;
    requires com.gluonhq.charm.glisten;
    requires com.gluonhq.attach.util;
    requires com.gluonhq.attach.display;

    opens com.instance.mancala2 to javafx.fxml;
    exports com.instance.mancala2;
    exports com.instance.mancala2.gluonViews;
    opens com.instance.mancala2.gluonViews to javafx.fxml;
}