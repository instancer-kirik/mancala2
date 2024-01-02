package com.instance.mancala2;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static com.instance.mancala2.MancalaBoard.*;

public class MancalaBoardGroup extends Group {
    MancalaGame game;
    private final MancalaBoard board;
    //private int currentPlayer
    private final Node[] pitNodes;
    private final Node[] mancalas;
    private final Text[] stoneCountLabels;
    private int pitRadius =35;
    private double pitPadding = 2.2;//2 is touching sincs center pos *2r
    private double largerRadius=45;
    // New members to track the visual indicators for current player
    private Rectangle currentPlayerIndicatorP1;
    private Rectangle currentPlayerIndicatorP2;

    public MancalaBoardGroup(MancalaBoard board,MancalaGame game) {
        this.board = board;
        this.game = game;
        this.mancalas = new Node[game.players.length];
        // Create a background rectangle
        Rectangle background = new Rectangle();
        background.setWidth(700); // Set to your desired width
        background.setHeight(600); // Set to your desired height
        background.setFill(Color.DARKRED); // Set to your desired background color

        // Add the background to the group
        getChildren().add(background);
// Initialize and add the current player indicators
        currentPlayerIndicatorP1 = createCurrentPlayerIndicator(1);
        currentPlayerIndicatorP2 = createCurrentPlayerIndicator(2);

        getChildren().addAll(currentPlayerIndicatorP1, currentPlayerIndicatorP2);

        // Create and position Mancalas
        //Group mancala1 =  // Assuming index 0 for Player 1's Mancala
        mancalas[0] =createMancala(MANCALA1);
        mancalas[1] = createMancala(MANCALA2); // Assuming last index for Player 2's Mancala

        getChildren().addAll(mancalas[0], mancalas[1]);
        // Create and position pit nodes
        int pitNodesCt=board.getPitCount();
        pitNodes = new Node[pitNodesCt];
        for (int i = 0; i < pitNodes.length; i++) {
            pitNodes[i] = createPitNode(i);
            getChildren().add(pitNodes[i]);
        }


        // Create and position stone count labels
        stoneCountLabels = new Text[pitNodesCt];
        for (int i = 0; i < stoneCountLabels.length; i++) {
            stoneCountLabels[i] = createStoneCountLabel(i);
            getChildren().add(stoneCountLabels[i]);
        }

        // Update stone counts based on initial board state
        updateStoneCounts();
        updateCurrentPlayerIndicator();
    }

    // Method to create pit node (implement as needed)
    private Node createPitNode(int pitIndex) {
        System.out.print("-");
        // Create a circle to represent the pit
        Circle pit = new Circle();
        Group pitGroup = new Group();
        // Set the radius of the pit (you can adjust this value as needed)
        pit.setRadius(pitRadius);
        pit.setCenterX(0);
        pit.setCenterY(0);

        // Set the color of the pit
        pit.setFill(Color.SANDYBROWN);

        // Set the position of the pit (you'll need to calculate this based on the pitIndex)
        //double xPosition = calculateXPosition(pitIndex);
        //double yPosition = calculateYPosition(pitIndex);
        //pitGroup.SetLayoutX(xPosition);


        pitGroup.getChildren().add(pit);
        // Create a text label for the pit index
        Text indexLabel = new Text(String.valueOf(pitIndex));
        indexLabel.setFont(Font.font("Verdana", 14)); // Set font and size as needed
        indexLabel.setFill(Color.CORAL); // Set text color as needed

        // Position the label at the center of the pit
        indexLabel.setLayoutX(pit.getCenterX() - indexLabel.getBoundsInLocal().getWidth() / 2);
        indexLabel.setLayoutY(pit.getCenterY() + indexLabel.getBoundsInLocal().getHeight() / 4);

        // Add the label to the pit group
        pitGroup.getChildren().add(indexLabel);
        // Get the number of stones in this pit
        int stones = board.getStones(pitIndex);

        // Create and position stones within the pit
        for (int i = 0; i < stones; i++) {
            Circle stone = new Circle();
            stone.setRadius(5); // Small radius for stones
            stone.setFill(Color.BLACK); // Stone color

            // Calculate the position for each stone
            double angle = 2 * Math.PI / stones * i;
            double stoneX = Math.cos(angle) * 20; // 20 is the distance from the center of the pit
            double stoneY = Math.sin(angle) * 20;

            stone.setCenterX(stoneX);
            stone.setCenterY(stoneY);

            pitGroup.getChildren().add(stone);
        }
        pitGroup.setLayoutX(calculateXPosition(pitIndex));
        pitGroup.setLayoutY(calculateYPosition(pitIndex));

        // Optionally, you can add a stroke to the circle to make it more visible
        pit.setStroke(Color.BLACK);
        pitGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handlePitClick(pitIndex));
        pit.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            int stoness = board.getStones(pitIndex); // Assuming getStones method exists in MancalaBoard
            System.out.println("HOVERED ix: " + pitIndex + ", s: " + stoness);
        });
        return pitGroup;
    }

    private double calculateXPosition(int pitIndex) {
        // Calculate and return the X position for the pit based on its index
        // This is just a placeholder logic. You should adjust it as per your layout
       // return 50 + (pitIndex % 6) * pitPadding * pitRadius+20; // Example layout
        int indexInRow = pitIndex % BOARDLENGTH;
        return 50 + indexInRow * pitPadding * pitRadius + 20; // Example layout

    }

    private double calculateYPosition(int pitIndex) {
        // Calculate and return the Y position for the pit based on its index
        // This is just a placeholder logic. You should adjust it as per your layout
        return pitIndex < BOARDLENGTH ? 100 : 200; // Two rows of pits as an example
    }

    // Method to create stone count label (implement as needed)

    private Text createStoneCountLabel(int pitIndex) {
        Text label = new Text("V "+pitIndex);
        // Set label properties (font, color, alignment)
        label.setFont(Font.font(20));
        label.setFill(Color.BLACK);
        label.setTextAlignment(TextAlignment.CENTER);

        // Position the label relative to the corresponding pit node
        // ... Adapt this based on your actual pit node implementation
        label.setLayoutX(pitNodes[pitIndex].getLayoutX() + pitNodes[pitIndex].getBoundsInLocal().getWidth() / 2);
        label.setLayoutY(pitNodes[pitIndex].getLayoutY() + pitNodes[pitIndex].getBoundsInLocal().getHeight() / 2);

        return label;
    }
    // Method to update stone counts (implement as needed)

    public Node[] getPitNodes() {
        return pitNodes;
    }
    public void updateStoneCounts() {
        for (int i = 0; i < stoneCountLabels.length; i++) {
            stoneCountLabels[i].setText(String.valueOf(i)+" "+String.valueOf(board.getStones(i)));
        }
    }

    public void updateUI() {
        // Update small pits
        for (int i = 0; i < board.getPitCount(); i++) {
            updatePitUI(i);
        }

        // Update Mancalas
        updateMancalaUI(0);
        updateMancalaUI(1);

        // Update stone counts and current player indicator
        updateStoneCounts();
        updateCurrentPlayerIndicator();
    }




    public void updatePitUI(int i) {
//updates small pits

            Group pitGroup = (Group) pitNodes[i];
            pitGroup.getChildren().clear(); // Clear the existing children (stones)

            // Re-create the pit itself

            Circle pit = new Circle();
            pit.setRadius(pitRadius);
            pit.setFill(Color.SANDYBROWN);
            pit.setStroke(Color.BLACK);
            pitGroup.getChildren().add(pit);

            // Get the updated number of stones in this pit
            int stones = board.getStones(i);

            // Re-create and position stones within the pit
            for (int j = 0; j < stones; j++) {
                Circle stone = new Circle();
                stone.setRadius(5); // Small radius for stones
                stone.setFill(Color.BLACK); // Stone color

                // Calculate the position for each stone
                double angle = 2 * Math.PI / stones * j;
                double stoneX = Math.cos(angle) * 20; // 20 is the distance from the center of the pit
                double stoneY = Math.sin(angle) * 20;

                stone.setCenterX(stoneX);
                stone.setCenterY(stoneY);

                pitGroup.getChildren().add(stone);
            }
            //createPitNode(i);

 //      createMancala(int index);
//        //handle mancalas ui

    }
      //  updateStoneCounts();
      private void updateMancalaUI(int mancalaIndex) {//this takes 0,1
          Group mancalaGroup = (Group) mancalas[mancalaIndex];
          Circle mancalaCircle = (Circle) mancalaGroup.getChildren().get(0); // Assuming the first child is the Mancala Circle

          // Clear existing stones

          if (mancalaGroup.getChildren().size() > 1) {
              mancalaGroup.getChildren().remove(1, mancalaGroup.getChildren().size());
          }

          int stones = board.getStones(mancalaIndex == 0 ? MANCALA1 : MANCALA2);
          double radiusOffset = largerRadius - 10; // Adjust this value as needed

          for (int i = 0; i < stones; i++) {
              Circle stone = new Circle();
              stone.setRadius(5); // Small radius for stones
              stone.setFill(Color.BLACK); // Stone color

              double angle = 2 * Math.PI / stones * i;
              double stoneX = mancalaCircle.getLayoutX() + radiusOffset * Math.cos(angle);
              double stoneY = mancalaCircle.getLayoutY() + radiusOffset * Math.sin(angle);

              stone.setLayoutX(stoneX);
              stone.setLayoutY(stoneY);

              mancalaGroup.getChildren().add(stone);
          }
      }

    private Group createMancala(int index) {
        Group pitGroup = new Group();

        // Create the Mancala Circle
        Circle mancala = new Circle();
        mancala.setRadius(largerRadius); // Set a larger radius for Mancalas
        mancala.setFill(Color.GOLDENROD); // Different color for Mancala
        mancala.setLayoutX(calculateMancalaPositionX(index));
        mancala.setLayoutY(calculateMancalaPositionY(index));

        // Add Mancala to the group
        pitGroup.getChildren().add(mancala);
        pitGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleClickMancala(index));

        // Get the number of stones in this pit
        int stones = board.getStones(index);

        // Create and position stones within the pit
        for (int i = 0; i < stones; i++) {
            Circle stone = new Circle();
            stone.setRadius(5); // Small radius for stones
            stone.setFill(Color.BLACK); // Stone color

            // Calculate the position for each stone
            double angle = 2 * Math.PI / stones * i;
            double stoneX = mancala.getLayoutX() + Math.cos(angle) * 20; // 20 is the distance from the center of the pit
            double stoneY = mancala.getLayoutY() + Math.sin(angle) * 20;

            stone.setCenterX(stoneX);
            stone.setCenterY(stoneY);

            // Add each stone to the group
            pitGroup.getChildren().add(stone);
        }

        // Return the group containing the Mancala and its stones
        return pitGroup;
    }


    private double calculateMancalaPositionX(int index) {
        // Assuming Mancalas are at the ends of the board
        if (index == MANCALA1) { // First Mancala
            return pitRadius + 10; // 10 is a margin from the edge
        } else { // Last Mancala
            return game.getWidth() - pitRadius - 10; // Assuming 'width' is the width of the board
        }
    }

    private double calculateMancalaPositionY(int index) {
        // Centered vertically
        return (double) game.getHeight() / 2; // Assuming 'height' is the height of the board
    }
// Assuming this is part of a class that has access to 'board', 'game', and 'updateUI'

    /**
     * Handles a click event on a pit.
     * @param pitIndex The index of the pit that was clicked.
     */
    public void handlePitClick(int pitIndex) {
        int pitStones = board.getStones(pitIndex);
        System.out.println("CLICKED Index: " + pitIndex + ", Stones: " + pitStones);

        if (game.isPlacingPhase()) {
            System.out.println("DROP phase " + game.players[game.getCurrentPlayer()].getHand() + " STONES");
            if (game.players[game.getCurrentPlayer()].getHand() >= 1) {
                board.placeStone(pitIndex, game.getCurrentPlayer());
                game.players[game.getCurrentPlayer()].drop(1);

                if (game.players[game.getCurrentPlayer()].getHand() == 0) {
                    processLastStonePlacement(pitIndex, pitStones);
                }
            }
        } else {
            System.out.println("TAKE phase");
            if (board.isPitValidAndBelongsToCurrentPlayer(pitIndex, game.getCurrentPlayer())) {
                game.players[game.getCurrentPlayer()].addStonesToHand(board.take(pitIndex));
                game.initiateStonePlacement();
            }
        }

        updateUI();
    }

    private void processLastStonePlacement(int pitIndex, int pitStones) {
        System.out.println("PROCESSING last stone");
        if (board.isPitPlayerMancala(pitIndex, game.getCurrentPlayer())) {
            // Player gets another turn, no state reset
            game.resetTurnState();
            System.out.println( "Player gets another turn, no state reset");
        } else if (pitStones == 0 && board.isPitOnCurrentPlayerSide(pitIndex, game.getCurrentPlayer())) {
            int capturedStones = board.captureOppositeStones(pitIndex);
            //add these to mancala ui or base mancala's on score
            board.addCapturedStonesToMancala(capturedStones, game.getCurrentPlayer());
            game.players[game.getCurrentPlayer()].addScore(capturedStones);
            game.endTurn();

        } else if (pitStones>0 && board.isPitEnemySide(pitIndex, game.getCurrentPlayer())) {
            game.players[game.getCurrentPlayer()].addStonesToHand(board.take(pitIndex));
            game.initiateStonePlacement();
        } else if (pitStones>0 && board.isPitOnCurrentPlayerSide(pitIndex, game.getCurrentPlayer())) {
            game.players[game.getCurrentPlayer()].addStonesToHand(board.take(pitIndex));
            game.initiateStonePlacement();
        } else if (pitStones==0 && board.isPitEnemySide(pitIndex, game.getCurrentPlayer())) {
            game.endTurn();


        } else {
            System.out.println("UNCAPTURED_STATE");
            game.resetTurnState();
            game.endTurn();
            updateCurrentPlayerIndicator();
        }
    }
    /**
     * Handles a click event on a pit in the Mancala game.
     * @param pitIndex The index of the pit that was clicked.
     */
    public void handleClickMancala(int pitIndex) {
        // Check if the clicked Mancala belongs to the current player
        if (!isMancalaOfCurrentPlayer(pitIndex)) {
            System.out.println("Clicked Mancala does not belong to the current player.");
            return; // Ignore clicks on the opponent's Mancala
        }

        int pitStones = board.getStones(pitIndex);
        System.out.println("CLK MANCALA " + pitIndex + "stones: " + pitStones);
// Checking if it's currently the placing phase or taking phase
        if (game.isPlacingPhase()) {
            handlePlacingPhase(pitIndex);
        } else {
            handleTakingPhase(pitIndex);
        }

        // Update the UI after handling the click
        updateUI();
    }
    private boolean isMancalaOfCurrentPlayer(int pitIndex) {
        // Check if the pitIndex corresponds to the current player's Mancala
        // You need to implement this logic based on how you determine the Mancala for each player
        // For example:
        return (pitIndex == MANCALA1 && game.getCurrentPlayer() == 0) ||
                (pitIndex == MANCALA2 && game.getCurrentPlayer() == 1);
    }
    private void handlePlacingPhase(int pitIndex) {
        int pitStones = board.getStones(pitIndex);
        System.out.println("DROP phase " + game.players[game.getCurrentPlayer()].getHand() + " STONES");

        if (game.players[game.getCurrentPlayer()].getHand() >= 1) {
            board.placeStone(pitIndex, game.getCurrentPlayer());
            game.players[game.getCurrentPlayer()].drop(1);

            if (game.players[game.getCurrentPlayer()].getHand() == 0) {
//                if (board.isPitPlayerMancala(pitIndex, game.getCurrentPlayer())) {
//                    // If so, the current player gets another turn
//                    // (Adjust this logic based on your game's rules)
//                    // For example, you might not change the current player or reset the turn state
//                    //game.resetTurnState();
//                } else {
                    // Otherwise, end the turn and switch to the next player
                    processLastStonePlacement(pitIndex, pitStones);
                    //game.resetTurnState();
                    //game.endTurn();
                    //updateCurrentPlayerIndicator();
                   // game.switchToNextPlayer();

            }
        }
    }

    private void handleTakingPhase(int pitIndex) {
        System.out.println("TAKE phase");
        if (board.isPitValidAndBelongsToCurrentPlayer(pitIndex, game.getCurrentPlayer())) {
            game.players[game.getCurrentPlayer()].addStonesToHand(board.take(pitIndex));
            game.initiateStonePlacement();
        }
    }



    // Method to create a visual indicator for the current player
    private Rectangle createCurrentPlayerIndicator(int player) {
        Rectangle indicator = new Rectangle();
        indicator.setWidth(700); // Adjust size as needed
        indicator.setHeight(50); // Adjust size as needed
        indicator.setFill(Color.TRANSPARENT);
        indicator.setStroke(player == 1 ? Color.BLUE : Color.RED); // Different color for each player
        indicator.setStrokeWidth(5);
        indicator.setVisible(false); // Initially hidden

        // Position the indicator (assuming the top of the board for Player 1, bottom for Player 2)
        indicator.setY(player == 1 ? 0 : 550); // Adjust position as needed

        return indicator;
    }

    // Method to update the visual indicator based on the current player
    public void updateCurrentPlayerIndicator() {
        int currentPlayer = game.getCurrentPlayer();
        currentPlayerIndicatorP1.setVisible(currentPlayer == 0); // Assuming player 1 is index 0
        currentPlayerIndicatorP2.setVisible(currentPlayer == 1); // Assuming player 2 is index 1
    }
}

