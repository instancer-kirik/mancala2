package com.instance.mancala2;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndGameScreen {
    private VBox view = new VBox(10);
    private Stage stage;
    GamePreferences preferences;
    public EndGameScreen(Stage stage,GamePreferences preferences) {
        this.preferences=preferences;
        this.stage = stage;
        setupEndGameView();
    }

    private void setupEndGameView() {
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> playAgain());

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> returnToMainMenu());

        view.getChildren().addAll(playAgainButton, mainMenuButton);
    }

    private void playAgain() {
        // Start a new game
    }

    private void returnToMainMenu() {
        MainMenu menu = new MainMenu(stage);
        stage.setScene(new Scene(menu.getView(), 800, 600));
    }

    public Parent getView() {
        return view;
    }
}
