package com.instance.mancala2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MancalaGameApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Initial scene setup
        MainMenu menu = new MainMenu(primaryStage);
        Scene scene = new Scene(menu.getView(), 800, 600);

        primaryStage.setTitle("Mancala Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
