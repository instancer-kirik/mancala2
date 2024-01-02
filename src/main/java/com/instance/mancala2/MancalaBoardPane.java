package com.instance.mancala2;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MancalaBoardPane extends Pane {

    private final MancalaBoard board;

    public MancalaBoardPane(MancalaBoard board) {

        this.board = board;
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

    // ... Additional methods for game logic interactions (e.g., highlighting selected pits)
}
