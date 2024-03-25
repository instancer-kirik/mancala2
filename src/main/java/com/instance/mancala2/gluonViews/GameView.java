package com.instance.mancala2.gluonViews;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GameView extends View {

    public GameView() {
        MancalaGameComponent gameComponent = new MancalaGameComponent();

        BackgroundFill backgroundFill = new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
        this.setCenter(gameComponent.getView()); // Add the game component as the central content of the view

    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Mancala Game");
        // Additional AppBar configuration as needed
    }

}
