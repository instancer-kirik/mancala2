package com.instance.mancala2;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class MancalaBoard {

    private static final int DEFAULT_PIT_COUNT = 14; // Adjust as needed

    public static final int MANCALA1 = DEFAULT_PIT_COUNT - 1; // Index of player 1's mancala
    public static final int MANCALA2 = DEFAULT_PIT_COUNT - 2; // Index of player 2's mancala
    public static int BOARDLENGTH;
    private final int pitCount; // Total number of pits
    private static int playerCount; // Number of players
    private final int[] pits; // Array to store stone counts in each pit
    private int pitStartQuantity=3;
    private Player[] players; // Array of players

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }


    public MancalaBoard(int pitCount, int playerCount,Player[] players) {
        this.pitCount = pitCount;
        MancalaBoard.playerCount = playerCount;
        this.pits = new int[pitCount]; // Initialize pits with 0 stones
        for(int i=0;i<this.pits.length-2;i++){//-2 makes so it doesn't prime the mancalas
            this.pits[i]=pitStartQuantity;
        }
        BOARDLENGTH=(DEFAULT_PIT_COUNT-2)/playerCount;// this is just num of pits on one side
        // Implement any additional setup logic (initial stone placement, etc.)
    }


    public int getPitCount() {
        return pits.length-2;
    }


    public int getStones(int pitIndex) {
        if (pitIndex < 0 || pitIndex >= pits.length) {
            throw new IndexOutOfBoundsException("Invalid pit index: " + pitIndex);
        }
        return pits[pitIndex];
    }

    public boolean isValidPit(int pitIndex) {
        if (pitIndex < 0 || pitIndex >= pits.length) {
            return false;
        }
        if (pits[pitIndex]==0) return false;
        return pitIndex != MANCALA1 && pitIndex != MANCALA2; // Excluding mancala pits
    }
//this is the automated one, maybe use later
//    public void sowFromPit(int pitIndex, int player) {
//        if (!isValidPit(pitIndex)) return;
//
//        int stones = pits[pitIndex]; // Save stones for later update
//        pits[pitIndex] = 0; // Empty the clicked pit
//
//        int nextPitIndex = (pitIndex + 1) % pits.length;
//        while (stones > 0) {
//            if (nextPitIndex == pitIndex || nextPitIndex == MANCALA1 || nextPitIndex == MANCALA2) { // Skip opponent's mancala and clicked pit
//                nextPitIndex++;
//                continue;
//            }
//
//            pits[nextPitIndex]++; // Sow one stone
//            stones--;
//
//            if (stones == 1 && nextPitIndex == player * 6) { // Capture rule
//                pits[MANCALA1 + player]++; // Capture stone in own mancala
//                stones = 0; // Stop sowing after capturing
//            }
//
//            nextPitIndex++; // Move to next pit
//        }
//    }

    public boolean placeStone(int pitIndex, int currentPlayer){

        boolean wasEmpty;
        wasEmpty= pits[pitIndex] == 0;
        pits[pitIndex]++;
        return wasEmpty;

    }
    public int take(int pitIndex) {
        if (!isValidPit(pitIndex)) return 0;

        int stones = pits[pitIndex]; // Save stones for later update
        pits[pitIndex] = 0; // Empty the clicked pit
        return stones;


    }
    public Boolean isGameOver(int currentPlayer) {
        if(currentPlayer==0){
            return isPlayer1NoMove();
        }else{
            return isPlayer2NoMove();
        }
    }
    public Boolean isPlayer1NoMove(){
        Boolean flag = true;
        //checks if one side has no stones in any pits
        for (int i = 0; i < getPitCount() / 2; i++) {

            if (pits[i] == 0) {

            } else {
                flag = false;//there is still a valid turn for player 1
            }
        }
        return flag;
    }
    public Boolean isPlayer2NoMove(){
        Boolean flag = true;
        //checks if one side has no stones in any pits
        System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}"+getPitCount());
        for (int i = getPitCount() / 2; i < getPitCount(); i++) {

            if (pits[i] == 0) {

            } else {
                flag = false;//there is still a valid turn for player 2
            }
        }
        return flag;
    }
    public boolean isPitOnCurrentPlayerSide(int pitIndex, int currentPlayer) {
        // Calculate the range of pits for the current player
        int startPit = currentPlayer * BOARDLENGTH;
        int endPit = startPit + BOARDLENGTH - 1;

        return pitIndex >= startPit && pitIndex <= endPit;
    }

    public int captureOppositeStones(int pitIndex) {
        // Calculate the index of the opposite pit
        int oppositePitIndex;

        // For pits on the first player's side
        if (pitIndex >= 0 && pitIndex < BOARDLENGTH) {
            oppositePitIndex = BOARDLENGTH + pitIndex;
        }
        // For pits on the second player's side
        else if (pitIndex >= BOARDLENGTH && pitIndex < 2 * BOARDLENGTH) {
            oppositePitIndex = pitIndex - BOARDLENGTH;
        } else {
            // Invalid pit index
            throw new IllegalArgumentException("Invalid pit index: " + pitIndex);
        }

        // Get the number of stones in the opposite pit
        int capturedStones = pits[oppositePitIndex];

        // Remove the stones from the opposite pit
        pits[oppositePitIndex] = 0;


        // Return the number of captured stones
        return capturedStones;
    }

    public boolean isPitValidAndBelongsToCurrentPlayer(int pitIndex, int currentPlayer) {
        // Check if the pit is valid and if it belongs to the current player's side
        return isValidPit(pitIndex) && isPitOnCurrentPlayerSide(pitIndex, currentPlayer);
    }

    public boolean isPitEnemySide(int pitIndex, int currentPlayer) {
        // Calculate the range of pits for the enemy player
        int enemyStartPit = (1 - currentPlayer) * BOARDLENGTH;
        int enemyEndPit = enemyStartPit + BOARDLENGTH - 1;

        return pitIndex >= enemyStartPit && pitIndex <= enemyEndPit;
    }

    public boolean isPitPlayerMancala(int pitIndex, int currentPlayer) {
        if (currentPlayer == 0) {
            return pitIndex == MANCALA1;
        } else {
            return pitIndex == MANCALA2;
        }
    }
    // ... Additional methods for specific board functionalities (e.g., initial setup, player scores)
    public void addCapturedStonesToMancala(int capturedStones, int currentPlayer) {
        // Determine the index of the current player's Mancala
        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;

        // Add captured stones to the player's Mancala
        for (int i = 0; i < capturedStones; i++) {
            placeStone(mancalaIndex, currentPlayer);
        }
    }
    public int whoHasMorePoints(){
        if(pits[MANCALA1]==pits[MANCALA2]) return -1; //tie
        else if(pits[MANCALA1]>pits[MANCALA2]) return 0;//player 1 wins
        else{ return 1;} //player 2 wins
    }

    private void removeStones(int pitIndex, int stonesToRemove) {
        if (pitIndex >= 0 && pitIndex < pits.length && stonesToRemove >= 0) {
            pits[pitIndex] = Math.max(0, pits[pitIndex] - stonesToRemove); // Ensure not to go below 0
        }
    }
    // Penalty 1: Score Fractionally Reduced
    public void penaltyScoreReduced(int currentPlayer, double fraction, MancalaBoardGroup mbg) {
        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
        int totalScore = getStones(mancalaIndex);
        int scoreToKeep = (int) (totalScore * (1 - fraction));
        int scoreToDistribute = totalScore - scoreToKeep; // Ensures any rounding extra is included

        // Update Mancala score
        pits[mancalaIndex] = scoreToKeep;

        // Any specific action for distributed score can be added here if needed
    }
//As this method ventures beyond my understanding, I will comment my code
//    // Penalty 2: Fractionally Reduced and Add to Random Pits (Excluding Mancalas)
//    public void penaltyFractionScoreToRandomPits(int currentPlayer, double fraction, MancalaBoardGroup mbg) {
//        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
//        int totalScore = getStones(mancalaIndex);
//        int scoreToKeep = (int) (totalScore * (1 - fraction));
//        int scoreToDistribute = totalScore - scoreToKeep; // Include extra from rounding
//
//        pits[mancalaIndex] = scoreToKeep;
//
//        // Distribute the score
//        Random random = new Random();
//        while (scoreToDistribute > 0) {
//            int randomPitIndex = random.nextInt(pits.length - 2); // Avoid Mancalas
//            if (randomPitIndex != MANCALA1 && randomPitIndex != MANCALA2) {
//                pits[randomPitIndex]++;
//                scoreToDistribute--;
//
//                uiUpdater.update();
//            }
//        }
//    }
/////////////////////////////V2 with pop in/out animations
//    public void penaltyFractionScoreToRandomPits(int currentPlayer, double fraction, MancalaBoardGroup mbg) {
//        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
//        AtomicInteger mancalaScore = new AtomicInteger(getStones(mancalaIndex));
//        AtomicInteger scoreToDistribute = new AtomicInteger((int) (mancalaScore.get() * fraction)); // Use AtomicInteger
//
//        // Method to recursively distribute stones with a delay
//        Runnable distributeOneStone = () -> {
//            if (scoreToDistribute.get() > 0) {
//                PauseTransition delay = new PauseTransition(Duration.seconds(0.3)); // Adjust delay as needed
//                delay.setOnFinished(event -> {
//                    int randomPitIndex = new Random().nextInt(pits.length - 2); // Exclude Mancalas
//                    if (randomPitIndex != MANCALA1 && randomPitIndex != MANCALA2) {
//                        placeStone(randomPitIndex, currentPlayer); // Place a stone in a random pit
//                        mancalaScore.decrementAndGet(); // Decrement the Mancala's total score
//                        scoreToDistribute.decrementAndGet(); // Decrement the stones left to distribute
//                        setStones(mancalaIndex, mancalaScore.get()); // Update the Mancala's stone count
//
//                        uiUpdater.update(); // Call the UI update function
//
//                        if (scoreToDistribute.get() > 0) {
//                            delay.playFromStart(); // Continue the delay for the next distribution
//                        }
//                    }
//                });
//                delay.play(); // Start the first delay
//            }
//        };
//
//        distributeOneStone.run(); // Initiate the stone distribution process
////    }
//private void distributeStones(int currentPlayer, AtomicInteger mancalaScore, AtomicInteger scoreToDistribute, MancalaBoardGroup mancalaBoardGroup, int mancalaIndex) {
//    if (scoreToDistribute.get() > 0) {
//        // This ensures that the operation is performed on the JavaFX Application Thread
//        Platform.runLater(() -> {
//            // Since we need finalRandomPitIndex to be effectively final, it's recalculated or used here directly if it does not change
//            Random random = new Random();
//            int randomPitIndex = random.nextInt(pitCount - 2); // Avoid Mancalas
//            if (randomPitIndex >= currentPlayer * (pitCount / 2)) {
//                // Adjust index to skip Mancala pits
//                randomPitIndex += 2;
//            }
//
//            // Ensure the index is within bounds after adjustment
//            final int finalRandomPitIndex = Math.min(randomPitIndex, pitCount - 3);
//
//            Node mancalaNode = mancalaBoardGroup.getNodeForPit(mancalaIndex);
//            Node targetPitNode = mancalaBoardGroup.getNodeForPit(finalRandomPitIndex);
//
//            Circle movingStone = new Circle(5, Color.BLACK); // Visual representation of a stone
//
//            Bounds startBounds = mancalaNode.localToScene(mancalaNode.getBoundsInLocal());
//            Bounds endBounds = targetPitNode.localToScene(targetPitNode.getBoundsInLocal());
//
//            movingStone.setLayoutX(startBounds.getMinX());
//            movingStone.setLayoutY(startBounds.getMinY());
//
//            mancalaBoardGroup.getChildren().add(movingStone); // Add stone to the scene
//
//            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), movingStone);
//            transition.setToX(endBounds.getMinX() - startBounds.getMinX());
//            transition.setToY(endBounds.getMinY() - startBounds.getMinY());
//
//            transition.setOnFinished(event -> {
//                mancalaBoardGroup.getChildren().remove(movingStone); // Remove the stone from the scene after animation
//
//                // Now you can safely decrement and update because finalRandomPitIndex is not modified after its assignment
//                mancalaScore.decrementAndGet();
//                scoreToDistribute.decrementAndGet();
//                placeStone(finalRandomPitIndex, currentPlayer); // Simulate placing a stone
//
//                setStones(mancalaIndex, mancalaScore.get()); // Update Mancala's stone count
//
//                mancalaBoardGroup.updateUI(); // Refresh UI
//
//                if (scoreToDistribute.get() > 0) {
//                    distributeStones(currentPlayer, mancalaScore, scoreToDistribute, mancalaBoardGroup, mancalaIndex); // Recursive call for next stone
//                }
//            });
//
//            transition.play(); // Start the animation
//        });
//    }
//}
//    private void distributeStones(int currentPlayer, AtomicInteger mancalaScore, AtomicInteger scoreToDistribute, MancalaBoardGroup mancalaBoardGroup, int mancalaIndex) {
//        if (scoreToDistribute.get() > 0) {
//            Platform.runLater(() -> {
//                Random random = new Random();
//                int randomPitIndex = random.nextInt(pitCount - 2); // Avoid Mancalas
//                if (randomPitIndex >= currentPlayer * (pitCount / 2)) {
//                    randomPitIndex += 2; // Adjust index to skip Mancala pits
//                }
//                final int finalRandomPitIndex = Math.min(randomPitIndex, pitCount - 3);
//
//                Node mancalaNode = mancalaBoardGroup.getNodeForPit(mancalaIndex);
//                Node targetPitNode = mancalaBoardGroup.getNodeForPit(finalRandomPitIndex);
//
//                // Calculate centers of the starting and ending nodes
//                Bounds startBounds = mancalaNode.localToScene(mancalaNode.getBoundsInLocal());
//                Bounds endBounds = targetPitNode.localToScene(targetPitNode.getBoundsInLocal());
//
//                double startX = startBounds.getMinX() + (startBounds.getWidth() / 2);
//                double startY = startBounds.getMinY() + (startBounds.getHeight() / 2);
//
//                double endX = endBounds.getMinX() + (endBounds.getWidth() / 2);
//                double endY = endBounds.getMinY() + (endBounds.getHeight() / 2);
//
//                Circle movingStone = new Circle(5, Color.BLACK); // Visual representation of a stone
//                movingStone.setLayoutX(startX);
//                movingStone.setLayoutY(startY);
//
//                mancalaBoardGroup.getChildren().add(movingStone); // Add stone to the scene
//
//                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), movingStone);
//                transition.setToX(endX - startX);
//                transition.setToY(endY - startY);
//
//                transition.setOnFinished(event -> {
//                    mancalaBoardGroup.getChildren().remove(movingStone); // Remove the stone from the scene after animation
//                    mancalaScore.decrementAndGet();
//                    scoreToDistribute.decrementAndGet();
//                    placeStone(finalRandomPitIndex, currentPlayer); // Simulate placing a stone
//                    setStones(mancalaIndex, mancalaScore.get()); // Update Mancala's stone count
//                    mancalaBoardGroup.updateUI(); // Refresh UI
//
//                    if (scoreToDistribute.get() > 0) {
//                        distributeStones(currentPlayer, mancalaScore, scoreToDistribute, mancalaBoardGroup, mancalaIndex); // Recursive call for next stone
//                    }
//                });
//
//                transition.play(); // Start the animation
//            });
//        }
//    }
void distributeStones(int currentPlayer, AtomicInteger mancalaScore, AtomicInteger scoreToDistribute, MancalaBoardGroup mancalaBoardGroup, int mancalaIndex) {
    if (scoreToDistribute.get() > 0) {
        Platform.runLater(() -> {
            Random random = new Random();
            int randomPitIndex = random.nextInt(pitCount - 2); // Avoid Mancalas
            if (randomPitIndex >= currentPlayer * (pitCount / 2)) {
                randomPitIndex += 2; // Skip Mancala pits
            }
            final int finalRandomPitIndex = Math.min(randomPitIndex, pitCount- 3);

            Node mancalaNode = mancalaBoardGroup.getNodeForPit(mancalaIndex);
            Node targetPitNode = mancalaBoardGroup.getNodeForPit(finalRandomPitIndex);

            List<Node> stones = ((Group) mancalaNode).getChildren().stream()
                    .filter(node -> node instanceof Circle)
                    .collect(Collectors.toList());

            if (!stones.isEmpty()) {
                Circle stoneInMancala = (Circle) stones.get(0); // Use the first stone for simplicity

                Bounds startBounds = stoneInMancala.localToScene(stoneInMancala.getBoundsInLocal());
                Bounds endBounds = targetPitNode.localToScene(targetPitNode.getBoundsInLocal());

                double startX = startBounds.getCenterX();
                double startY = startBounds.getCenterY();
                double endX = endBounds.getCenterX() - startX;
                double endY = endBounds.getCenterY() - startY;

                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), stoneInMancala); // Slowed down to 1 second
                transition.setToX(endX);
                transition.setToY(endY);

                transition.setOnFinished(event -> {
                    // After animation finishes, update game state and UI
                    mancalaScore.decrementAndGet();
                    scoreToDistribute.decrementAndGet();
                    placeStone(finalRandomPitIndex, currentPlayer); // Or directly manipulate pits array if needed
                    setStones(mancalaIndex, mancalaScore.get());

                    // Remove the animated stone and update UI
                    ((Group) mancalaNode).getChildren().remove(stoneInMancala);
                    mancalaBoardGroup.updateUI();

                    // Continue distribution if there are stones left to distribute
                    if (scoreToDistribute.get() > 0) {
                        distributeStones(currentPlayer, mancalaScore, scoreToDistribute, mancalaBoardGroup, mancalaIndex);
                    }
                });

                transition.play(); // Start the animation
            }
        });
    }
}



    public void penaltyFractionScoreToRandomPits(int currentPlayer, double fraction, MancalaBoardGroup mancalaBoardGroup) {
        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
        AtomicInteger mancalaScore = new AtomicInteger(getStones(mancalaIndex));
        AtomicInteger scoreToDistribute = new AtomicInteger((int) (mancalaScore.get() * fraction));

        distributeStones(currentPlayer, mancalaScore, scoreToDistribute, mancalaBoardGroup, mancalaIndex);
    }




//    public void penaltyFractionScoreToRandomPits(int currentPlayer, double fraction, MancalaBoardGroup mancalaBoardGroup) {
//    int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
//    AtomicInteger mancalaScore = new AtomicInteger(getStones(mancalaIndex));
//    AtomicInteger scoreToDistribute = new AtomicInteger((int) (mancalaScore.get() * fraction));
//
//    Runnable distributeOneStone = () -> {
//        if (scoreToDistribute.get() > 0) {
//            int randomPitIndex = new Random().nextInt(pitCount); // Adjust method call if necessary
//            if (randomPitIndex != MANCALA1 && randomPitIndex != MANCALA2) { // Adjust check if necessary
//
//                // Retrieve nodes for animation
//                Node mancalaNode = mancalaBoardGroup.getNodeForPit(mancalaIndex);
//                Node targetPitNode = mancalaBoardGroup.getNodeForPit(randomPitIndex);
//
//                // Create a visual representation of a moving stone (could be a Circle node)
//                Circle movingStone = new Circle(5, Color.BLACK); // Example stone
//
//                // Calculate start and end positions for the animation
//                Bounds startBounds = mancalaNode.localToScene(mancalaNode.getBoundsInLocal());
//                Bounds endBounds = targetPitNode.localToScene(targetPitNode.getBoundsInLocal());
//
//                // Position the stone at the start position
//                movingStone.setLayoutX(startBounds.getMinX());
//                movingStone.setLayoutY(startBounds.getMinY());
//
//                // Add the stone to the scene (add to the parent node)
//                mancalaBoardGroup.getChildren().add(movingStone);
//
//                // Create and configure the translation animation
//                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), movingStone);
//                transition.setToX(endBounds.getMinX() - startBounds.getMinX());
//                transition.setToY(endBounds.getMinY() - startBounds.getMinY());
//                transition.setOnFinished(event -> {
//                    // Update logic after animation finishes
//                    placeStone(randomPitIndex, currentPlayer);
//                    mancalaScore.decrementAndGet();
//                    scoreToDistribute.decrementAndGet();
//                    setStones(mancalaIndex, mancalaScore.get());
//                    mancalaBoardGroup.getChildren().remove(movingStone); // Remove the stone from the scene
//
//                    mancalaBoardGroup.updateUI(); // Refresh UI
//
//                    if (scoreToDistribute.get() > 0) {
//                        distributeOneStone.run(); // Continue distribution
//                    }
//                });
//
//                transition.play(); // Start the animation
//            }
//        }
//    };
//
//    distributeOneStone.run(); // Initiate the stone distribution process
//}
//
//



    private void setStones(int index, int scoreToKeep) {
        pits[index]=scoreToKeep;
    }

    // Penalty 3: Fractionally Reduced and Add to A Single Random Pit (Excluding Mancalas)
    public void penaltyFractionScoreToARandomPit(int currentPlayer, double fraction, MancalaBoardGroup mbg) {
        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
        int totalScore = getStones(mancalaIndex);
        int scoreToKeep = (int) (totalScore * (1 - fraction));
        int scoreToDistribute = totalScore - scoreToKeep; // Include extra from rounding

        pits[mancalaIndex] = scoreToKeep;

        // Distribute the score to a single random pit
        Random random = new Random();
        int randomPitIndex = random.nextInt(pits.length - 2); // Avoid Mancalas
        while (randomPitIndex == MANCALA1 || randomPitIndex == MANCALA2) {
            randomPitIndex = random.nextInt(pits.length - 2);
        }
        pits[randomPitIndex] += scoreToDistribute;
    }

    // Penalty 4: Fractionally Reduced and Add to All Pits Consecutively (Excluding Mancalas)
    public void penaltyFractionAddToAllPitsConsecutively(int currentPlayer, double fraction, MancalaBoardGroup mbg) {
        int mancalaIndex = (currentPlayer == 0) ? MANCALA1 : MANCALA2;
        int totalScore = getStones(mancalaIndex);
        int scoreToKeep = (int) (totalScore * (1 - fraction));
        int scoreToDistribute = totalScore - scoreToKeep; // Include extra from rounding

        pits[mancalaIndex] = scoreToKeep;

        // Distribute the score consecutively
        for (int i = 0; i < pits.length - 2 && scoreToDistribute > 0; i++) {
            if (i != MANCALA1 && i != MANCALA2) {
                pits[i]++;
                scoreToDistribute--;
            }
        }
    }


}
