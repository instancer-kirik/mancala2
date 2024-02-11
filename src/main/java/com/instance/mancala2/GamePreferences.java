package com.instance.mancala2;

public class GamePreferences {
    private static GamePreferences instance;

    private CheatPhrase cheatPhrase;
    private PenaltyAmount penaltyAmount;
    private PenaltyStrategy penaltyStrategy;
    private boolean stealOpposite;
    private boolean capturePlacedAlso;
    private boolean continueAfterEmptyHand_AutoPickup;
    // Private constructor to prevent instantiation
    private GamePreferences() {
        // Default values
        this.cheatPhrase = CheatPhrase.DEFAULT_PHRASE;
        this.penaltyAmount = PenaltyAmount.HALF;
        this.penaltyStrategy = PenaltyStrategy.DISTRIBUTE_MANY_RANDOM;
        this.continueAfterEmptyHand_AutoPickup = true;
    }

    // Public method to get the single instance
    public static synchronized GamePreferences getInstance() {
        if (instance == null) {
            instance = new GamePreferences();
        }
        return instance;
    }

    // Getters and Setters for your preferences
    public CheatPhrase getCheatPhrase() { return cheatPhrase; }
    public void setCheatPhrase(CheatPhrase cheatPhrase) { this.cheatPhrase = cheatPhrase; }
    public PenaltyAmount getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(PenaltyAmount penaltyAmount) { this.penaltyAmount = penaltyAmount; }
    public PenaltyStrategy getPenaltyStrategy() { return penaltyStrategy; }
    public void setPenaltyStrategy(PenaltyStrategy penaltyStrategy) { this.penaltyStrategy = penaltyStrategy; }
    public boolean isContinueAfterEmptyHand_AutoPickup() {return continueAfterEmptyHand_AutoPickup;}
    public void setContinueAfterEmptyHand_AutoPickup(boolean continueAfterEmptyHand_AutoPickup) {this.continueAfterEmptyHand_AutoPickup = continueAfterEmptyHand_AutoPickup;}
    public boolean isStealOpposite() {return stealOpposite;}
    public void setStealOpposite(boolean stealOpposite) {this.stealOpposite = stealOpposite;}

    public boolean isCapturePlacedAlso() {
        return capturePlacedAlso;
    }

    public void setCapturePlacedAlso(boolean capturePlacedAlso) {
        this.capturePlacedAlso = capturePlacedAlso;
    }

}
