package com.instance.mancala2;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndGameScreen extends View {
    private GamePreferences preferences;

    public EndGameScreen(GamePreferences preferences) {
        this.preferences = preferences;
        setupEndGameView();
    }

    private void setupEndGameView() {
        VBox view = new VBox(10);
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> playAgain());

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> returnToMainMenu());

        view.getChildren().addAll(playAgainButton, mainMenuButton);
        setCenter(view);
    }

    private void playAgain() {
        // Logic to start a new game
        // This might involve navigating to the game view or resetting the game state
    }

    private void returnToMainMenu() {
        // Navigate back to the main menu
        // Assuming "MAIN_MENU" is the ID of your main menu view registered with MobileApplication
        MobileApplication.getInstance().switchView("MAIN_MENU_VIEW_ID");
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("End Game");
        // You can add navigation back button or other actions here if needed
    }
}