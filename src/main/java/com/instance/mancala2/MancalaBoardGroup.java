package com.instance.mancala2;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static com.instance.mancala2.MancalaBoard.*;
import static javafx.beans.binding.Bindings.when;

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
    private double BOARDX=600;
    private double BOARDY=400;
    private final double leftMargin = 60; // Example value, adjust as needed
    private final double rightMargin = 10; // Example value, adjust as needed
    // Define dimensions for the Mancala
    double mancalaWidth = 100; // Example width, adjust as needed
    double mancalaHeight = 250; // Example height, adjust as needed
    double cornerRadius = 100; // Corner radius for rounded edges
    private Group currentPlayerIndicatorP1;
    private Group currentPlayerIndicatorP2;
    private Text handEmptyIndicator;
    private Rectangle handEmptyBackground;
    public MancalaBoardGroup(MancalaBoard board,MancalaGame game) {
        this.board = board;
        this.game = game;
        this.mancalas = new Node[game.players.length];
        Image woodImage = new Image(getClass().getResourceAsStream("/images/wood_texture.jpg"));
        ImageView woodImageView = new ImageView(woodImage);

        woodImageView.setFitWidth(700); // Adjust to your desired width
        woodImageView.setFitHeight(600); // Adjust to your desired height
        getChildren().add(woodImageView);

        // Create and position Mancalas
        //Group mancala1 =  // Assuming index 0 for Player 1's Mancala
        mancalas[0] =createMancala(MANCALA1);
        mancalas[1] = createMancala(MANCALA2); // Assuming last index for Player 2's Mancala
        currentPlayerIndicatorP1 = createCurrentPlayerIndicator(1);
        currentPlayerIndicatorP2 = createCurrentPlayerIndicator(2);
        getChildren().addAll(currentPlayerIndicatorP1, currentPlayerIndicatorP2);

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
        Path arrowPath = createDisjointPath();
        getChildren().add(arrowPath);

        Group arrowHeads = createArrowHeadsGroup(120, 405, 9); // Adjust parameters as needed

        // Add the arrowheads group to the board group
        getChildren().add(arrowHeads);
        handEmptyBackground = new Rectangle();
        handEmptyBackground.setFill(new Color(0, 0, 0, 0.5)); // Semi-transparent black
        handEmptyBackground.setArcWidth(10); // Rounded corners
        handEmptyBackground.setArcHeight(10);
        handEmptyBackground.setVisible(false); // Initially hidden
        // Initialize the hand empty indicator
        handEmptyIndicator = new Text("Hand Empty");
        handEmptyIndicator.setFont(new Font("Verdana", 24));
        handEmptyIndicator.setFill(Color.YELLOW); // Example color
        handEmptyIndicator.setVisible(false); // Initially hidden
        handEmptyIndicator.setEffect(new DropShadow(5, Color.YELLOW));
        // Position the indicator in the middle of the board
        handEmptyIndicator.setX(282); // Assuming the board width is 700
        handEmptyIndicator.setY(260); // Assuming the board height is 600

        getChildren().add(handEmptyBackground);
        getChildren().add(handEmptyIndicator);

        // Update stone counts based on initial board state
        updateStoneCounts();
        updateCurrentPlayerIndicator();
        updateUI();
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

        // Create a RadialGradient to simulate depth
        RadialGradient gradient = new RadialGradient(
                0, 0.0, // Focus angle and focus distance
                0.5, 0.5, // Center X and Y of the circle
                1, // Radius of the circle
                true, // Proportional to the circle size
                CycleMethod.NO_CYCLE, // No cycle method
                new Stop(0, Color.TRANSPARENT), // Transparent at the edges
                new Stop(1, Color.rgb(0, 0, 0, 0.2)) // Gradient stops, dark color at the center

        );
        // Set the color of the pit
        pit.setFill(gradient);

//        // Add a more pronounced InnerShadow effect
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(10.0); // Increase the radius for a larger shadow
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0,0.8)); // Fully opaque black for stronger shadow
        innerShadow.setOffsetX(0); // Center the shadow
        innerShadow.setOffsetY(0); // Center the shadow
// Add DropShadow effect to the inner pit
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.889)); // Semi-transparent black shadow
        dropShadow.setRadius(3.0); // Radius of the shadow
        dropShadow.setOffsetX(0.0); // Horizontal offset of the shadow
        dropShadow.setOffsetY(0.0); // Vertical offset of the shadow

        pit.setEffect(dropShadow);

        pit.setEffect(innerShadow);


        pitGroup.getChildren().addAll(pit);
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
        // Set up detection for drag gestures
        final double[] startPosition = new double[2];
        pitGroup.setOnMousePressed(event -> {
            if(event.isAltDown()){
                System.out.println("CHEAT2 ALT HELD");
            }
            // Store the start position
            startPosition[0] = event.getSceneX();
            startPosition[1] = event.getSceneY();
            pitGroup.setUserData(Boolean.FALSE); // Reset the drag detected flag
        });

        pitGroup.setOnMouseDragged(event -> {
            // Calculate the drag distance
            // Set a threshold for the drag distance to consider it an intentional drag
                pitGroup.setUserData(Boolean.TRUE); // Mark as a drag

        });
        pitGroup.setOnMouseReleased(event -> {
            double dragDistance = Math.sqrt(Math.pow(event.getSceneX() - startPosition[0], 2) + Math.pow(event.getSceneY() - startPosition[1], 2));
            if (dragDistance > 21) { // Threshold value can be adjusted

                cheatAction(pitIndex);

            }
        });
        return pitGroup;
    }

    private double calculateXPosition(int pitIndex) {
        // Calculate and return the X position for the pit based on its index
        // This is just a placeholder logic. You should adjust it as per your layout
       // return 50 + (pitIndex % 6) * pitPadding * pitRadius+20; // Example layout
        int indexInRow = pitIndex % BOARDLENGTH;
        double additionalSpacing=0;
        double extraSpaceBetweenPits=0;

        double startingX = leftMargin + mancalaWidth + additionalSpacing; // Increase starting X position


        return startingX + indexInRow * (pitPadding * pitRadius + extraSpaceBetweenPits);

    }

    private double calculateYPosition(int pitIndex) {
        // Calculate and return the Y position for the pit based on its index
        // This is just a placeholder logic. You should adjust it as per your layout
        return pitIndex < BOARDLENGTH ? 200 : 300; // Two rows of pits as an example
    }

    private Group createMancala(int index) {
        Group pitGroup = new Group();

        // Create the Mancala Circle

        // Create the Mancala Rectangle
        Rectangle mancala = new Rectangle(mancalaWidth, mancalaHeight);
        mancala.setArcWidth(cornerRadius);
        mancala.setArcHeight(cornerRadius);

        RadialGradient gradient = new RadialGradient(
                0, 0.0, // Focus angle and focus distance
                0.5, 0.5, // Center X and Y of the circle
                1, // Radius of the circle
                true, // Proportional to the circle size
                CycleMethod.NO_CYCLE, // No cycle method
                new Stop(0, Color.TRANSPARENT), // Transparent at the edges
                new Stop(1, Color.rgb(0, 0, 0, 0.2)) // Gradient stops, dark color at the center

        );
        mancala.setFill(gradient); // Different color for Mancala
        mancala.setLayoutX(calculateMancalaPositionX(index));
        mancala.setLayoutY(calculateMancalaPositionY(index));
        mancala.setStroke(Color.BLACK);
        mancala.setStrokeWidth(1);
        // Add Mancala to the group
        pitGroup.getChildren().add(mancala);
        pitGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleClickMancala(index));

        // Get the number of stones in this pit
        int stones = board.getStones(index);
        // Return the group containing the Mancala and its stones
        return pitGroup;
    }

    private double calculateMancalaPositionX(int index) {
        // Assuming Mancalas are at the ends of the board
        if (index == MANCALA1) {
            return leftMargin- mancalaWidth / 2; // Position for the first Mancala
        } else { // MANCALA2
            return BOARDX - rightMargin; // Position for the second Mancala
        }
    }

    private double calculateMancalaPositionY(int index) {
        // Centered vertically
        double upperRowY = calculateYPosition(0); // Y position of the upper row of pits
        double lowerRowY = calculateYPosition(BOARDLENGTH - 1); // Y position of the lower row of pits

        // Center the Mancala vertically between the two rows
        return (upperRowY + lowerRowY) / 2 - mancalaHeight / 4;// Assuming 'height' is the height of the board
    }
// Assuming this is part of a class that has access to 'board', 'game', and 'updateUI'
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
        updateHandEmptyIndicator();
    }

    //updates small pits
    public void updatePitUI(int i) {


        Group pitGroup = (Group) pitNodes[i];
        pitGroup.getChildren().clear(); // Clear the existing children (stones)

        // Re-create the pit itself

        Circle pit = new Circle();
        pit.setRadius(pitRadius);

        pit.setStroke(Color.BLACK);
        RadialGradient gradient = new RadialGradient(
                0, 0.0, // Focus angle and focus distance
                0.5, 0.5, // Center X and Y of the circle
                1, // Radius of the circle
                true, // Proportional to the circle size
                CycleMethod.NO_CYCLE, // No cycle method
                new Stop(0, Color.TRANSPARENT), // Transparent at the edges
                new Stop(1, Color.rgb(0, 0, 0, 0.2)) // Gradient stops, dark color at the center

        );
        // Set the color of the pit
        pit.setFill(gradient);
//        // Add a more pronounced InnerShadow effect
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(pitRadius / 2); // Increase the radius for a larger shadow
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0,0.8)); // Fully opaque black for stronger shadow
        innerShadow.setOffsetX(0); // Center the shadow
        innerShadow.setOffsetY(0); // Center the shadow

        pit.setEffect(innerShadow);

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
    }
    private Node updateMancalaUI(int mancalaIndex) {
        Group mancalaGroup = (Group) mancalas[mancalaIndex];
        Rectangle mancalaRectangle = (Rectangle) mancalaGroup.getChildren().get(0); // Now treating it as a Rectangle

        // Clear existing stones
        if (mancalaGroup.getChildren().size() > 1) {
            mancalaGroup.getChildren().remove(1, mancalaGroup.getChildren().size());
        }

        int stones = board.getStones(mancalaIndex == 0 ? MANCALA1 : MANCALA2);

        double centerX = mancalaRectangle.getLayoutX() + mancalaWidth / 2;
        double centerY = mancalaRectangle.getLayoutY() + mancalaHeight / 2;

        // Adjust the way you calculate the position of the stones
        double mancalaWidth = mancalaRectangle.getWidth();
        double mancalaHeight = mancalaRectangle.getHeight();

        for (int i = 0; i < stones; i++) {
            Circle stone = new Circle();
            stone.setRadius(5); // Small radius for stones
            stone.setFill(Color.BLACK); // Stone color


            // Calculate position for each stone relative to center
            double angle = 2 * Math.PI / stones * i;
            double stoneX = centerX + Math.cos(angle) * (mancalaWidth / 4); // Adjust radius as needed
            double stoneY = centerY + Math.sin(angle) * (mancalaHeight / 4); // Adjust radius as needed

            stone.setLayoutX(stoneX);
            stone.setLayoutY(stoneY);

            mancalaGroup.getChildren().add(stone);
        }
        return mancalaGroup;
    }

    // Method to create a visual indicator for the current player
    private Group createCurrentPlayerIndicator(int player) {
        Group indicators = new Group();
        // Create the mindicator Rectangle
        Rectangle mindicator = new Rectangle(mancalaWidth + 20, mancalaHeight + 20);
        mindicator.setArcWidth(cornerRadius + 10);
        mindicator.setArcHeight(cornerRadius + 10);
        mindicator.setStroke(player == 1 ? Color.BLUE : Color.RED);
        mindicator.setStrokeWidth(5);
        mindicator.setFill(Color.TRANSPARENT);
        mindicator.setLayoutX(-10);
        mindicator.setLayoutY(-10);
        mindicator.setX(player == 1 ? 0 : 424);
        Rectangle mancala= (Rectangle) ((Group) mancalas[player-1]).getChildren().get(0);

       // Align the mindicator with the Mancala
        mindicator.setLayoutX(mancala.getLayoutX() - 10 ); // Offset to surround the Mancala
        mindicator.setLayoutY(mancala.getLayoutY() - 10);


        Rectangle indicator = new Rectangle();
        indicator.setWidth(700); // Adjust size as needed
        indicator.setHeight(100); // Adjust size as needed
        indicator.setFill(Color.TRANSPARENT);
        indicator.setStroke(player == 1 ? Color.BLUE : Color.RED); // Different color for each player
        indicator.setStrokeWidth(5);
        indicator.setVisible(true); // Initially hidden

        // Position the indicator (assuming the top of the board for Player 1, bottom for Player 2)
        indicator.setY(player == 1 ? 0 : 424); // Adjust position as needed
        indicators.getChildren().addAll(indicator,mindicator);
        return indicators;
    }

    // Method to update the visual indicator based on the current player
    public void updateCurrentPlayerIndicator() {
        int currentPlayer = game.getCurrentPlayer();
        //currentPlayerIndicatorP1.getChildren().get(0).setVisible(currentPlayer == 0);
        currentPlayerIndicatorP1.setVisible(currentPlayer == 0); // Assuming player 1 is index 0
        currentPlayerIndicatorP2.setVisible(currentPlayer == 1); // Assuming player 2 is index 1
    }

    private Polygon createArrowHead(double x, double y, boolean isRight) {
        Polygon arrowHead = new Polygon();
        double size = 10; // Size of the arrowhead

        if (isRight) {
            // Pointing to the right
            arrowHead.getPoints().addAll(x - size, y - size, x - size, y + size, x + size, y);
        } else {
            // Pointing to the left
            arrowHead.getPoints().addAll(x + size, y - size, x + size, y + size, x - size, y);
        }

        arrowHead.setFill(Color.BLACK);

        return arrowHead;
    }

    private Path createDisjointPath() {
        Path path = new Path();

        // Define the top line
        MoveTo moveToTopStart = new MoveTo(); // Starting point of the top line
        moveToTopStart.setX(120); // Adjust as needed
        moveToTopStart.setY(120);

        LineTo lineToTopEnd = new LineTo(); // Ending point of the top line
        lineToTopEnd.setX(580); // Adjust as needed
        lineToTopEnd.setY(120);

        // Add the top line to the path
        path.getElements().add(moveToTopStart);
        path.getElements().add(lineToTopEnd);

        // Define the bottom line
        MoveTo moveToBottomStart = new MoveTo(); // Starting point of the bottom line
        moveToBottomStart.setX(120); // Adjust as needed
        moveToBottomStart.setY(405);

        LineTo lineToBottomEnd = new LineTo(); // Ending point of the bottom line
        lineToBottomEnd.setX(580); // Adjust as needed
        lineToBottomEnd.setY(405);

        // Since PathElements cannot be reused, we need to add new instances for the bottom line
        path.getElements().add(new MoveTo(moveToBottomStart.getX(), moveToBottomStart.getY()));
        path.getElements().add(new LineTo(lineToBottomEnd.getX(), lineToBottomEnd.getY()));

        // Customize the path's appearance
        path.setStrokeWidth(2);
        path.setStroke(Color.BLACK);
        path.setFill(null); // No fill for disjoint path

        return path;
    }

    private Group createArrowHeadsGroup(double topY, double bottomY, int numberOfArrows) {
        Group arrowHeadsGroup = new Group();
        double startX = 50; // Start X coordinate
        double endX = 650; // End X coordinate
        double spacing = (endX - startX) / (numberOfArrows + 1);

        for (int i = 1; i <= numberOfArrows; i++) {
            double x = startX + i * spacing;

            // Top line arrowheads pointing left for counter-clockwise movement
            Polygon topArrow = createArrowHead(x, topY, false);
            arrowHeadsGroup.getChildren().add(topArrow);

            // Bottom line arrowheads pointing right for counter-clockwise movement
            Polygon bottomArrow = createArrowHead(x, bottomY, true);
            arrowHeadsGroup.getChildren().add(bottomArrow);
        }

        return arrowHeadsGroup;
    }
    public void updateHandEmptyIndicator() {
        int currentPlayer = game.getCurrentPlayer();

        if (game.players[currentPlayer].getHand() == 0) {
            handEmptyIndicator.setVisible(true);
            handEmptyBackground.setVisible(true);

            // Update the position and size of the background rectangle
            double padding = -4; // Reduced padding for a tighter fit
            Bounds textBounds = handEmptyIndicator.getBoundsInLocal();
            handEmptyBackground.setWidth(textBounds.getWidth() + padding * 2);
            handEmptyBackground.setHeight(textBounds.getHeight() + padding * 2);

            // Position the background centered on the text
            handEmptyBackground.setX(handEmptyIndicator.getX());//- padding - textBounds.getWidth() / 2);
            handEmptyBackground.setY(handEmptyIndicator.getY()-textBounds.getHeight()+14); //- textBounds.getHeight() - padding);

            // Adjust the orientation based on the current player
            if (currentPlayer == 0) {
                handEmptyIndicator.setRotate(180); // Facing player 1
            } else {
                handEmptyIndicator.setRotate(0); // Rotate to face player 2
            }
        } else {
            handEmptyIndicator.setVisible(false);
            handEmptyBackground.setVisible(false);
        }

    }

    private void cheatAction(int pitIndex) {
        // Check if cheating is allowed based on the game's state and rules
        if (canCheat(pitIndex)) {
            System.out.println("Cheating at pit " + pitIndex);
            // Perform the cheat action, e.g., adding a stone
            //board.addStoneToPit(pitIndex, 1); // Adjust this action as needed
            updateUI();
        } else {
            System.out.println("Cheating attempt not allowed.");
        }
    }

    private boolean canCheat(int pitIndex) {
        // Implement logic to determine if a cheat action is valid
        // Example: Check if it's the player's turn and the game phase allows it
        return game.isPlacingPhase() && board.isValidPit(pitIndex);
    }
}

