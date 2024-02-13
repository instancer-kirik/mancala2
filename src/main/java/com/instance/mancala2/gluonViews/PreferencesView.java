package com.instance.mancala2.gluonViews;

import com.gluonhq.charm.glisten.mvc.View;
import com.instance.mancala2.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreferencesView extends View {
    private Stage stage;
    private final GamePreferences preferences;

    public PreferencesView() {
        super();
        this.preferences = GamePreferences.getInstance();
//        // Initialize your layout within the constructor
//        VBox view = new VBox(10);
//        view.setAlignment(Pos.CENTER);
//        view.setStyle("-fx-background-color: black;");
//        setupMenu(view);
//        // Set the VBox as the center of this View
//        this.setCenter(view);
    }

    public Parent getView() {

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setStyle("-fx-background-color: black;");

        // Set a general style for labels and combo boxes to have white text
        String labelStyle = "-fx-text-fill: #F0E68C;";
        String comboBoxStyle = "-fx-background-color: darkgrey; -fx-text-fill: #F0E68C;"; // Darker background for ComboBoxes


        // Cheat Phrase
        Label cheatPhraseLabel = new Label("Cheat Phrase:");
        cheatPhraseLabel.setStyle(labelStyle);
        ComboBox<CheatPhrase> cheatPhraseComboBox = new ComboBox<>();

        cheatPhraseComboBox.getItems().setAll(CheatPhrase.values());
        cheatPhraseComboBox.setValue(preferences.getCheatPhrase());
        //cheatPhraseComboBox.setStyle(comboBoxStyle);
// Increase the visible row count to show more options at once
        cheatPhraseComboBox.setVisibleRowCount(10);

// Apply CSS styles directly to make the scrollbar darker
        cheatPhraseComboBox.setStyle(
                comboBoxStyle + "-fx-control-inner-background: #696969; " + // Attempt to set the dropdown background
                        "-fx-accent: #666666; " // Set the selection color in the dropdown

        );
        layout.add(cheatPhraseLabel, 0, 0);
        layout.add(cheatPhraseComboBox, 1, 0);

        // Penalty Amount
        Label penaltyAmountLabel = new Label("Penalty Amount:");
        penaltyAmountLabel.setStyle(labelStyle);
        ComboBox<PenaltyAmount> penaltyAmountComboBox = new ComboBox<>();
        penaltyAmountComboBox.getItems().setAll(PenaltyAmount.values());
        penaltyAmountComboBox.setValue(preferences.getPenaltyAmount());
        penaltyAmountComboBox.setStyle(comboBoxStyle);
        layout.add(penaltyAmountLabel, 0, 1);
        layout.add(penaltyAmountComboBox, 1, 1);

        // Penalty Strategy
        Label penaltyStrategyLabel = new Label("Penalty Strategy:");
        penaltyStrategyLabel.setStyle(labelStyle);
        ComboBox<PenaltyStrategy> penaltyStrategyComboBox = new ComboBox<>();
        penaltyStrategyComboBox.getItems().setAll(PenaltyStrategy.values());
        penaltyStrategyComboBox.setValue(preferences.getPenaltyStrategy());
        penaltyStrategyComboBox.setStyle(comboBoxStyle);
        layout.add(penaltyStrategyLabel, 0, 2);
        layout.add(penaltyStrategyComboBox, 1, 2);

        // Continue After Empty Hand
        Label continueAfterEmptyHandLabel = new Label("Continue After Empty Hand (AutoPickup):");
        continueAfterEmptyHandLabel.setStyle(labelStyle);
        CheckBox continueAfterEmptyHandCheckBox = new CheckBox();
        continueAfterEmptyHandCheckBox.setSelected(preferences.isContinueAfterEmptyHand_AutoPickup());
        continueAfterEmptyHandCheckBox.setStyle(comboBoxStyle);
        layout.add(continueAfterEmptyHandLabel, 0, 3); // Adjust row index as needed
        layout.add(continueAfterEmptyHandCheckBox, 1, 3); // Adjust row index as needed
// Steal Opposite Stones
        Label stealOppositeLabel = new Label("Steal Opposite Stones:");
        stealOppositeLabel.setStyle(labelStyle);
        CheckBox stealOppositeCheckBox = new CheckBox();
        stealOppositeCheckBox.setStyle(comboBoxStyle);
        stealOppositeCheckBox.setSelected(preferences.isStealOpposite());
        layout.add(stealOppositeLabel, 0, 4); // Adjust row index as needed
        layout.add(stealOppositeCheckBox, 1, 4); // Adjust row index as needed

// Capture Placed Stone Also
        Label capturePlacedAlsoLabel = new Label("Also Capture Placed Stone:");
        capturePlacedAlsoLabel.setStyle(labelStyle);
        CheckBox capturePlacedAlsoCheckBox = new CheckBox();
        capturePlacedAlsoCheckBox.setSelected(preferences.isCapturePlacedAlso());
        capturePlacedAlsoCheckBox.setStyle(comboBoxStyle);
        layout.add(capturePlacedAlsoLabel, 0, 5); // Adjust row index as needed
        layout.add(capturePlacedAlsoCheckBox, 1, 5); // Adjust row index as needed
        // Steal Opposite Stones
        Label altWoodLabel = new Label("Alternate wood texture:");
        altWoodLabel.setStyle(labelStyle);
        CheckBox altWoodCheckBox = new CheckBox();
        altWoodCheckBox.setStyle(comboBoxStyle);
        altWoodCheckBox.setSelected(preferences.isAltWoodTexture());
        layout.add(altWoodLabel, 0, 6); // Adjust row index as needed
        layout.add(altWoodCheckBox, 1, 6); // Adjust row index as needed

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #556B2F; -fx-text-fill: white;"); // Dark Olive Green button with white text
        saveButton.setOnAction(e -> {
            preferences.setCheatPhrase(cheatPhraseComboBox.getValue());
            preferences.setPenaltyAmount(penaltyAmountComboBox.getValue());
            preferences.setPenaltyStrategy(penaltyStrategyComboBox.getValue());
            preferences.setContinueAfterEmptyHand_AutoPickup(continueAfterEmptyHandCheckBox.isSelected());
            preferences.setStealOpposite(stealOppositeCheckBox.isSelected());
            preferences.setCapturePlacedAlso(capturePlacedAlsoCheckBox.isSelected());
            preferences.setAltWoodTexture(altWoodCheckBox.isSelected());
            MainMenu.navigateToMainMenu(stage);
        });
        layout.add(saveButton, 2, 6);

        return layout;
    }
    // Define the Runnable for navigating to the main menu

    public void show() {
        Scene scene = new Scene(getView(), 530, 230);
        stage.setScene(scene);
        stage.show();
    }
}
