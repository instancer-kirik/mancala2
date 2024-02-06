package com.instance.mancala2;

public class GamePenalties {
    private MancalaBoard board;
    private GamePreferences preferences; // Assuming this class exists to hold game settings

    public GamePenalties(MancalaBoard board, GamePreferences preferences) {
        this.board = board;
        this.preferences = preferences;
    }

    public void applyPenalty( int currentPlayer, MancalaBoardGroup mbg) {
        PenaltyStrategy strategy = preferences.getPenaltyStrategy();
        PenaltyAmount amount = preferences.getPenaltyAmount();
        switch (strategy) {
            case DEDUCT:
                applyFractionalPenalty(currentPlayer, amount, mbg);
                break;
            case DISTRIBUTE_SINGLE_RANDOM:
                moveScoreToARandomPit(currentPlayer, amount, mbg);
                break;
            case DISTRIBUTE_MANY_RANDOM:
                moveScoreToRandomPits(currentPlayer,amount, mbg);
                break;
            case DISTRIBUTE_ALL_CONSECUTIVE:
                distributeScoreConsecutively(currentPlayer, amount, mbg);
                break;
            case MOVE_TO_SELECT:
                System.out.println("TODO");
                break;
            // Handle other cases as needed
        }
    }

    private void applyFractionalPenalty(int currentPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyScoreReduced(currentPlayer,amount.getFraction(), mbg);
    }

    private void moveScoreToARandomPit(int currentPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
       board.penaltyFractionScoreToARandomPit(currentPlayer,amount.getFraction(), mbg);
    }
    private void moveScoreToRandomPits(int currentPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyFractionScoreToRandomPits(currentPlayer,amount.getFraction(), mbg);
    }
    private void distributeScoreConsecutively(int currentPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyFractionAddToAllPitsConsecutively(currentPlayer,amount.getFraction(), mbg);
    }

    // Additional methods for specific penalty actions
}
