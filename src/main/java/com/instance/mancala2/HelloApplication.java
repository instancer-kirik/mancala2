package com.instance.mancala2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        ///Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        //stage.setScene(scene);
       // Scene scene = new Scene(fxmlLoader.load());
       // stage.setScene(scene);


        MancalaGame mancalaGame = new MancalaGame();

        // Set the game scene using the MancalaGame's scene
        stage.setScene(mancalaGame.getScene());
        stage.setTitle("mancala!");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}