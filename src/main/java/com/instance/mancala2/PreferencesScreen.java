package com.instance.mancala2;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        // Continue After Empty Hand
        Label continueAfterEmptyHandLabel = new Label("Continue After Empty Hand (AutoPickup):");
        CheckBox continueAfterEmptyHandCheckBox = new CheckBox();
        continueAfterEmptyHandCheckBox.setSelected(preferences.isContinueAfterEmptyHand_AutoPickup());
        layout.add(continueAfterEmptyHandLabel, 0, 3); // Adjust row index as needed
        layout.add(continueAfterEmptyHandCheckBox, 1, 3); // Adjust row index as needed
// Steal Opposite Stones
        Label stealOppositeLabel = new Label("Steal Opposite Stones:");
        CheckBox stealOppositeCheckBox = new CheckBox();
        stealOppositeCheckBox.setSelected(preferences.isStealOpposite());
        layout.add(stealOppositeLabel, 0, 4); // Adjust row index as needed
        layout.add(stealOppositeCheckBox, 1, 4); // Adjust row index as needed

// Capture Placed Stone Also
        Label capturePlacedAlsoLabel = new Label("Also Capture Placed Stone:");
        CheckBox capturePlacedAlsoCheckBox = new CheckBox();
        capturePlacedAlsoCheckBox.setSelected(preferences.isCapturePlacedAlso());
        layout.add(capturePlacedAlsoLabel, 0, 5); // Adjust row index as needed
        layout.add(capturePlacedAlsoCheckBox, 1, 5); // Adjust row index as needed
        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            preferences.setCheatPhrase(cheatPhraseComboBox.getValue());
            preferences.setPenaltyAmount(penaltyAmountComboBox.getValue());
            preferences.setPenaltyStrategy(penaltyStrategyComboBox.getValue());
            preferences.setContinueAfterEmptyHand_AutoPickup(continueAfterEmptyHandCheckBox.isSelected());
            preferences.setStealOpposite(stealOppositeCheckBox.isSelected());
            preferences.setCapturePlacedAlso(capturePlacedAlsoCheckBox.isSelected());
            MainMenu.navigateToMainMenu(stage);
        });
        layout.add(saveButton, 2, 5);

        return layout;
    }
    // Define the Runnable for navigating to the main menu

    public void show() {
        Scene scene = new Scene(getView(), 530, 200);
        stage.setScene(scene);
        stage.show();
    }
}
