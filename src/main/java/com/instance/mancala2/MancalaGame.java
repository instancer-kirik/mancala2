package com.instance.mancala2;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

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
    public boolean isNewTurn = true;
    public boolean wasNewTurn = true;
    private int width = 700;
    private int height = 500;
    private final Scene scene;
    private final MancalaBoard board; // Existing board class

    MancalaBoardGroup mbg;
    GamePreferences preferences;
    GamePenalties gamePenalties;
    private final int playerCount=2; // Number of players

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    private String evidence="";
    public String getMoveLogs() {
        StringBuilder logs = new StringBuilder();
        for (Move move : moveStack) {
            logs.append(move.toString()).append("\n");
        }
        return logs.toString();
    }

    public String getTurnLog() {
        StringBuilder logs = new StringBuilder();
        for (Move move : turnStack) {
            logs.append(move.toString()).append("\n");
        }
        return logs.toString();
    }
    private enum TurnState { PICKING, PLACING }
    private TurnState turnState = TurnState.PICKING;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Stage getStage() {
        return stage;
    }

    private final Stage stage;

    private int currentPlayer; // Current player index
    private Stack<Move> moveStack;
    private Stack<Move> turnStack;//this just keeps the relevant actions on most recent turn
    public Player[] players;
    public MancalaGame(Stage stage) {
        this.stage=stage;
        preferences  = GamePreferences.getInstance();

        players= new Player[playerCount] ;
        for(int i=0;i<players.length;i++){
            players[i]= new Player("name "+i, this);
        }

        board = new MancalaBoard(14, playerCount,players);
        board.setPlayers(players);

        mbg= new MancalaBoardGroup(board,this);
        mbg.game=this;
        gamePenalties = new GamePenalties(board,preferences);
//mbp.getScene().getRoot() would probably return null
        scene = new Scene(mbg, width, height);
//game.players[game.getCurrentPlayer()].unsleeveStone();
        scene.setOnKeyPressed(event -> {
            if (event.isShiftDown()) {
                System.out.println("SHIFT, no click");
                if(players[currentPlayer].getHand()==0){
                    System.out.println("HAND, no stone");
                }else{
                // Add a stone to the current player's sleeve
                players[currentPlayer].storeStone();

                //this line returns to pickup phase when a player's hand empty
                if(players[currentPlayer].getHand()==0){resetTurnState();}

                mbg.updateUI(); // Update the UI to reflect the change
            }}
        if  (event.isControlDown()) {
                    System.out.println("CONTROL, no click");
                    if (players[currentPlayer].getSleeveStones() == 0) {
                        System.out.println("SLEEVE, no stone");
                        if (players[currentPlayer].getHand() == 0 ) {
                            resetTurnState();
                        }
                    } else {
                        // Add a stone to the current player's sleeve
                        players[currentPlayer].unsleeveStone();
                    }
                        //this line returns to pickup phase when a player's hand empty
            System.out.println(players[currentPlayer].getHand());
           mbg.updateUI(); // Update the UI to reflect the change
        }
        });




        moveStack = new Stack<>();
        turnStack = new Stack<>();


    }


    // Method to add a move to the stack
    public void addMove(ActionType actionType, int pitIndex) {

        if (wasNewTurn && (actionType ==ActionType.GRAB_STONES  || actionType ==ActionType.END_TURN) ){
            wasNewTurn=false;
            evidence="";
            // If the last action was END_TURN, clear turnStack for a new turn
            turnStack.clear();
        }
        Move move = new Move(actionType, pitIndex, currentPlayer);
        moveStack.push(move);
        turnStack.push(move);
    }
    public void addMove(ActionType actionType, int pitIndex, int player) {
        if (wasNewTurn && actionType ==ActionType.GRAB_STONES) {
            wasNewTurn=false;
            evidence="";
            // If the last action was END_TURN, clear turnStack for a new turn
            turnStack.clear();
        }
        Move move = new Move(actionType, pitIndex, player);
        moveStack.push(move);
        turnStack.push(move);
    }

    // Method to undo the last move
    public void undoLastMove() {
        if (!moveStack.isEmpty()) {
            Move lastMove = moveStack.pop();
            ActionType actionType = lastMove.actionType();
            int pitIndex = lastMove.pitIndex();

            // Undo the action based on its type
            switch (actionType) {
                case PLACE_STONE:
                    // Logic to undo placing a stone
                    break;
                case CAPTURE_STONES:
                    // Logic to undo capturing stones
                    break;
                // Add cases for other action types
            }
        }
    }

    public void endTurn() {
        System.out.println("END TURN LOG GAME");
        addMove(ActionType.END_TURN,-1);
        // Switch to the next player
        isNewTurn = true;
        wasNewTurn = true;
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
    public boolean checkTurnVerily(){
        return verifyMoveStack(turnStack);
    }
    public boolean checkGame(){
        return verifyMoveStack(moveStack);
    }
    public int getAccusingPlayerNumber() {
        Stack<Move> moves = (Stack<Move>) turnStack.clone();

        for (int i = moves.size() - 1; i >= 0; i--) {
            Move move = moves.get(i);
            // Now, process each move as needed, starting with the newest
            if (move.actionType() == ActionType.ACCUSE) {
                // Found the ACCUSE action, process accordingly
                return move.player();
            }
        }

        // Return -1 if no ACCUSE action is found, indicating no accusation was made
        return -1;
    }


    public boolean verifyMoveStack(Stack<Move> moves) {
        int lastPitIndex = -1; // Initialize to an impossible pit index for the first move
        int currentPlayer = moves.isEmpty() ? -1 : moves.peek().player();
        int lastPlayer = -1;
        int extra = 0;
        for (Move move : moves) {
            int currentPitIndex = move.pitIndex();

            switch (move.actionType()) {
                case GRAB_STONES:

                    lastPitIndex = move.pitIndex();
                    break;

                case PLACE_STONE:
                case CAPTURE_STONES:
                    if (currentPlayer == move.player()) {
                        if (!isPlacementSequenceValid(lastPitIndex, move.pitIndex(), move.player())) {
                            evidence+= "Invalid placement sequence detected: from " + lastPitIndex + " to " + move.pitIndex() + " by player " + move.player()+"\n";
                            return false;
                        }
                        lastPitIndex = move.pitIndex();
                    }
                    break;

                case END_TURN:
                    currentPlayer = (currentPlayer + 1) % 2; // Toggle current player
                    lastPitIndex = -1; // Reset for the new player's turn
                    break;
                case SUMMON_STONE, UNSLEEVE_STONE:
                    extra++;
                    break;
                case STORE_STONE:
                    extra--;
                    break;
                case CHEAT_ACTION:
                    evidence += move.toString();
                default:
                    // For other actions like CAST_SUMMON, ACCUSE, GET_ACCUSED, no sequence reset is needed
                    break;
            }
            lastPlayer = currentPlayer; // Update last player for next iteration
        }
        if(extra!=0){
            evidence+= Math.abs(extra)+" anomalous stones.";
            return false;
        }
        return true;
    }

    private boolean isPlacementSequenceValid(int lastPitIndex, int currentPitIndex, int player) {
        // Handling the sequence validation including moves on the opponent's side

        // Starting move validation
        if (lastPitIndex == -1) {
            // Player 0 starts in pits 0-5, Player 1 starts in pits 6-11
            return (player == 0 && currentPitIndex <= 5) ||
                    (player == 1 && currentPitIndex >= 6 && currentPitIndex <= 11);
        }

        // Sequence validation for player 0
        if (player == 0) {
            // Normal sequence in player 0's pits
            if (lastPitIndex <= 5 && currentPitIndex == lastPitIndex - 1) return true;
            // Move to Mancala
            if (lastPitIndex == 0 && currentPitIndex == 13) return true;
            // Move from Mancala to start of opponent's pits
            if (lastPitIndex == 13 && currentPitIndex == 6) return true;
            // Normal sequence in player 1's pits
            if ((lastPitIndex >= 6 && lastPitIndex < 11 && currentPitIndex == lastPitIndex + 1)) return true;
            // Wrap around to player 0's first pit
            if (lastPitIndex == 11 && currentPitIndex == 5) return true;

        }

        // Sequence validation for player 1
        if (player == 1) {
            // Normal sequence in player 1's pits
            if ((lastPitIndex >= 6 && lastPitIndex < 11 && currentPitIndex == lastPitIndex + 1))return true;
            // Move to Mancala
            if (lastPitIndex == 11 && currentPitIndex == 12) return true;
            // Move from Mancala to end of opponent's pits
            if (lastPitIndex == 12 && currentPitIndex == 5) return true;
            // Normal sequence in player 0's pits
            if (lastPitIndex <= 5 && currentPitIndex == lastPitIndex - 1) return true;
            // Wrap around to player 1's first pit
            if (lastPitIndex == 0 && currentPitIndex == 6) return true;
        }

        return false; // If none of the valid sequences are met
    }




    private void prepareForNextPlayer() {
        currentPlayer = (currentPlayer + 1) % playerCount;
        resetTurnState();
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

//
//    private void declareWinner() {
//        // Logic to declare the winner
//        // This could involve calculating scores and displaying the winner
//        int winner;
//        switch (board.whoHasMorePoints()) {
//            case 1:
//                System.out.println("Player 2 wins!!");
//                players[1].addWin();
//                break;
//            case -1:
//                System.out.println("TIE");
//                break;
//
//            case 0:
//                System.out.println("Player 1 wins!!");
//                players[0].addWin();
//                break;
//            default:
//                System.out.println("DEFAULT IN MANCALAGAMEDECLAREWINNER");
//        }
//
//
//
//    }
private void declareWinner() {
    // Logic to declare the winner...
    int winner = board.whoHasMorePoints();// Determine the winner...

            Platform.runLater(() -> {
                // Update UI on the JavaFX Application Thread
                EndGameScreen endGameScreen = new EndGameScreen(stage,preferences);
                Scene endGameScene = new Scene(endGameScreen.getView(), 800, 600);
                stage.setScene(endGameScene);
                stage.show();
            });
}
    // ... Additional methods for menu options, settings, etc.

}
