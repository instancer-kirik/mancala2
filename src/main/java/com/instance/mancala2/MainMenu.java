package com.instance.mancala2;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private VBox view = new VBox(10); // Simple vertical layout
    private Stage stage;

    public MainMenu(Stage stage) {

        this.stage = stage;
        setupMenu();
    }

    private void setupMenu() {
        // Set the background of the VBox to black
        view.setStyle("-fx-background-color: black;");
        view.setAlignment(Pos.CENTER);
        Button startGameButton = new Button("Start Game");
        styleButton(startGameButton);
        startGameButton.setOnAction(e -> startGame());

        Button preferencesButton = new Button("Preferences");
        styleButton(preferencesButton);
        preferencesButton.setOnAction(e -> showPreferences());

        Button quitButton = new Button("Quit");
        styleButton(quitButton);
        quitButton.setOnAction(e -> stage.close());

        view.getChildren().addAll(startGameButton, preferencesButton, quitButton);
    }

    private void styleButton(Button button) {
        // Set the text color to off-white and background to transparent (or any desired style)
        button.setStyle("-fx-text-fill: #F0E68C; -fx-background-color: dimgrey;");
    }

    private void startGame() {
        // Switch to game scene
        // You might instantiate your game scene here and set it on the stage


        MancalaGame mancalaGame = new MancalaGame(stage);

        // Set the game scene using the MancalaGame's scene
        stage.setScene(mancalaGame.getScene());
        stage.setTitle("mancala!");
        stage.show();
    }

    private void showPreferences() {
        // Show preferences dialog or scene
        // Here you could create a new Scene or a Popup for game preferences

        PreferencesScreen preferencesScreen = new PreferencesScreen(stage);
        preferencesScreen.show();

    }

    public Parent getView() {
        return view;
    }

    public static void navigateToMainMenu(Stage stage) {
        Platform.runLater(() -> {
            try {
                MainMenu menu = new MainMenu(stage); // Assuming MainMenu has a constructor that accepts Stage
                Scene scene = new Scene(menu.getView(),640,400); // Customize dimensions as necessary
                stage.setScene(scene);
                stage.show(); // Make sure the stage is shown
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
