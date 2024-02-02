package com.instance.mancala2;

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
        if(pits[pitIndex]==0){
        wasEmpty=true;
        }else {wasEmpty=false;}
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

}
