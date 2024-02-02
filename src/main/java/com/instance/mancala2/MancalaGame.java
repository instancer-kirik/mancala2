package com.instance.mancala2;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class MancalaGame extends GameApplication {
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width = 700;
    private int height = 500;
    private Scene scene;
    private final MancalaBoard board; // Existing board class
    MancalaBoardPane mbp;
    MancalaBoardGroup mbg;

    private final int playerCount=2; // Number of players



    private enum TurnState { PICKING, PLACING }
    private TurnState turnState = TurnState.PICKING;

    public int getCurrentPlayer() {
        return currentPlayer;
    }



    private int currentPlayer; // Current player index

    public Player[] players;
    public MancalaGame() {
        players= new Player[playerCount] ;
        for(int i=0;i<players.length;i++){
            players[i]= new Player("name "+i);
        }

        board = new MancalaBoard(14, playerCount,players);
        board.setPlayers(players);
        mbg= new MancalaBoardGroup(board,this);
        mbg.game=this;
//mbp.getScene().getRoot() would probably return null
        scene = new Scene(mbg, width, height);
//OLD SHIFT IMPL; NO CLICK
        scene.setOnKeyPressed(event -> {
            if (event.isShiftDown()) {
                System.out.println("SHIFT, no click");
                // Add a stone to the current player's sleeve
                players[currentPlayer].storeStone();
                if(players[currentPlayer].getHand()==0){resetTurnState();}
                mbg.updateUI(); // Update the UI to reflect the change
            }
        });



    }

    public void endTurn() {
        System.out.println("END TURN LOG GAME");
        // Switch to the next player

        // Check if the game is over
        if (board.isGameOver(currentPlayer)) {

            // Handle the end of the game, such as declaring a winner
            declareWinner();
            // Optionally, reset the game or provide options for a new game
        } else {
            // Prepare for the next player's turn
            prepareForNextPlayer();
        }

        // Update the UI to reflect the new state
        //updateUI();
    }



    private void prepareForNextPlayer() {
        currentPlayer = (currentPlayer + 1) % playerCount;
        resetTurnState();
    }


    private void captureStones(int pitIndex) {
        System.out.println("MANCALAGAME CAPTURESTONES");
        int capturedStones = board.captureOppositeStones(pitIndex);
        players[currentPlayer].addScore(capturedStones);
    }



    //@Override
    public void stop() {
        // Perform any necessary cleanup or resource release
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {

//        for (int i = 0; i < board.getPitCount(); i++) {
//            final int pitIndex = i; // Capture i in a final variable
//            pitNodes[i].setOnMouseClicked(event -> playTurn(pitIndex));
//        }
        // Set game title and icon
//        setTitle();
//        setIcon("mancala_icon.png"); // Replace with your icon

        // Initialize board state and visual elements

        // ... Create visual representations of pits (Rectangle, Circle, etc.)
        // ... Add event listeners for pit clicks (using lambda expressions)

        // Initialize current player and display information
        currentPlayer = 0;
        // ... Display current player name or indicator on the scene
       // MancalaBoardPane mbp =new MancalaBoardPane(board);
      //  scene = new Scene(mbp);
        //MOVED TO CONSTRUCTOR


    }

    public Scene getScene() {
        return scene;
    }


    public void initiateStonePlacement() {
        turnState = TurnState.PLACING;
    }

    public boolean isPlacingPhase() {
        return turnState == TurnState.PLACING;
    }

    public void resetTurnState() {
        turnState = TurnState.PICKING;
        if (board.isGameOver(currentPlayer)) {
            // Handle the end of the game, such as declaring a winner
            declareWinner();
        }
    }
    private void declareWinner() {
        // Logic to declare the winner
        // This could involve calculating scores and displaying the winner
        int winner;
        switch (board.whoHasMorePoints()) {
            case 1:
                System.out.println("Player 2 wins!!");
                players[1].addWin();
                break;
            case -1:
                System.out.println("TIE");
                break;

            case 0:
                System.out.println("Player 1 wins!!");
                players[0].addWin();
                break;
            default:
                System.out.println("DEFAULT IN MANCALAGAMEDECLAREWINNER");
        }



    }
    // ... Additional methods for menu options, settings, etc.

}
