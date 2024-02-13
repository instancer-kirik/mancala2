package com.instance.mancala2;

public class GamePenalties {
    private MancalaBoard board;
    private GamePreferences preferences; // Assuming this class exists to hold game settings

    public GamePenalties(MancalaBoard board, GamePreferences preferences) {
        this.board = board;
        this.preferences = preferences;
    }

    public void applyPenalty( int blightedPlayer, MancalaBoardGroup mbg, boolean all) {
        PenaltyStrategy strategy = preferences.getPenaltyStrategy();
        PenaltyAmount amount = preferences.getPenaltyAmount();
        if(all){amount = PenaltyAmount.WHOLE;}
        switch (strategy) {
            case DEDUCT:
                applyFractionalPenalty(blightedPlayer, amount, mbg);
                break;
            case DISTRIBUTE_SINGLE_RANDOM:
                moveScoreToARandomPit(blightedPlayer, amount, mbg);
                break;
            case DISTRIBUTE_MANY_RANDOM:
                moveScoreToRandomPits(blightedPlayer,amount, mbg);
                break;
            case DISTRIBUTE_ALL_CONSECUTIVE:
                distributeScoreConsecutively(blightedPlayer, amount, mbg);
                break;
            case MOVE_TO_SELECT:
                System.out.println("TODO");
                break;
            // Handle other cases as needed
        }
    }

    private void applyFractionalPenalty(int blightedPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyScoreReduced(blightedPlayer,amount.getFraction(), mbg);
    }

    private void moveScoreToARandomPit(int blightedPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
       board.penaltyFractionScoreToARandomPit(blightedPlayer,amount.getFraction(), mbg);
    }
    private void moveScoreToRandomPits(int blightedPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyFractionScoreToRandomPits(blightedPlayer,amount.getFraction(), mbg);
    }
    private void distributeScoreConsecutively(int blightedPlayer, PenaltyAmount amount, MancalaBoardGroup mbg) {
        board.penaltyFractionAddToAllPitsConsecutively(blightedPlayer,amount.getFraction(), mbg);
    }

    // Additional methods for specific penalty actions
}
