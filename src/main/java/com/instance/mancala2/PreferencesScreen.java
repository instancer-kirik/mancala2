package com.instance.mancala2;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PreferencesScreen {
    private Stage stage;
    private final GamePreferences preferences;

    public PreferencesScreen(Stage stage) {
        this.stage = stage;
        this.preferences = GamePreferences.getInstance();
    }

    public Parent getView() {
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        // Cheat Phrase
        Label cheatPhraseLabel = new Label("Cheat Phrase:");
        ComboBox<CheatPhrase> cheatPhraseComboBox = new ComboBox<>();
        cheatPhraseComboBox.getItems().setAll(CheatPhrase.values());
        cheatPhraseComboBox.setValue(preferences.getCheatPhrase());
        layout.add(cheatPhraseLabel, 0, 0);
        layout.add(cheatPhraseComboBox, 1, 0);

        // Penalty Amount
        Label penaltyAmountLabel = new Label("Penalty Amount:");
        ComboBox<PenaltyAmount> penaltyAmountComboBox = new ComboBox<>();
        penaltyAmountComboBox.getItems().setAll(PenaltyAmount.values());
        penaltyAmountComboBox.setValue(preferences.getPenaltyAmount());
        layout.add(penaltyAmountLabel, 0, 1);
        layout.add(penaltyAmountComboBox, 1, 1);

        // Penalty Strategy
        Label penaltyStrategyLabel = new Label("Penalty Strategy:");
        ComboBox<PenaltyStrategy> penaltyStrategyComboBox = new ComboBox<>();
        penaltyStrategyComboBox.getItems().setAll(PenaltyStrategy.values());
        penaltyStrategyComboBox.setValue(preferences.getPenaltyStrategy());
        layout.add(penaltyStrategyLabel, 0, 2);
        layout.add(penaltyStrategyComboBox, 1, 2);

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            preferences.setCheatPhrase(cheatPhraseComboBox.getValue());
            preferences.setPenaltyAmount(penaltyAmountComboBox.getValue());
            preferences.setPenaltyStrategy(penaltyStrategyComboBox.getValue());
            MainMenu.navigateToMainMenu(stage);
        });
        layout.add(saveButton, 1, 3);

        return layout;
    }
    // Define the Runnable for navigating to the main menu

    public void show() {
        Scene scene = new Scene(getView(), 400, 200);
        stage.setScene(scene);
        stage.show();
    }
}
