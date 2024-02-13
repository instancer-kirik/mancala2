package com.instance.mancala2.gluonViews;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class MainMenuView extends View {

    public MainMenuView() {
        // Calling the default constructor
        super();
        // Initialize your layout within the constructor
        VBox view = new VBox(10);
        view.setAlignment(Pos.CENTER);
        view.setStyle("-fx-background-color: black;");
        setupMenu(view);
        // Set the VBox as the center of this View
        this.setCenter(view);
    }

    private void setupMenu(VBox view) {
        // Setup buttons and their actions
        Button startGameButton = new Button("Start Game");
        styleButton(startGameButton);
        // Assuming "GAME_VIEW" is a view name you've registered elsewhere
        startGameButton.setOnAction(e -> AppManager.getInstance().switchView("GAME_VIEW"));

        Button preferencesButton = new Button("Preferences");
        styleButton(preferencesButton);
        // Adjust this for Gluon navigation if you have a preferences view
         preferencesButton.setOnAction(e -> AppManager.getInstance().switchView("PREFERENCES_VIEW"));

        Button quitButton = new Button("Quit");
        styleButton(quitButton);
        quitButton.setOnAction(e -> Platform.exit());

        view.getChildren().addAll(startGameButton, preferencesButton, quitButton);
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-text-fill: #F0E68C; -fx-background-color: dimgrey;");
    }


    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Main Menu");
        // Configure the AppBar as needed
    }
}
