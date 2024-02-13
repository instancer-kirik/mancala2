package com.instance.mancala2.gluonViews;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;

public class GameView extends View {

    public GameView() {
        MancalaGameComponent gameComponent = new MancalaGameComponent();
        this.setCenter(gameComponent.getView()); // Add the game component as the central content of the view
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Mancala Game");
        // Additional AppBar configuration as needed
    }
}
