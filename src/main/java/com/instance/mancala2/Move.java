package com.instance.mancala2;

public record Move(ActionType actionType, int pitIndex, int player) {
    @Override
    public String toString() {
        if (pitIndex == -1) {
            return "Player " + (player + 1) + ": " + actionType;
        } else {
            return "Player " + (player + 1) + ": " + actionType + " at pit " + pitIndex;

        }
    }
}
