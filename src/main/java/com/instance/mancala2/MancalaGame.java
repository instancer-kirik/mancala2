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

    private int width = 600;
private int height = 700;
    private Scene scene;
    private final MancalaBoard board; // Existing board class
    MancalaBoardPane mbp;
    MancalaBoardGroup mbg;
    Node[] pitNodes;
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

        scene.setOnKeyPressed(event -> {
            if (event.isShiftDown()) {
                System.out.println("SHIFT");
                // Add a stone to the current player's sleeve
                players[currentPlayer].storeStone();
                mbg.updateUI(); // Update the UI to reflect the change
            }
        });

         pitNodes= mbg.getPitNodes();

// Add event listeners for pit clicks

    }

    public void endTurn() {
        // Switch to the next player
        currentPlayer = (currentPlayer + 1) % playerCount;
        resetTurnState();
        // Check if the game is over
        if (board.isGameOver()) {
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

    private void declareWinner() {
        // Logic to declare the winner
        // This could involve calculating scores and displaying the winner
        System.out.println(players[currentPlayer].getName()+" WON!");
    }

    private void prepareForNextPlayer() {
        // Any logic that needs to be executed before the next player's turn starts
        // For example, resetting certain game state variables
    }
    private void onPitClicked(int pitIndex) {
        // Placing stones in subsequent pits
        if (players[currentPlayer].getHand() > 0) {

            players[currentPlayer].drop(1);
            boolean wasEmpty = board.placeStone(pitIndex, currentPlayer);
            //is current playerindex needed in this call??


            if (players[currentPlayer].getHand() == 0) {
                if (wasEmpty && board.isPitOnCurrentPlayerSide(pitIndex, currentPlayer)) {
                    // Handle capturing of stones if last stone lands in an empty pit
                    captureStones(pitIndex);
                }
                finishTurn();
            }
        }
    }

    private void captureStones(int pitIndex) {
        System.out.println("MANCALAGAME CAPTURESTONES");
        int capturedStones = board.captureOppositeStones(pitIndex);
        players[currentPlayer].addScore(capturedStones);
    }

    private void finishTurn() {
        // Switch players, check for game end, update UI
    }
    public void playTurn(int pitIndex) {
        System.out.println("PLAYTURN AT "+pitIndex);
        if (!board.isValidPit(pitIndex)) return; // Ignore invalid clicks
        players[currentPlayer].addStonesToHand(board.take(pitIndex));
        // Initial picking of stones-
        if (board.isPitValidAndBelongsToCurrentPlayer(pitIndex, currentPlayer)) {
            players[currentPlayer].addStonesToHand(board.take(pitIndex));
        }

        while (players[currentPlayer].getHand()!=0){
        //listens for pitClicks to deposit
            //listens for shift to store

        }
        System.out.println("EMPTY HAND IN PLAYTURN");
        // Implement game logic based on Mancala rules
        //board.sowFromPit(pitIndex, currentPlayer);
        // ... Check for captures, ending conditions, etc.

        // Update UI based on stone movements and captures
        // ... Update visual elements of pits based on changed stone counts
        // ... Use animations or sound effects if desired

        // Switch player and display information
        currentPlayer = (currentPlayer + 1) % playerCount;
        board.setCurrentPlayerIndex(currentPlayer);
        // ... Display updated player information on the scene

        // Check for game end and handle accordingly
        if (board.isGameOver()) {
            // ... Display winner or draw message
        }
    }

    //@Override
    public void stop() {
        // Perform any necessary cleanup or resource release
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {

        for (int i = 0; i < board.getPitCount(); i++) {
            final int pitIndex = i; // Capture i in a final variable
            pitNodes[i].setOnMouseClicked(event -> playTurn(pitIndex));
        }
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
    }

    // ... Additional methods for menu options, settings, etc.

}
