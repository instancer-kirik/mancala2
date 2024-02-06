package com.instance.mancala2;

import javafx.application.Platform;
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
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> startGame());

        Button preferencesButton = new Button("Preferences");
        preferencesButton.setOnAction(e -> showPreferences());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> stage.close());

        view.getChildren().addAll(startGameButton, preferencesButton, quitButton);
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
                Scene scene = new Scene(menu.getView(), 800, 600); // Customize dimensions as necessary
                stage.setScene(scene);
                stage.show(); // Make sure the stage is shown
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
